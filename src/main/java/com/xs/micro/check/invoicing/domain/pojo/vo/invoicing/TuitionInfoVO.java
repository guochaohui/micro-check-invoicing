package com.xs.micro.check.invoicing.domain.pojo.vo.invoicing;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 统一（教育费）表数据
 *
 * @author guochaohui
 * @return
 * @date 2019-11-14 18:37
 */
public class TuitionInfoVO {

    private List<TuitionItemVO> list;
    private double total;

    public TuitionInfoVO() {
        list = Lists.newArrayList();
        total = 0;
    }

    public TuitionInfoVO(List<TuitionItemVO> list, double total) {
        this.list = list;
        this.total = total;
    }

    public List<TuitionItemVO> getList() {
        return list;
    }

    public void setList(List<TuitionItemVO> list) {
        this.list = list;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
