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
 * 教练缴费
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach_order")
public class CoachOrder extends Model<CoachOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 学员id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 学员名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 教练id
     */
    @TableField("coach_id")
    private Integer coachId;
    /**
     * 教练名称
     */
    @TableField("coach_name")
    private String coachName;
    /**
     * 教练缩略图
     */
    @TableField("coach_img")
    private String coachImg;
    /**
     * 教练等级id
     */
    @TableField("coach_grade_id")
    private Integer coachGradeId;
    /**
     * 教练等级名称
     */
    @TableField("coach_grade_name")
    private String coachGradeName;
    /**
     * 教练等级缩略图
     */
    @TableField("coach_grade_img")
    private String coachGradeImg;
    /**
     * 可预约次数
     */
    @TableField("coach_num")
    private Integer coachNum;
    /**
     * 已预约次数
     */
    @TableField("use_num")
    private Integer useNum;
    /**
     * 总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;

    /**
     * 订单进度：waitPay(待支付)、usein(可预约)、finish(已完成)
     */
    private String progress;
    /**
     * 支付方式：wx（微信支付）、ali（支付宝）、unionPay(银联支付)
     */
    @TableField("pay_way")
    private String payWay;
    /**
     * 备注信息
     */
    private String remarks;

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


    public Integer getCoachNum() {
        return coachNum;
    }

    public void setCoachNum(Integer coachNum) {
        this.coachNum = coachNum;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getCoachGradeId() {
        return coachGradeId;
    }

    public void setCoachGradeId(Integer coachGradeId) {
        this.coachGradeId = coachGradeId;
    }

    public String getCoachGradeName() {
        return coachGradeName;
    }

    public void setCoachGradeName(String coachGradeName) {
        this.coachGradeName = coachGradeName;
    }

    public String getCoachGradeImg() {
        return coachGradeImg;
    }

    public void setCoachGradeImg(String coachGradeImg) {
        this.coachGradeImg = coachGradeImg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachImg() {
        return coachImg;
    }

    public void setCoachImg(String coachImg) {
        this.coachImg = coachImg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CoachOrder{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", userName=" + userName +
                ", coachId=" + coachGradeId +
                ", coachName=" + coachGradeName +
                ", coachImg=" + coachGradeImg +
                ", coachNum=" + coachNum +
                ", useNum=" + useNum +
                ", totalPrice=" + totalPrice +
                ", progress=" + progress +
                ", payWay=" + payWay +
                ", remarks=" + remarks +
                ", createtime=" + createtime +
                ", oprtime=" + oprtime +
                "}";
    }
}
