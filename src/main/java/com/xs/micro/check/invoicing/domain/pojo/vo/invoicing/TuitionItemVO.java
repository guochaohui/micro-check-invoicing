package com.xs.micro.check.invoicing.domain.pojo.vo.invoicing;

/**
 * 统一（教育费）表数据
 *
 * @author guochaohui
 * @return
 * @date 2019-11-14 18:37
 */
public class TuitionItemVO {

    private String name;
    private String mobile;
    private double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
