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
 * 教练预约记录及反馈
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach_reservation")
public class CoachReservation extends Model<CoachReservation> {

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
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
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
     * 评价星级
     */
    @TableField("judge_level")
    private Double judgeLevel;
    /**
     * 反馈内容
     */
    @TableField("feedback_content")
    private String feedbackContent;
    /**
     * 是否完成:    F:否,  T:是
     */
    @TableField("if_finish")
    private String ifFinish;
    /**
     * 是否反馈:    F:否,  T:是
     */
    @TableField("if_feedback")
    private String ifFeedback;
    /*
    * 是否发送1小时提示短信
    */
    @TableField( "if_send_msg1")
    private String ifSendMsg1;
    
    /**
     * 是否取消
     */
    @TableField("if_feedback")
    private String ifCancel;
    
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIfCancel() {
		return ifCancel;
	}

	public void setIfCancel(String ifCancel) {
		this.ifCancel = ifCancel;
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

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

    public Double getJudgeLevel() {
        return judgeLevel;
    }

    public void setJudgeLevel(Double judgeLevel) {
        this.judgeLevel = judgeLevel;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getIfFinish() {
        return ifFinish;
    }

    public void setIfFinish(String ifFinish) {
        this.ifFinish = ifFinish;
    }

    public String getIfFeedback() {
        return ifFeedback;
    }

    public void setIfFeedback(String ifFeedback) {
        this.ifFeedback = ifFeedback;
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

    public String getIfSendMsg1() {
        return ifSendMsg1;
    }

    public void setIfSendMsg1(String ifSendMsg1) {
        this.ifSendMsg1 = ifSendMsg1;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CoachReservation{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", coachId=" + coachId +
        ", coachName=" + coachName +
        ", coachImg=" + coachImg +
        ", judgeLevel=" + judgeLevel +
        ", feedbackContent=" + feedbackContent +
        ", ifFinish=" + ifFinish +
        ", ifFeedback=" + ifFeedback +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
