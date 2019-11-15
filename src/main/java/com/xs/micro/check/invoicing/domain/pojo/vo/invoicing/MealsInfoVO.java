package com.xs.micro.check.invoicing.domain.pojo.vo.invoicing;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 往来（伙食费）表数据
 *
 * @author guochaohui
 * @return
 * @date 2019-11-14 18:37
 */
public class MealsInfoVO {

    private List<MealsItemVO> list;
    private double total;

    public MealsInfoVO() {
        list = Lists.newArrayList();
        total = 0;
    }

    public MealsInfoVO(List<MealsItemVO> list, double total) {
        this.list = list;
        this.total = total;
    }

    public List<MealsItemVO> getList() {
        return list;
    }

    public void setList(List<MealsItemVO> list) {
        this.list = list;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
