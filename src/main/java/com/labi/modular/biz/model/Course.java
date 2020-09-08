package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 班级前缀名
     */
    @TableField("class_prefix")
    private String classPrefix;
    /**
     * 课程简介
     */
    private String des;
    /**
     * 缩略图
     */
    private String img;
    /**
     * 
     */
    @TableField("more_img")
    private String moreImg;
    /**
     * 详情HTML文件
     */
    @TableField("des_file")
    private String desFile;
    /**
     * 开始时间
     */
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 分类id
     */
    @TableField("course_cat_id")
    private Integer courseCatId;
    /**
     * 分类名称
     */
    @TableField("course_cat_name")
    private String courseCatName;
    /**
     * 课程价格
     */
    private BigDecimal price;
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 最大报名人数
     */
    @TableField("max_num")
    private Integer maxNum;

    /**
     * 已报名人数
     */
    @TableField("sale_num")
    private Integer saleNum;
    /**
     * 是否推荐
     */
    @TableField("if_recommend")
    private String ifRecommend;
    /**
     * 是否上架:    F:否,  T:是
     */
    @TableField("if_shelf")
    private String ifShelf;
    /**
     * A:正常 D:删除
     */
    private String status;
    
    /**
     * 所属公司
     */
    private  Integer type;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMoreImg() {
		return moreImg;
	}

	public void setMoreImg(String moreImg) {
		this.moreImg = moreImg;
	}

	public String getClassPrefix() {
        return classPrefix;
    }

    public void setClassPrefix(String classPrefix) {
        this.classPrefix = classPrefix;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
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

    public Integer getCourseCatId() {
        return courseCatId;
    }

    public void setCourseCatId(Integer courseCatId) {
        this.courseCatId = courseCatId;
    }

    public String getCourseCatName() {
        return courseCatName;
    }

    public void setCourseCatName(String courseCatName) {
        this.courseCatName = courseCatName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
    }

    public String getIfShelf() {
        return ifShelf;
    }

    public void setIfShelf(String ifShelf) {
        this.ifShelf = ifShelf;
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

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        "id=" + id +
        ", name=" + name +
        ", classPrefix=" + classPrefix +
        ", des=" + des +
        ", img=" + img +
        ", desFile=" + desFile +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", courseCatId=" + courseCatId +
        ", courseCatName=" + courseCatName +
        ", price=" + price +
        ", seqNum=" + seqNum +
        ", saleNum=" + saleNum +
        ", ifRecommend=" + ifRecommend +
        ", ifShelf=" + ifShelf +
        ", status=" + status +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
