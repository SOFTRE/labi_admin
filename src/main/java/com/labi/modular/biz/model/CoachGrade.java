package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 教练等级
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach_grade")
public class CoachGrade extends Model<CoachGrade> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 等级名称
     */
    private String name;
    /**
     * 标题
     */
    private String title;
    /**
     * 简要
     */
    private String des;
    /**
     * 详情HTML文件
     */
    @TableField("des_file")
    private String desFile;
    /**
     * 缩略图
     */
    private String img;
    /**
     * 教练等级多图
     */
    @TableField("more_img")
    private String moreImg;
    /**
     * 可预约次数
     */
    @TableField("coach_num")
    private Integer coachNum;
    /**
     * 定价
     */
    private BigDecimal price;
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 状态
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
    }

    public Integer getCoachNum() {
        return coachNum;
    }

    public void setCoachNum(Integer coachNum) {
        this.coachNum = coachNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMoreImg() {
		return moreImg;
	}

	public void setMoreImg(String moreImg) {
		this.moreImg = moreImg;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CoachGrade{" +
        "id=" + id +
        ", name=" + name +
        ", seqNum=" + seqNum +
        ", status=" + status +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
