package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * <p>
 * 分销商
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
@TableName("biz_agent")
public class BizAgent extends Model<BizAgent> {

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
     * 分销商姓名
     */
    @TableField("agent_name")
    private String agentName;
    /**
     * 合约开始时间
     */
    @TableField("begin_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    /**
     * 合约结束时间
     */
    @TableField("end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 累计收益
     */
    @TableField("total_balance")
    private BigDecimal totalBalance;
    /**
     * 累计邀请
     */
    @TableField("total_sub_user")
    private Integer totalSubUser;
    /**
     * 推广订单
     */
    @TableField("total_order")
    private Integer totalOrder;
    /**
     * 是否冻结
     */
    @TableField("if_frozen")
    private String ifFrozen;
    /**
     *进度：wait(等待审核)、success(成功)、fail(失败)
     */
    private String progress;
    /**
     * 备注信息，拒绝理由
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 操作时间
     */
    private Date oprtime;

    private String areaid;

    private String cityid;

    private String provinceid;
    /*
   * 省
   */
    private String province;
    /*
    * 市
    */
    private String city;
    /*
    * 区
    */
    private String area;

    /*
    * 家庭住址的街道乡镇等详细地址
    */
    @TableField("address_detail")
    private String addressDetail;

    @TableField(exist = false)
    private String phoneNum;
    @TableField(exist = false)
    private String bankNo;
    @TableField(exist = false)
    private String bankName;
    @TableField(exist = false)
    private String bankSubname;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

	public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Integer getTotalSubUser() {
        return totalSubUser;
    }

    public void setTotalSubUser(Integer totalSubUser) {
        this.totalSubUser = totalSubUser;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getIfFrozen() {
        return ifFrozen;
    }

    public void setIfFrozen(String ifFrozen) {
        this.ifFrozen = ifFrozen;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BizAgent{" +
        "id=" + id +
        ", userId=" + userId +
        ", agentName=" + agentName +
        ", balance=" + balance +
        ", totalBalance=" + totalBalance +
        ", totalSubUser=" + totalSubUser +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
