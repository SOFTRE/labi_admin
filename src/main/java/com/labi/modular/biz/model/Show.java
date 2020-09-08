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
 * 拉比展示
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_show")
public class Show extends Model<Show> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类id
     */
    @TableField("cat_id")
    private Integer catId;
    /**
     * 缩略图
     */
    private String img;
    
    /**
     * 多图
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
     * 是否推荐
     */
    @TableField("if_recommend")
    private String ifRecommend;
    /**
     * 是否上线
     */
    @TableField("if_online")
    private String ifOnline;
    /**
     * 数据状态
     */
    private String  status;
    /**
     * 所属公司
     */
    private Integer type;
    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 是否播放视频
     */
    @TableField("if_video")
    private String ifVideo;
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

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
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

    public String getMoreImg() {
		return moreImg;
	}

	public void setMoreImg(String moreImg) {
		this.moreImg = moreImg;
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

	public String getIfVideo() {
		return ifVideo;
	}

	public void setIfVideo(String ifVideo) {
		this.ifVideo = ifVideo;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Show{" +
        "id=" + id +
        ", catId=" + catId +
        ", img=" + img +
        ", title=" + title +
        ", description=" + description +
        ", desFile=" + desFile +
        ", ifRecommend=" + ifRecommend +
        ", ifOnline=" + ifOnline +
        ", seqNum=" + seqNum +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
