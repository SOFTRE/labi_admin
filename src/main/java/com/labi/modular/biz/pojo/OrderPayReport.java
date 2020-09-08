package com.labi.modular.biz.pojo;

import java.math.BigDecimal;


/**
 * 报表
 *
 * @author lyr123
 * @since 2018年7月18日 10:59:41
 */
public class OrderPayReport {

    private String time;
    private String businessType;

    private int num;
    private BigDecimal totalPrice;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}