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
 * 优惠券列表
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coupon_list")
public class CouponList extends Model<CouponList> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 规则id
     */
    @TableField("rule_id")
    private Integer ruleId;
    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;
    /**
     * 发放方式，product(商城消费赠送)、coach(预约教练赠送)、course(课程报名赠送)
     */
    @TableField("extend_mode")
    private String extendMode;
    /**
     * 使用方式，all(通用)、product(商城消费)、coach(预约教练)、course(课程报名)
     */
    @TableField("use_mode")
    private String useMode;
    /**
     * 电子券代码
     */
    @TableField("ec_code")
    private String ecCode;
    /**
     * 面额
     */
    @TableField("face_value")
    private Integer faceValue;
    /**
     * 是否使用
     */
    @TableField("if_used")
    private String ifUsed;
    
    /**
     * 所属公司
     */
    private Integer type;
    /**
     * 是否过期
     */
    @TableField("if_expired")
    private String ifExpired;
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
     * 使用时间
     */
    @TableField("use_time")
    private Date useTime;
    /**
     * 备注
     */
    private String remarks;
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

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
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

    public String getEcCode() {
        return ecCode;
    }

    public void setEcCode(String ecCode) {
        this.ecCode = ecCode;
    }

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public String getIfUsed() {
        return ifUsed;
    }

    public void setIfUsed(String ifUsed) {
        this.ifUsed = ifUsed;
    }

    public String getIfExpired() {
        return ifExpired;
    }

    public void setIfExpired(String ifExpired) {
        this.ifExpired = ifExpired;
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

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CouponList{" +
        "id=" + id +
        ", userId=" + userId +
        ", ruleId=" + ruleId +
        ", couponName=" + couponName +
        ", extendMode=" + extendMode +
        ", useMode=" + useMode +
        ", ecCode=" + ecCode +
        ", faceValue=" + faceValue +
        ", ifUsed=" + ifUsed +
        ", ifExpired=" + ifExpired +
        ", validBeginDate=" + validBeginDate +
        ", validEndDate=" + validEndDate +
        ", validMinVal=" + validMinVal +
        ", useTime=" + useTime +
        ", remarks=" + remarks +
        ", createtime=" + createtime +
        "}";
    }
}
