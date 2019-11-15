package com.xs.micro.check.invoicing.domain.business;

import com.google.common.collect.Lists;
import com.xs.micro.check.invoicing.domain.pojo.vo.invoicing.*;
import com.xs.micro.check.invoicing.extension.util.ImportExcel;
import com.xs.micro.check.invoicing.extension.util.ValidateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 开票数据校验业务
 *
 * @author guochaohui
 */
@Component
public class CheckInvoicingBusiness extends BaseBusiness {

    /**
     * 开票数据校验业务
     *
     * @return
     */
    public List<String> checkInvoicingBusiness(SummaryInfoVO summaryInfo, TuitionInfoVO tuitionInfo, MealsInfoVO mealsInfo, List<LinkManItemVO> linkManList) {
        List<String> errors = Lists.newArrayList();
        try {
            boolean canCheckLinkMan = true;
            if (CollectionUtils.isEmpty(linkManList)) {
                errors.add("联系人数据为空，无法校验联系人填写是否正确");
                canCheckLinkMan = false;
            }
            if (CollectionUtils.isEmpty(summaryInfo.getList())) {
                errors.add("总表数据为空，无法继续执行校验");
                return errors;
            }
            boolean canCheckTuition = true;
            if (CollectionUtils.isEmpty(tuitionInfo.getList())) {
                errors.add("统一（教育费）表数据为空，跳过校验");
                canCheckTuition = false;
            }
            boolean canCheckMeals = true;
            if (CollectionUtils.isEmpty(mealsInfo.getList())) {
                errors.add("往来（伙食费）表数据为空，跳过校验");
                canCheckMeals = false;
            }
            if (canCheckTuition) {
                if (summaryInfo.getTuitionTotal() != tuitionInfo.getTotal()) {
                    errors.add(String.format("统一（教育费）合计金额 [ %f ] 与总表合计金额 [ %f ] 不相等", tuitionInfo.getTotal(), summaryInfo.getTuitionTotal()));
                }
            }
            if (canCheckMeals) {
                if (summaryInfo.getMealsTotal() != mealsInfo.getTotal()) {
                    errors.add(String.format("往来（伙食费）合计金额 [ %f ] 与总表合计金额 [ %f ] 不相等", mealsInfo.getTotal(), summaryInfo.getMealsTotal()));
                }
            }
            // 循环总表数据
            for (SummaryItemVO summaryItem : summaryInfo.getList()) {
                String summaryName = removeAllSpaceChar(summaryItem.getName());
                // 只校验教育费大于0
                if (summaryItem.getTuition() > 0) {
                    if (canCheckTuition) {
                        // 循环教育费
                        TuitionItemVO findTuitionItem = null;
                        for (TuitionItemVO tuitionItem : tuitionInfo.getList()) {
                            String tuitionName = removeAllSpaceChar(tuitionItem.getName());
                            // 比较姓名、金额
                            if (StringUtils.equalsIgnoreCase(summaryName, tuitionName) && summaryItem.getTuition() == tuitionItem.getAmount()) {
                                // 找到数据跳出循环
                                findTuitionItem = tuitionItem;
                                break;
                            }
                        }
                        if (findTuitionItem == null) {
                            // 教育费数据没找到
                            errors.add(String.format("总表中 [ %s ] 的教育费 [ %f ] 在统一（教育费）表中没找到", summaryItem.getName(), summaryItem.getTuition()));
                            continue;
                        }
                        if (canCheckLinkMan) {
                            // 校验联系人
                            boolean findTuitionLinkMan = findLinkMan(linkManList, findTuitionItem.getName(), findTuitionItem.getMobile());
                            if (!findTuitionLinkMan) {
                                errors.add(String.format("统一（教育费）表中 [ %s ] 的手机号 [ %s ] 在联系人表中没找到", findTuitionItem.getName(), findTuitionItem.getMobile()));
                            }
                            if (!ValidateUtil.isMobile(findTuitionItem.getMobile())) {
                                errors.add(String.format("统一（教育费）表中 [ %s ] 的手机号 [ %s ] 填写的不是手机号", findTuitionItem.getName(), findTuitionItem.getMobile()));
                            }
                        }
                    }
                }
                // 只校验伙食费大于0
                if (summaryItem.getMeals() > 0) {
                    if (canCheckMeals) {
                        // 循环伙食费
                        MealsItemVO findMealsItem = null;
                        for (MealsItemVO mealsItem : mealsInfo.getList()) {
                            String mealsName = removeAllSpaceChar(mealsItem.getName());
                            // 比较姓名、金额
                            if (StringUtils.equalsIgnoreCase(summaryName, mealsName) && summaryItem.getMeals() == mealsItem.getAmount()) {
                                // 找到数据跳出循环
                                findMealsItem = mealsItem;
                                break;
                            }
                        }
                        if (findMealsItem == null) {
                            // 教育费数据没找到
                            errors.add(String.format("总表中 [ %s ] 的伙食费 [ %f ] 在往来（伙食费）表中没找到", summaryItem.getName(), summaryItem.getMeals()));
                            continue;
                        }
                        if (canCheckLinkMan) {
                            // 校验联系人
                            boolean findMealsLinkMan = findLinkMan(linkManList, findMealsItem.getName(), findMealsItem.getMobile());
                            if (!findMealsLinkMan) {
                                errors.add(String.format("往来（伙食费）表中 [ %s ] 的手机号 [ %s ] 在联系人表中没找到", findMealsItem.getName(), findMealsItem.getMobile()));
                            }
                            if (!ValidateUtil.isMobile(findMealsItem.getMobile())) {
                                errors.add(String.format("往来（伙食费）表中 [ %s ] 的手机号 [ %s ] 填写的不是手机号", findMealsItem.getName(), findMealsItem.getMobile()));
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            LOG.error("checkInvoicingBusiness error.", e);
            errors.add("校验程序业务逻辑异常." + e.getMessage());
        }
        return errors;
    }

    /**
     * 移除字符串中的所有空字符
     *
     * @param name
     * @return
     * @author guochaohui
     * @date 2019-11-15 18:46
     */
    private String removeAllSpaceChar(String name) {
        return name.replaceAll("\\s", "");
    }

    /**
     * 查找联系人
     *
     * @param linkManList
     * @param name
     * @param mobile
     * @return
     * @author guochaohui
     * @date 2019-11-15 18:36
     */
    private boolean findLinkMan(List<LinkManItemVO> linkManList, String name, String mobile) {
        boolean findLinkMan = false;
        name = removeAllSpaceChar(name);
        mobile = removeAllSpaceChar(mobile);
        for (LinkManItemVO linkManItem : linkManList) {
            String linkManName = removeAllSpaceChar(linkManItem.getName());
            String linkManMobile = removeAllSpaceChar(linkManItem.getMobile());
            // 比较姓名、手机号
            if (StringUtils.equalsIgnoreCase(linkManName, name) && StringUtils.equalsIgnoreCase(linkManMobile, mobile)) {
                // 找到联系人跳出循环
                findLinkMan = true;
                break;
            }
        }
        return findLinkMan;
    }

    /**
     * 获取联系人信息
     *
     * @param linkManExcelFile
     * @param param
     * @return
     * @author guochaohui
     * @date 2019-11-15 15:09
     */
    public List<LinkManItemVO> getLinkManDatas(MultipartFile linkManExcelFile, CheckInvoicingParamVO param) {
        List<String[]> originList = readExcelData(linkManExcelFile, param.getLinkManSheetNum(), param.getLinkManBeginRowNum());
        if (CollectionUtils.isEmpty(originList)) {
            return Lists.newArrayList();
        }
        List<LinkManItemVO> list = Lists.newArrayList();
        for (String[] data : originList) {
            LinkManItemVO item = new LinkManItemVO();
            item.setName(getArrayData(data, param.getLinkManNameColumnNum() - 1));
            item.setMobile(getArrayData(data, param.getLinkManMobileColumnNum() - 1));
            if (StringUtils.isBlank(item.getName())) {
                continue;
            }
            list.add(item);
        }
        return list;
    }

    /**
     * 获取总表数据
     *
     * @param summaryExcelFile
     * @param param
     * @return
     * @author guochaohui
     * @date 2019-11-15 15:24
     */
    public SummaryInfoVO getSummaryDatas(MultipartFile summaryExcelFile, CheckInvoicingParamVO param) {
        List<String[]> originList = readExcelData(summaryExcelFile, param.getSummarySheetNum(), param.getSummaryBeginRowNum());
        if (CollectionUtils.isEmpty(originList)) {
            return new SummaryInfoVO();
        }
        List<SummaryItemVO> list = Lists.newArrayList();
        double tuitionTotal = 0;
        double mealsTotal = 0;
        for (String[] data : originList) {
            double tuition = NumberUtils.toDouble(getArrayData(data, param.getSummaryTuitionColumnNum() - 1), 0);
            double meals = NumberUtils.toDouble(getArrayData(data, param.getSummaryMealsColumnNum() - 1), 0);

            SummaryItemVO item = new SummaryItemVO();
            item.setName(getArrayData(data, param.getSummaryNameColumnNum() - 1));
            item.setTuition(tuition);
            item.setMeals(meals);
            if (StringUtils.isBlank(item.getName())) {
                continue;
            }
            list.add(item);
            tuitionTotal += tuition;
            mealsTotal += meals;
        }
        return new SummaryInfoVO(list, tuitionTotal, mealsTotal);
    }

    /**
     * 获取统一（教育费）表数据
     *
     * @param tuitionExcelFile
     * @param param
     * @return
     * @author guochaohui
     * @date 2019-11-15 15:24
     */
    public TuitionInfoVO getTuitionDatas(MultipartFile tuitionExcelFile, CheckInvoicingParamVO param) {
        List<String[]> originList = readExcelData(tuitionExcelFile, param.getTuitionSheetNum(), param.getTuitionBeginRowNum());
        if (CollectionUtils.isEmpty(originList)) {
            return new TuitionInfoVO();
        }
        List<TuitionItemVO> list = Lists.newArrayList();
        double total = 0;
        for (String[] data : originList) {
            double tuitionAmount = NumberUtils.toDouble(getArrayData(data, param.getTuitionAmountColumnNum() - 1), 0);

            TuitionItemVO item = new TuitionItemVO();
            item.setName(getArrayData(data, param.getTuitionNameColumnNum() - 1));
            item.setMobile(getArrayData(data, param.getTuitionMobileColumnNum() - 1));
            item.setAmount(tuitionAmount);
            if (StringUtils.isBlank(item.getName())) {
                continue;
            }
            list.add(item);
            total += tuitionAmount;
        }
        return new TuitionInfoVO(list, total);
    }

    /**
     * 获取往来（伙食费）表数据
     *
     * @param mealsExcelFile
     * @param param
     * @return
     * @author guochaohui
     * @date 2019-11-15 15:24
     */
    public MealsInfoVO getMealsDatas(MultipartFile mealsExcelFile, CheckInvoicingParamVO param) {
        List<String[]> originList = readExcelData(mealsExcelFile, param.getMealsSheetNum(), param.getMealsBeginRowNum());
        if (CollectionUtils.isEmpty(originList)) {
            return new MealsInfoVO();
        }
        List<MealsItemVO> list = Lists.newArrayList();
        double total = 0;
        for (String[] data : originList) {
            double mealsAmount = NumberUtils.toDouble(getArrayData(data, param.getMealsAmountColumnNum() - 1), 0);

            MealsItemVO item = new MealsItemVO();
            item.setName(getArrayData(data, param.getMealsNameColumnNum() - 1));
            item.setMobile(getArrayData(data, param.getMealsMobileColumnNum() - 1));
            item.setAmount(mealsAmount);
            if (StringUtils.isBlank(item.getName())) {
                continue;
            }
            list.add(item);
            total += mealsAmount;
        }
        return new MealsInfoVO(list, total);
    }

    private String getArrayData(String[] array, int index) {
        if (array.length > index) {
            return array[index];
        }
        return "";
    }

    /**
     * 读取excel文件数据
     *
     * @param excelFile
     * @param sheetNum
     * @param dataBeginRowNum
     * @return
     * @author guochaohui
     * @date 2019-11-15 15:09
     */
    private List<String[]> readExcelData(MultipartFile excelFile, int sheetNum, int dataBeginRowNum) {
        try {
            return new ImportExcel(excelFile, dataBeginRowNum - 2, sheetNum - 1).getDataList();
        } catch (Throwable e) {
            LOG.error("read excel data error. file name={}", excelFile.getOriginalFilename(), e);
            return Lists.newArrayList();
        }
    }

}
