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
 * 新闻
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 缩略图
     */
    private String img;

    /**
    * 缩略图
    */
    @TableField("small_img")
    private String smallImg;
    /**
     * 更多图片
     */
    @TableField("more_img")
    private String moreImg; 
    /**
     * 标题
     */
    private String title;
    /**
     * 简要 
     */
    private String description;
    /**
     * 详情HTML文件
     */
    @TableField("des_file")
    private String desFile;
    /**
     * 视频文件URL
     */
    @TableField("video_file")
    private String videoFile;
    /**
     * 视频封面URL
     */
    @TableField("cover_file")
    private String coverFile;
    /**
     * 浏览量
     */
    private Integer pv;
    /**
     * 链接地址
     */
    @TableField("href_url")
    private String hrefUrl;
    /**
     * 类型，'selfUrl'内部链接,'outerUrl'外部链接
     */
    private String type;
    /**
     * 是否上线
     */
    @TableField("if_online")
    private String ifOnline;
    /**
     * 所属公司
     */
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoreImg() {
		return moreImg;
	}

	public void setMoreImg(String moreImg) {
		this.moreImg = moreImg;
	}

	public Integer getGsType() {
		return gsType;
	}

	public void setGsType(Integer gsType) {
		this.gsType = gsType;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIfOnline() {
        return ifOnline;
    }

    public void setIfOnline(String ifOnline) {
        this.ifOnline = ifOnline;
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

    public String getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	public String getCoverFile() {
		return coverFile;
	}

	public void setCoverFile(String coverFile) {
		this.coverFile = coverFile;
	}

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "News{" +
        "id=" + id +
        ", img=" + img +
        ", title=" + title +
        ", description=" + description +
        ", desFile=" + desFile +
        ", pv=" + pv +
        ", hrefUrl=" + hrefUrl +
        ", type=" + type +
        ", ifOnline=" + ifOnline +
        ", seqNum=" + seqNum +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
