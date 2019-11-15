package com.xs.micro.check.invoicing.domain.pojo.vo.invoicing;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 汇总表数据
 *
 * @author guochaohui
 * @return
 * @date 2019-11-14 18:37
 */
public class SummaryInfoVO {

    private List<SummaryItemVO> list;
    private double tuitionTotal;
    private double mealsTotal;

    public SummaryInfoVO() {
        list = Lists.newArrayList();
        tuitionTotal = 0;
        mealsTotal = 0;
    }

    public SummaryInfoVO(List<SummaryItemVO> list, double tuitionTotal, double mealsTotal) {
        this.list = list;
        this.tuitionTotal = tuitionTotal;
        this.mealsTotal = mealsTotal;
    }

    public List<SummaryItemVO> getList() {
        return list;
    }

    public void setList(List<SummaryItemVO> list) {
        this.list = list;
    }

    public double getTuitionTotal() {
        return tuitionTotal;
    }

    public void setTuitionTotal(double tuitionTotal) {
        this.tuitionTotal = tuitionTotal;
    }

    public double getMealsTotal() {
        return mealsTotal;
    }

    public void setMealsTotal(double mealsTotal) {
        this.mealsTotal = mealsTotal;
    }
}
