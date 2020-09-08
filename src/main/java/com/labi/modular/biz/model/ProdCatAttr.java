package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 分类包含的属性
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_cat_attr")
public class ProdCatAttr extends Model<ProdCatAttr> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属分类ID
     */
    @TableField("cat_id")
    private Integer catId;
    /**
     * 属性名称
     */
    @TableField("attr_name")
    private String attrName;
    /**
     * 属性描述
     */
    @TableField("attire_des")
    private String attireDes;
    /**
     * 状态 A:正常 D:删除
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

    @TableField(exist = false)
    private List<ProdCatAttrOption> options;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttireDes() {
        return attireDes;
    }

    public void setAttireDes(String attireDes) {
        this.attireDes = attireDes;
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

    public Date getOprtime() {
        return oprtime;
    }

    public void setOprtime(Date oprtime) {
        this.oprtime = oprtime;
    }

    public List<ProdCatAttrOption> getOptions() {
        return options;
    }

    public void setOptions(List<ProdCatAttrOption> options) {
        this.options = options;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProdCatAttr{" +
        "id=" + id +
        ", catId=" + catId +
        ", attrName=" + attrName +
        ", attireDes=" + attireDes +
        ", status=" + status +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
