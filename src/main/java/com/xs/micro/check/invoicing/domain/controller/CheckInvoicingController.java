package com.xs.micro.check.invoicing.domain.controller;


import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.xs.micro.check.invoicing.domain.business.CheckInvoicingBusiness;
import com.xs.micro.check.invoicing.domain.pojo.vo.invoicing.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 开票数据校验
 *
 * @author guochaohui
 */
@Controller
@RequestMapping(value = "/checkInvoicing")
public class CheckInvoicingController extends BaseController {

    @Autowired
    private CheckInvoicingBusiness checkInvoicingBusiness;

    @RequestMapping(value = "/doCheck", method = {RequestMethod.POST})
    public String doCheck(CheckInvoicingParamVO param
            , @RequestParam("linkManExcelFile") MultipartFile linkManExcelFile
            , @RequestParam("summaryExcelFile") MultipartFile summaryExcelFile
            , @RequestParam("tuitionExcelFile") MultipartFile tuitionExcelFile
            , @RequestParam("mealsExcelFile") MultipartFile mealsExcelFile
            , Model model) {
        LOG.info("check invoicing begin");
        Stopwatch sw = Stopwatch.createStarted();
        List<String> results = Lists.newArrayList();
        try {
            List<LinkManItemVO> linkManList = checkInvoicingBusiness.getLinkManDatas(linkManExcelFile, param);
            SummaryInfoVO summaryInfo = checkInvoicingBusiness.getSummaryDatas(summaryExcelFile, param);
            TuitionInfoVO tuitionInfo = checkInvoicingBusiness.getTuitionDatas(tuitionExcelFile, param);
            MealsInfoVO mealsInfo = checkInvoicingBusiness.getMealsDatas(mealsExcelFile, param);
            results = checkInvoicingBusiness.checkInvoicingBusiness(summaryInfo, tuitionInfo, mealsInfo, linkManList);
        } catch (Throwable e) {
            LOG.error("do check error.", e);
            results = Lists.newArrayList("校验程序入口逻辑异常." + e.getMessage());
        }
        boolean isSuccess = CollectionUtils.isEmpty(results);
        if (isSuccess) {
            results = Lists.newArrayList("校验成功，数据填写无误！");
        }
        model.addAttribute("results", results);
        model.addAttribute("msgColor", isSuccess ? "gree" : "red");
        LOG.info("check invoicing end. cost {}", sw.stop().toString());
        return "checkInvoicing";
    }

    @RequestMapping(value = "/doCheck", method = {RequestMethod.GET})
    public String doCheck() {
        return "checkInvoicing";
    }

}
