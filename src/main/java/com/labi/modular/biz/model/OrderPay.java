package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付表，统一支付
 *
 * @author lyr
 * @date 2018-06-06 10:55:04
 */
@TableName("biz_order_pay")
public class OrderPay extends Model<OrderPay> {
    /*
    * id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /*
    * 用户id
    */
    @TableField("user_id")
    private Long userId;
    /*
    * 公司id
    */
    @TableField("dept_id")
    private Long deptId;
    /*
    * 支付编号
    */
    @TableField("pay_no")
    private String payNo;
    /*
    * 订单id
    */
    @TableField("order_id")
    private Long orderId;
    /*
    * 订单类型：coach(教练预约)、course(课程报名)、prod(商城)
    */
    @TableField("type")
    private String type;
    /*
    * 支付金额
    */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /*
    * 支付方式：wx（微信支付）、ali（支付宝）、unionPay(银联支付)
    */
    @TableField("pay_way")
    private String payWay;
    /*
    * 订单状态：waitPay(待支付)、paySuccess(支付成功)、payFail（支付失败）
    */
    @TableField("progress")
    private String progress;
    /*
    * 交易流水号
    */
    @TableField("business_number")
    private String businessNumber;
    /*
    * 支付时间
    */
    @TableField("pay_time")
    private Date payTime;
    /*
    * 创建时间
    */
    @TableField("createtime")
    private Date createtime;
    /*
    * 操作时间
    */
    @TableField("oprtime")
    private Date oprtime;

    /*
    * 公司名称
    */
    @TableField(exist = false)
    private String deptName;
    /*
    * 用户名称
    */
    @TableField(exist = false)
    private String userName;
    /*
    * 用户手机号
    */
    @TableField(exist = false)
    private String phoneNum;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayNo() {
        return this.payNo;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayWay() {
        return this.payWay;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getBusinessNumber() {
        return this.businessNumber;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setOprtime(Date oprtime) {
        this.oprtime = oprtime;
    }

    public Date getOprtime() {
        return this.oprtime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderLogistics{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", createtime=" + createtime +
                "}";
    }
}