package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_attr")
public class ProdAttr extends Model<ProdAttr> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属商品ID
     */
    @TableField("prod_id")
    private Integer prodId;
    /**
     * 分类属性id
     */
    @TableField("attr_id")
    private Integer attrId;

    /**
     * 属性名称
     */
    @TableField("attr_name")
    private String attrName;
    /**
     * 分类属性值id
     */
    @TableField("attr_option_id")
    private Integer attrOptionId;

    /**
     * 属性值名称
     */
    @TableField("option_name")
    private String optionName;

    /**
     * 状态 A:正常 D:删除
     */
    private String status;
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

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getAttrOptionId() {
        return attrOptionId;
    }

    public void setAttrOptionId(Integer attrOptionId) {
        this.attrOptionId = attrOptionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
