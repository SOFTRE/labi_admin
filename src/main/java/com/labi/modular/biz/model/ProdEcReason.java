package com.labi.modular.biz.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 退货问题选项表
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_ec_reason")
public class ProdEcReason extends  Model<ProdEcReason>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 退款原因
	 */
	private String name;
	
	/**
	 * 排序
	 */
	 @TableField("seq_num")
	private Integer seqNum;
	/**
	 * 数据有效状态 A有效 D删除
	 */
	 private String  status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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
