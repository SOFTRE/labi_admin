package com.labi.modular.biz.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 站内消息阅读
 */
@TableName("sys_notice_read")
public class NoticeRead extends Model<NoticeRead> {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 消息ID
     */
    @TableField("notice_id")
    private Long noticeId;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户类型 A:管理端 C:用户端
     */
    @TableField("user_type")
    private String userType;
    /**
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

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderLogistics{" +
                "id=" + id +
                ", createtime=" + createtime +
                "}";
    }
}