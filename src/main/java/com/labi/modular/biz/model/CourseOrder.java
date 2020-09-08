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
 * 课程报名
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_course_order")
public class CourseOrder extends Model<CourseOrder> {

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
     * 付款用户手机号
     */
    @TableField("pay_user_phone_num")
    private String payUserPhoneNum;
    /**
     * 学员名称
     */
    @TableField("user_name")
    private String userName;
    
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 手机号
     */
    @TableField("phone_num")
    private String phoneNum;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 详细地址
     */
    @TableField("address_detail")
    private String addressDetail;
    /**
     * 行业
     */
    private String industry;
    /**
     * 职位
     */
    private String job;
    /**
     * 推荐人手机号
     */
    @TableField("ref_phone_num")
    private String refPhoneNum;
    /**
     * 推荐人姓名
     */
    @TableField("ref_name")
    private String refName;
    /**
     * 
     */
    @TableField("ref_class_name")
    private String refClassName;
    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;
    /**
     * 课程简介
     */
    @TableField("course_des")
    private String courseDes;
    /**
     * 缩略图
     */
    @TableField("course_img")
    private String courseImg;
    /**
     * 上课时间ID
     */
    @TableField("course_time_id")
    private Integer courseTimeId;
    /**
     * 上课日期
     */
    @TableField("listen_time")
    private Date listenTime;
    /**
     * 调整后上课时间ID
     */
    @TableField("adjust_course_time_id")
    private Integer adjustCourseTimeId;
    /**
     * 调整后上课日期(学员上课时间以该字段为准)
     */
    @TableField("adjust_listen_time")
    private Date adjustListenTime;
    
    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
    /**
     * 课程分类id
     */
    @TableField("course_cat_id")
    private Integer courseCatId;
    /**
     * 课程分类名称
     */
    @TableField("course_cat_name")
    private String courseCatName;
    
    /**
     * 班级分类id
     */
    @TableField("class_id")
    private Integer classId;
    
    /**
     * 班级名称
     */
    @TableField("class_name")
    private String className;
    /**
     * 总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;

    /*
    * 是否分成
    */
    @TableField("if_divided")
    private String ifDivided;
    /**
     * 订单进度：waitPay(待支付)、waitListen(待听课)、finish(已完成)
     */
    private String progress;
    /*
    * 是否发送15天提示短信
    */
    @TableField("if_send_msg15")
    private String ifSendMsg15;

    /*
    * 是否取消
    */
    @TableField("if_cancel")
    private String ifCancel;
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

    public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getRefPhoneNum() {
		return refPhoneNum;
	}

	public void setRefPhoneNum(String refPhoneNum) {
		this.refPhoneNum = refPhoneNum;
	}

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefClassName() {
		return refClassName;
	}

	public void setRefClassName(String refClassName) {
		this.refClassName = refClassName;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDes() {
        return courseDes;
    }

    public void setCourseDes(String courseDes) {
        this.courseDes = courseDes;
    }

    public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public Date getListenTime() {
        return listenTime;
    }

    public void setListenTime(Date listenTime) {
        this.listenTime = listenTime;
    }

    public Date getAdjustListenTime() {
        return adjustListenTime;
    }

    public void setAdjustListenTime(Date adjustListenTime) {
        this.adjustListenTime = adjustListenTime;
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

    public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
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

    public String getIfDivided() {
        return ifDivided;
    }

    public void setIfDivided(String ifDivided) {
        this.ifDivided = ifDivided;
    }

    public Integer getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(Integer courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public Integer getAdjustCourseTimeId() {
        return adjustCourseTimeId;
    }

    public void setAdjustCourseTimeId(Integer adjustCourseTimeId) {
        this.adjustCourseTimeId = adjustCourseTimeId;
    }

    public String getIfSendMsg15() {
        return ifSendMsg15;
    }

    public void setIfSendMsg15(String ifSendMsg15) {
        this.ifSendMsg15 = ifSendMsg15;
    }

    public String getIfCancel() {
        return ifCancel;
    }

    public void setIfCancel(String ifCancel) {
        this.ifCancel = ifCancel;
    }

    public String getPayUserPhoneNum() {
        return payUserPhoneNum;
    }

    public void setPayUserPhoneNum(String payUserPhoneNum) {
        this.payUserPhoneNum = payUserPhoneNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CourseOrder{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", userId=" + userId +
        ", userName=" + userName +
        ", courseName=" + courseName +
        ", courseDes=" + courseDes +
        ", courseImg=" + courseImg +
        ", listenTime=" + listenTime +
        ", adjustListenTime=" + adjustListenTime +
        ", courseCatId=" + courseCatId +
        ", courseCatName=" + courseCatName +
        ", totalPrice=" + totalPrice +
        ", progress=" + progress +
        ", payWay=" + payWay +
        ", remarks=" + remarks +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
