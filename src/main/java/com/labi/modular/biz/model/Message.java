package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 短信消息模板
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    
    /**
     * 模板id
     */
    @TableField("template_id")
    private String templateId;
    /**
     * 内容
     */
    private String content;
    /**
     * 所属公司
     */
    private Integer type;
    
    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    


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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShowCat{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", createUser=" + createUser +
        ", createtime=" + createTime +
        "}";
    }
}
