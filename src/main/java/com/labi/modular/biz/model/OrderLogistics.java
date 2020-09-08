package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单物流信息
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_order_logistics")
public class OrderLogistics extends Model<OrderLogistics> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 订单类型  S:商城订单 R:退货订单
     */
    @TableField("order_type")
    private String orderType;
    /**
     * 物流公司
     */
    @TableField("logistics_name")
    private String logisticsName;
    /**
     * 物流公司代号
     */
    @TableField("logistics_code")
    private String logisticsCode;
    /**
     * 物流单号
     */
    @TableField("logistics_no")
    private String logisticsNo;
    /**
     * 创建时间
     */
    private Date createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
                ", logisticsName=" + logisticsName +
                ", logisticsCode=" + logisticsCode +
                ", logisticsNo=" + logisticsNo +
                ", createtime=" + createtime +
                "}";
    }
}
