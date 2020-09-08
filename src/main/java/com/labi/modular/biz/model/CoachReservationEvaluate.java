package com.labi.modular.biz.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 反馈评分
 * @author Administrator
 *
 */
@TableName("biz_coach_reservation_evaluate")
public class CoachReservationEvaluate extends Model<CoachReservationEvaluate>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;// id 
	
	@TableField("reservation_id")
	private Integer reservationId;// 预约id
	
	@TableField("evaluate_id")
	private Integer evaluateId;// 评价项id
	
	@TableField("evaluate_name")
	private String evaluateName;
	
	private Double score;// 评分
	
	@TableField("user_id")
	private Integer userId;// 学员id
	
	@TableField("user_name")
	private String userName;// 学员名称
	
	private Integer type;//所属公司
	
	@TableField("coach_id")
	private Integer coachId;// 教练id
	
	@TableField("coach_name")
	private String coachName;//  教练名称
	
	private Date createtime;
	private Date oprtime;
	
	
	//--------------------扩展字段------------------------
	@TableField(exist = false)
	private String desQuerName;//评价id 对应的问题描述
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}
 
	public Integer getReservationId() {
		return reservationId;
	}



	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}



	public String getEvaluateName() {
		return evaluateName;
	}



	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}



	public Integer getEvaluateId() {
		return evaluateId;
	}



	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}



	public String getDesQuerName() {
		return desQuerName;
	}



	public void setDesQuerName(String desQuerName) {
		this.desQuerName = desQuerName;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public Double getScore() {
		return score;
	}



	public void setScore(Double score) {
		this.score = score;
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



	public String getCoachName() {
		return coachName;
	}



	public void setCoachName(String coachName) {
		this.coachName = coachName;
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
		// TODO Auto-generated method stub
		 return this.id;
	}
	
}
