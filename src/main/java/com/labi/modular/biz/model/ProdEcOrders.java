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
 * <p>
 * 退货单
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_ec_orders")
public class ProdEcOrders extends Model<ProdEcOrders> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Integer orderId;

    /**
     * 订单产品id
     */
    @TableField("order_dtl_id")
    private Integer orderDtlId;


    /**
     * 退货原因id
     */
    @TableField("reason_id")
    private Integer reasonId;

    /**
     * 退货原因
     */
    @TableField("reason_name")
    private String reasonName;
    /**
     * 拒绝原因
     */
    @TableField("refuse_remark")
    private String refuseRemark;
    /**
     * 退货订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 电话
     */
    @TableField("phone_num")
    private String phoneNum;

    /**
     * 备注
     */
    @TableField("return_remark")
    private String returnRemark;

    /**
     * 退货总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /**
     * 退货总价
     */
    @TableField("real_price")
    private BigDecimal realPrice;


    /**
     * 所属公司
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 退货类型  M:仅退款 G:退货退款
     */
    private String type;

    /**
     * 订单状态：waitReview(待审核)、waitDelivery(待发货)、waitTake(待收货)、refuse(拒绝)、canceled(已取消)、finish(已完成)
     */
    private String progress;

    /**
     * 备注信息
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

    //-------------扩展字段-------------------
    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String prodName;

    /**
     * 单价
     */
    @TableField(exist = false)
    private Double prodSalePrice;
    /**
     * 图片
     */
    @TableField(exist = false)
    private String prodThumbImg;
    /**
     * 购买数量
     */
    @TableField(exist = false)
    private String buyNmu;
    /**
     * 商品属性
     */
    @TableField(exist = false)
    private String prodAttrInfo;
    /**
     * 退货用户电话
     */
    @TableField(exist = false)
    private String userPhoneNum;

    @TableField(exist = false)
    private String logisticsNo;

    @TableField(exist = false)
    private String logisticsName;

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

    public Integer getReasonId() {
        return reasonId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
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

    public Double getProdSalePrice() {
        return prodSalePrice;
    }

    public void setProdSalePrice(Double prodSalePrice) {
        this.prodSalePrice = prodSalePrice;
    }

    public String getProdThumbImg() {
        return prodThumbImg;
    }

    public void setProdThumbImg(String prodThumbImg) {
        this.prodThumbImg = prodThumbImg;
    }

    public String getBuyNmu() {
        return buyNmu;
    }

    public void setBuyNmu(String buyNmu) {
        this.buyNmu = buyNmu;
    }

    public String getProdAttrInfo() {
        return prodAttrInfo;
    }

    public void setProdAttrInfo(String prodAttrInfo) {
        this.prodAttrInfo = prodAttrInfo;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public Integer getOrderDtlId() {
        return orderDtlId;
    }

    public void setOrderDtlId(Integer orderDtlId) {
        this.orderDtlId = orderDtlId;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
