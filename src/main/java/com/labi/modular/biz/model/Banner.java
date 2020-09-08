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
 * Banner
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_banner")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String img;
    /**
     * 类型： product（商品）、video（视频课程）、 course(课程报名)、coach(教练)
     */
    private String type;
    /**
     * 跳转的地址/指定对象的id
     */
    @TableField("goto_info")
    private String gotoInfo;
    /**
     * 是否上线
     */
    @TableField("if_online")
    private String ifOnline;
    /**
     * banner所属模块,   1首页 2课程报名 3教练预约 4拉比展示 5在线直播
     */
    private Integer position;
    /**
     * banner位置索引
     */
    private Integer indexes;
    
    /**
     * 所属公司
     */
    @TableField("gs_type")
    private Integer gsType;
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 操作时间
     */
    private Date oprtime;

    /**
     * 扩展字段
     */
    //描述
    @TableField(exist = false)
    private String infoDes;

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

    
    public Integer getGsType() {
		return gsType;
	}

	public void setGsType(Integer gsType) {
		this.gsType = gsType;
	}

	public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfoDes() {
		return infoDes;
	}

	public void setInfoDes(String infoDes) {
		this.infoDes = infoDes;
	}

	public String getGotoInfo() {
        return gotoInfo;
    }

    public void setGotoInfo(String gotoInfo) {
        this.gotoInfo = gotoInfo;
    }

    public String getIfOnline() {
        return ifOnline;
    }

    public void setIfOnline(String ifOnline) {
        this.ifOnline = ifOnline;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
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

    
    public Integer getIndexes() {
		return indexes;
	}

	public void setIndexes(Integer indexes) {
		this.indexes = indexes;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Banner{" +
        "id=" + id +
        ", name=" + name +
        ", img=" + img +
        ", type=" + type +
        ", gotoInfo=" + gotoInfo +
        ", ifOnline=" + ifOnline +
        ", position=" + position +
        ", indexes=" + indexes +
        ", seqNum=" + seqNum +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
    
    
}
