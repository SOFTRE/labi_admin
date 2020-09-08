package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户商城订单
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_orders")
public class ProdOrders extends Model<ProdOrders> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 发货时间
     */
    @TableField("delivery_time")
    private Date deliveryTime;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 订单总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 配送地址信息，格式：收货人,收货人手机号,收货地址
     */
    @TableField("ship_info")
    private String shipInfo;
    /**
     * 订单状态：waitPay(待支付)、waitDelivery(待发货)、waitTake(待收货)、waitJudge(待评价)、judged(已评价)、canceled(已取消)
     */
    private String progress;
    /**
     * 是否视频
     */
    @TableField("if_video")
    private String ifVideo;
    /**
    * 公司id
    */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 支付方式：wx（微信支付）、ali（支付宝）、unionPay(银联支付)
     */
    @TableField("pay_way")
    private String payWay;
    /**
     * 备注信息，建议不超过100字
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 操作时间
     */
    private Date oprtime;

    @TableField(exist = false)
    private String prodName;
    @TableField(exist = false)
    private BigDecimal prodSalePrice;
    @TableField(exist = false)
    private String prodThumbImg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getShipInfo() {
        return shipInfo;
    }

    public void setShipInfo(String shipInfo) {
        this.shipInfo = shipInfo;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getOprtime() {
        return oprtime;
    }

    public void setOprtime(Date oprtime) {
        this.oprtime = oprtime;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public BigDecimal getProdSalePrice() {
        return prodSalePrice;
    }

    public void setProdSalePrice(BigDecimal prodSalePrice) {
        this.prodSalePrice = prodSalePrice;
    }

    public String getProdThumbImg() {
        return prodThumbImg;
    }

    public void setProdThumbImg(String prodThumbImg) {
        this.prodThumbImg = prodThumbImg;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getIfVideo() {
        return ifVideo;
    }

    public void setIfVideo(String ifVideo) {
        this.ifVideo = ifVideo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProdOrders{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", userId=" + userId +
        ", userName=" + userName +
        ", totalPrice=" + totalPrice +
        ", freight=" + freight +
        ", shipInfo=" + shipInfo +
        ", progress=" + progress +
        ", payWay=" + payWay +
        ", remarks=" + remarks +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
