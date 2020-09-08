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
 * 用户商城订单明细
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_orders_dtl")
public class ProdOrdersDtl extends Model<ProdOrdersDtl> {

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
     * 商品SKU_ID
     */
    @TableField("prod_sku_id")
    private Integer prodSkuId;
    /**
     * 商品ID
     */
    @TableField("prod_id")
    private String prodId;
    /**
     * 供货商id
     */
    @TableField("supplier_id")
    private Integer supplierId;
    /**
     * 商品名称
     */
    @TableField("prod_name")
    private String prodName;
    /**
     * 购买时商品单价
     */
    @TableField("prod_sale_price")
    private BigDecimal prodSalePrice;
    /**
     * 商品缩略图
     */
    @TableField("prod_thumb_img")
    private String prodThumbImg;
    /**
     * 购买数量
     */
    @TableField("buy_num")
    private Integer buyNum;
    /**
     * 商品属性描述，格式：属性1值,属性2值。使用英文逗号间隔。
     */
    @TableField("prod_attr_info")
    private String prodAttrInfo;
    /**
     * 是否退货中:  F:否,  T:是 O:完成
     */
    @TableField("if_return")
    private String ifReturn;
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

    public Integer getProdSkuId() {
        return prodSkuId;
    }

    public void setProdSkuId(Integer prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
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

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getProdAttrInfo() {
        return prodAttrInfo;
    }

    public void setProdAttrInfo(String prodAttrInfo) {
        this.prodAttrInfo = prodAttrInfo;
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

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getIfReturn() {
        return ifReturn;
    }

    public void setIfReturn(String ifReturn) {
        this.ifReturn = ifReturn;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProdOrdersDtl{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", prodSkuId=" + prodSkuId +
        ", prodId=" + prodId +
        ", prodName=" + prodName +
        ", prodSalePrice=" + prodSalePrice +
        ", prodThumbImg=" + prodThumbImg +
        ", buyNum=" + buyNum +
        ", prodAttrInfo=" + prodAttrInfo +
        ", remarks=" + remarks +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
