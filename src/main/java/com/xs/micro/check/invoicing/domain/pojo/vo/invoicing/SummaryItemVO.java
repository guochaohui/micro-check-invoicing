package com.xs.micro.check.invoicing.domain.pojo.vo.invoicing;

/**
 * 汇总表数据
 *
 * @author guochaohui
 * @return
 * @date 2019-11-14 18:37
 */
public class SummaryItemVO {

    private String name;
    private double meals;
    private double tuition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMeals() {
        return meals;
    }

    public void setMeals(double meals) {
        this.meals = meals;
    }

    public double getTuition() {
        return tuition;
    }

    public void setTuition(double tuition) {
        this.tuition = tuition;
    }
}
