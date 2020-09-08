package com.labi.modular.biz.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("biz_coach_evaluate_option")
public class CoachEvaluateOption extends Model<CoachEvaluateOption>{
	private static final long serialVersionUID = 1L;
	
	private Integer id;//id
	
	private String name;//名称
	
	 @TableField("seq_num")
	private Integer seqNum;// 排序
	
	private Integer type;//所属公司
	
	@TableField("is_start")
	private String isStart;//是否启用
	
	private String status;// 状态 A正常 D删除
	
	private Date createtime;// 创建时间
	
	private Date oprtime;// 操作时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	
}
