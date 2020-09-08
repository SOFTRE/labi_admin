package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 教练表
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_coach")
public class Coach extends Model<Coach> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 教练分类id
     */
    @TableField("coach_cat_id")
    private Integer coachCatId;
    /**
     * 教练分类名称
     */
    @TableField("coach_cat_name")
    private String coachCatName;
    
    /**
     * 名称
     */
    private String name;
    /**
     * 缩略图
     */
    private String img;
    /**
     * 更多轮播图，使用英文,间隔
     */
    @TableField("more_img")
    private String moreImg;
    /**
     * 详情HTML文件
     */
    @TableField("des_file")
    private String desFile;
    /**
     * 教练简介
     */
    private String des;
    /**
     * 单买定价
     */
    @TableField("price")
    private BigDecimal price;
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
     * 教练内部电话
     */
    private String telephone;
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 是否推荐
     */
    @TableField("if_recommend")
    private String ifRecommend;
    
    /**
     * 是否冻结  F:否,  T:是
     */
    @TableField("if_frozen")
    private String ifFrozen;

    /**
     * 评分
     */
    private Double score;
    /**
     * 累计评分
     */
    @TableField("total_score")
    private Double totalScore;
    /**
     * 被评价次数
     */
    @TableField("score_num")
    private Integer scoreNum;
    /**
     * 已预约次数
     */
    @TableField("sale_num")
    private Integer saleNum;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(Integer scoreNum) {
		this.scoreNum = scoreNum;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getCoachCatId() {
		return coachCatId;
	}

	public void setCoachCatId(Integer coachCatId) {
		this.coachCatId = coachCatId;
	}

	public String getCoachCatName() {
		return coachCatName;
	}

	public void setCoachCatName(String coachCatName) {
		this.coachCatName = coachCatName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIfFrozen() {
		return ifFrozen;
	}

	public void setIfFrozen(String ifFrozen) {
		this.ifFrozen = ifFrozen;
	}
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
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

    public String getMoreImg() {
        return moreImg;
    }

    public void setMoreImg(String moreImg) {
        this.moreImg = moreImg;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Coach{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", img=" + img +
        ", desFile=" + desFile +
        ", coachGradeId=" + coachGradeId +
        ", coachGradeName=" + coachGradeName +
        ", telephone=" + telephone +
        ", seqNum=" + seqNum +
        ", ifRecommend=" + ifRecommend +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
