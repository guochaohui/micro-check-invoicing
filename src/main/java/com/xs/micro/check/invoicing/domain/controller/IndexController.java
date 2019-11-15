package com.xs.micro.check.invoicing.domain.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 主页显示
 *
 * @author guochaohui
 */
@ApiIgnore
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model) {
        return "checkInvoicing";
    }

}
