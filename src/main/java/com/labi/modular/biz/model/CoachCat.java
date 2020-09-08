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
 * 教练分类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach_cat")
public class CoachCat extends Model<CoachCat> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类名称 
     */
    private String name;
    
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 所属公司
     */
    private Integer type;
    
    /**
     * 状态;A:正常 D:删除
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date oprtime;
    
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
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
    
    
}
