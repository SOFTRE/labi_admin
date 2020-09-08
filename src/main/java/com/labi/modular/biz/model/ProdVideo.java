package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 在线课程视频
 * </p>
 *
 * @author lyr123
 * @since 2018-05-22
 */
@TableName("biz_prod_video")
public class ProdVideo extends Model<ProdVideo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品ID
     */
    @TableField("prod_id")
    private Integer prodId;
    /**
     * 视频名称
     */
    private String name;
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
     * 视频大小(字节)
     */
    @TableField("video_size")
    private Long videoSize;

    /**
     * 排序
     */
    @TableField("seq_num")
    private Integer seqNum;
    /**
     * 状态
     */
    private String status;
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

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(Long videoSize) {
        this.videoSize = videoSize;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
