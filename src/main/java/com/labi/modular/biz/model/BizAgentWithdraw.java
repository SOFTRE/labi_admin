package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 分销商提现
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
@TableName("biz_agent_withdraw")
public class BizAgentWithdraw extends Model<BizAgentWithdraw> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 分销商姓名
     */
    @TableField("agent_name")
    private String agentName;
    /**
     * 手机号
     */
    @TableField("phone_num")
    private String phoneNum;
    /**
     * 提现金额
     */
    @TableField("total_amount")
    private Integer totalAmount;
    /**
     * 转账交易流水号
     */
    @TableField("exchange_num")
    private String exchangeNum;
    /**
     * 转账交易备注（拒绝原因等）
     */
    @TableField("exchange_bak")
    private String exchangeBak;
    /**
     * 银行卡号
     */
    @TableField("bank_no")
    private String bankNo;
    /**
     * 银行卡信息
     */
    @TableField("bank_info")
    private String bankInfo;
    /**
     * 进度：wait(等待处理)、success(成功)、fail(失败)
     */
    private String progress;
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(String exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public String getExchangeBak() {
        return exchangeBak;
    }

    public void setExchangeBak(String exchangeBak) {
        this.exchangeBak = exchangeBak;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BizAgentWithdraw{" +
                "id=" + id +
                ", agentId=" + agentId +
                ", agentName=" + agentName +
                ", totalAmount=" + totalAmount +
                ", exchangeNum=" + exchangeNum +
                ", exchangeBak=" + exchangeBak +
                ", bankNo=" + bankNo +
                ", bankInfo=" + bankInfo +
                ", progress=" + progress +
                ", createtime=" + createtime +
                ", oprtime=" + oprtime +
                "}";
    }
}
