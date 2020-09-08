package com.labi.modular.biz.model;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商余额记录
 */
@TableName("biz_agent_balance_record")
public class BizAgentBalanceRecord implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 代理商id
     */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 涉及金额
     */
    @TableField("balance")
    private BigDecimal balance;
    /**
     * 类型：income（收入）、expend（支出）
     */
    @TableField("type")
    private String type;
    /**
     * 收支后剩余金额
     */
    @TableField("left_balance")
    private BigDecimal leftBalance;
    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
    /*
    * 创建时间
    */
    @TableField("createtime")
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLeftBalance() {
        return leftBalance;
    }

    public void setLeftBalance(BigDecimal leftBalance) {
        this.leftBalance = leftBalance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}