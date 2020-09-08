package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 优惠券规则表
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coupon_rule")
public class CouponRule extends Model<CouponRule> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 规则名称
     */
    @TableField("rule_name")
    private String ruleName;
    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;
    /**
     * 赠送方式，product(商城消费赠送)、coach(预约教练赠送)、course(课程报名赠送)
     */
    @TableField("extend_mode")
    private String extendMode;
    /**
     * 使用方式，all(通用)、product(商城消费)、coach(预约教练)、course(课程报名)
     */
    @TableField("use_mode")
    private String useMode;
    /**
     * 赠送时的最低消费金额
     */
    @TableField("present_min_val")
    private Integer presentMinVal;
    /**
     * 面额
     */
    @TableField("face_value")
    private Integer faceValue;
    /**
     * 面额规则，当面额为0时，根据规则生成面额
     */
    @TableField("face_rule")
    private String faceRule;
    /**
     * 发放开始日期
     */
    @TableField("issue_begin_date")
    private Date issueBeginDate;
    /**
     * 发放结束日期
     */
    @TableField("issue_end_date")
    private Date issueEndDate;
    /**
     * 使用开始日期
     */
    @TableField("valid_begin_date")
    private Date validBeginDate;
    /**
     * 使用结束日期
     */
    @TableField("valid_end_date")
    private Date validEndDate;
    /**
     * 使用的最低消费金额
     */
    @TableField("valid_min_val")
    private Integer validMinVal;
    /**
     * 限量发行总张数
     */
    @TableField("limit_ttl_qty")
    private Integer limitTtlQty;
    /**
     * 每人限领张数
     */
    @TableField("limit_per_qty")
    private Integer limitPerQty;
    /**
     * 已发行总张数
     */
    @TableField("issued_ttl_qty")
    private Integer issuedTtlQty;
    /**
     * 已使用总张数
     */
    @TableField("used_ttl_qty")
    private Integer usedTtlQty;
    /**
     * 编号前缀
     */
    @TableField("num_prefix")
    private String numPrefix;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否开启
     */
    @TableField("if_open")
    private String ifOpen;
    /**
     * A:正常 D:删除
     */
    private String status;
    
    /**
     * 所属公司
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 操作时间
     */
    private Date oprtime;

   //--------------扩展字段---------------
//    @TableField(exist = false)
//    private Date issueBeginDateTwo;
//    @TableField(exist = false)
//    private Date issueEndDateTwo;
//    @TableField(exist = false)
//    private Date validBeginDateTwo;
//    @TableField(exist = false)
//    private Date validEndDateTwo;
    
    

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getExtendMode() {
        return extendMode;
    }

    public void setExtendMode(String extendMode) {
        this.extendMode = extendMode;
    }

    public String getUseMode() {
        return useMode;
    }

    public void setUseMode(String useMode) {
        this.useMode = useMode;
    }

    public Integer getPresentMinVal() {
        return presentMinVal;
    }

    public void setPresentMinVal(Integer presentMinVal) {
        this.presentMinVal = presentMinVal;
    }

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public String getFaceRule() {
        return faceRule;
    }

    public void setFaceRule(String faceRule) {
        this.faceRule = faceRule;
    }

    public Date getIssueBeginDate() {
        return issueBeginDate;
    }

    public void setIssueBeginDate(Date issueBeginDate) {
        this.issueBeginDate = issueBeginDate;
    }

    public Date getIssueEndDate() {
        return issueEndDate;
    }

    public void setIssueEndDate(Date issueEndDate) {
        this.issueEndDate = issueEndDate;
    }

    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public Integer getValidMinVal() {
        return validMinVal;
    }

    public void setValidMinVal(Integer validMinVal) {
        this.validMinVal = validMinVal;
    }

    public Integer getLimitTtlQty() {
        return limitTtlQty;
    }

    public void setLimitTtlQty(Integer limitTtlQty) {
        this.limitTtlQty = limitTtlQty;
    }

    public Integer getLimitPerQty() {
        return limitPerQty;
    }

    public void setLimitPerQty(Integer limitPerQty) {
        this.limitPerQty = limitPerQty;
    }

    public Integer getIssuedTtlQty() {
        return issuedTtlQty;
    }

    public void setIssuedTtlQty(Integer issuedTtlQty) {
        this.issuedTtlQty = issuedTtlQty;
    }

    public Integer getUsedTtlQty() {
        return usedTtlQty;
    }

    public void setUsedTtlQty(Integer usedTtlQty) {
        this.usedTtlQty = usedTtlQty;
    }

    public String getNumPrefix() {
        return numPrefix;
    }

    public void setNumPrefix(String numPrefix) {
        this.numPrefix = numPrefix;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIfOpen() {
        return ifOpen;
    }

    public void setIfOpen(String ifOpen) {
        this.ifOpen = ifOpen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CouponRule{" +
        "id=" + id +
        ", ruleName=" + ruleName +
        ", couponName=" + couponName +
        ", extendMode=" + extendMode +
        ", useMode=" + useMode +
        ", presentMinVal=" + presentMinVal +
        ", faceValue=" + faceValue +
        ", faceRule=" + faceRule +
        ", issueBeginDate=" + issueBeginDate +
        ", issueEndDate=" + issueEndDate +
        ", validBeginDate=" + validBeginDate +
        ", validEndDate=" + validEndDate +
        ", validMinVal=" + validMinVal +
        ", limitTtlQty=" + limitTtlQty +
        ", limitPerQty=" + limitPerQty +
        ", issuedTtlQty=" + issuedTtlQty +
        ", usedTtlQty=" + usedTtlQty +
        ", numPrefix=" + numPrefix +
        ", remarks=" + remarks +
        ", ifOpen=" + ifOpen +
        ", status=" + status +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
