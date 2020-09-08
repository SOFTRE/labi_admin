package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 教练上课时间
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach_time")
public class CoachTime extends Model<CoachTime> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 教练id
     */
    @TableField("coach_id")
    private Integer coachId;
    
    /**
     * 开始时间
     */
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    
    /**
     * 结束时间
     */
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    
    /***
     * 创建时间
     */
    @TableField("create_time")
    private Date createtime;


    //-----------扩展字段---------------
    @TableField(exist = false)
    private String coachName;//教练名称
    
    @TableField(exist = false)
    private String coachGradeName;//教练等级描述
    
    @TableField(exist = false)
    private String dayTime;//日期
    
    @TableField(exist = false)
    private String hhmmKey;//索引key
    
    @TableField(exist = false)
    private String hhmm; //时分
    
    @TableField(exist = false)
    private Integer flg=0;//教练时间是否存在标识
    
    @TableField(exist = false)
    private String week; //周几
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public String getHhmm() {
		return hhmm;
	}

	public void setHhmm(String hhmm) {
		this.hhmm = hhmm;
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
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

	public Integer getFlg() {
		return flg;
	}

	public void setFlg(Integer flg) {
		this.flg = flg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getCoachGradeName() {
		return coachGradeName;
	}

	public void setCoachGradeName(String coachGradeName) {
		this.coachGradeName = coachGradeName;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getHhmmKey() {
		return hhmmKey;
	}

	public void setHhmmKey(String hhmmKey) {
		this.hhmmKey = hhmmKey;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
 
    @Override
    public String toString() {
        return "CoachOrder{" +
        "id=" + id +
        ", coachId=" + coachId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
