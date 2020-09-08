package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_user")
public class BizUser extends Model<BizUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 头像
     */
    @TableField("head_img")
    private String headImg;
    /**
     * 昵称
     */
    private String name;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 手机号
     */
    @TableField("phone_num")
    private String phoneNum;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 是否教练:    F:否,  T:是
     */
    @TableField("if_coach")
    private String ifCoach;
    /**
     * 是否分销商:    F:否,  T:是
     */
    @TableField("agent_status")
    private String agentStatus;
    /*
    * 代理商id
    */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 推荐者id
     */
    @TableField("ref_user_id")
    private Integer refUserId;
    /**
     * 上级推荐者id
     */
    @TableField("root_user_id")
    private Integer rootUserId;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 身份证号
     */
    @TableField("card_id")
    private String cardId;
    /**
     * 银行卡号
     */
    @TableField("bank_no")
    private String bankNo;
    /**
     * 银行卡开户行
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * 银行卡开户行支行
     */
    @TableField("bank_subname")
    private String bankSubname;
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
     * 家庭住址的街道乡镇等详细地址
     */
    @TableField("address_detail")
    private String addressDetail;
    private String industry;

    private String job;
    /**
     * 微信用户唯一标识
     */
    private String openid;
    /**
     * 是否冻结:    F:否,  T:是
     */
    @TableField("if_frozen")
    private String ifFrozen;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 操作时间
     */
    private Date oprtime;

    @TableField(exist = false)
    private List<BizUser> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIfCoach() {
        return ifCoach;
    }

    public void setIfCoach(String ifCoach) {
        this.ifCoach = ifCoach;
    }


    public Integer getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(Integer refUserId) {
        this.refUserId = refUserId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankSubname() {
        return bankSubname;
    }

    public void setBankSubname(String bankSubname) {
        this.bankSubname = bankSubname;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIfFrozen() {
        return ifFrozen;
    }

    public void setIfFrozen(String ifFrozen) {
        this.ifFrozen = ifFrozen;
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

    public Integer getRootUserId() {
        return rootUserId;
    }

    public void setRootUserId(Integer rootUserId) {
        this.rootUserId = rootUserId;
    }

    public List<BizUser> getChildren() {
        return children;
    }

    public void setChildren(List<BizUser> children) {
        this.children = children;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", headImg=" + headImg +
        ", name=" + name +
        ", sex=" + sex +
        ", birthday=" + birthday +
        ", phoneNum=" + phoneNum +
        ", password=" + password +
        ", salt=" + salt +
        ", ifCoach=" + ifCoach +
        ", refUserId=" + refUserId +
        ", realName=" + realName +
        ", cardId=" + cardId +
        ", bankNo=" + bankNo +
        ", bankName=" + bankName +
        ", bankSubname=" + bankSubname +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", addressDetail=" + addressDetail +
        ", openid=" + openid +
        ", ifFrozen=" + ifFrozen +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
