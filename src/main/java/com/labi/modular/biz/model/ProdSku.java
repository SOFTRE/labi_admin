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
 * 商品销量单位
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_sku")
public class ProdSku extends Model<ProdSku> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品id
     */
    @TableField("prod_id")
    private Integer prodId;
    /**
     * sku编码
     */
    @TableField("sku_num")
    private String skuNum;
    /**
     * SKU图
     */
    private String img;
    /**
     * 现价/售价
     */
    @TableField("sale_price")
    private BigDecimal salePrice;
    /**
     * 原价
     */
    @TableField("origin_price")
    private BigDecimal originPrice;
    /**
     * 供货价
     */
    @TableField("provide_price")
    private BigDecimal providePrice;
    /**
     * 销量
     */
    @TableField("sale_num")
    private Integer saleNum;
    /**
     * 库存
     */
    @TableField("left_num")
    private Integer leftNum;
    /**
     * 属性可选项ID串，多个id使用英文_间隔
     */
    @TableField("attr_option_ids")
    private String attrOptionIds;
    /**
     * 属性值描述，多个使用英文+间隔
     */
    @TableField("attr_option_names")
    private String attrOptionNames;
    /**
     * 状态
     */
    private String status;
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

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getProvidePrice() {
        return providePrice;
    }

    public void setProvidePrice(BigDecimal providePrice) {
        this.providePrice = providePrice;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public String getAttrOptionIds() {
        return attrOptionIds;
    }

    public void setAttrOptionIds(String attrOptionIds) {
        this.attrOptionIds = attrOptionIds;
    }

    public String getAttrOptionNames() {
        return attrOptionNames;
    }

    public void setAttrOptionNames(String attrOptionNames) {
        this.attrOptionNames = attrOptionNames;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProdSku{" +
        "id=" + id +
        ", prodId=" + prodId +
        ", skuNum=" + skuNum +
        ", img=" + img +
        ", salePrice=" + salePrice +
        ", originPrice=" + originPrice +
        ", providePrice=" + providePrice +
        ", saleNum=" + saleNum +
        ", leftNum=" + leftNum +
        ", attrOptionIds=" + attrOptionIds +
        ", attrOptionNames=" + attrOptionNames +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
