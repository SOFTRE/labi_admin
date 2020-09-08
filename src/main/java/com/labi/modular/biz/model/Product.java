package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类id
     */
    @TableField("cat_id")
    private Integer catId;
    /**
     * 分类名
     */
    @TableField("cat_name")
    private String catName;
    /**
     * 供货商id
     */
    @TableField("supplier_id")
    private Integer supplierId;
    /**
     * 名称
     */
    private String name;
    /**
     * 名称
     */
    private String des;

    /**
     * 缩略图
     */
    @TableField("thumb_img")
    private String thumbImg;
    /**
     * 更多轮播图，使用英文,间隔
     */
    @TableField("more_img")
    private String moreImg;
    /**
     * 展示属性;号分隔
     */
    @TableField("exhibition_attr")
    private String exhibitionAttr;
    /**
     * 商品详情HTML文件绝对路径
     */
    @TableField("des_file")
    private String desFile;
    /**
     * 累计销量
     */
    @TableField("sale_num")
    private Integer saleNum;
    /**
     * 现价/售价
     */
    @TableField("sale_price")
    private BigDecimal salePrice;
    /**
     * 原价
     */
    @TableField("origin_price")
    private BigDecimal originPrice;
    /**
     * 是否免费视频:  F:否,  T:是
     */
    @TableField("if_free")
    private String ifFree;
    /**
     * 是否视频:  F:否,  T:是
     */
    @TableField("if_video")
    private String ifVideo;
    /**
     * 是否推荐:   F:否,  T:是
     */
    @TableField("if_recommend")
    private String ifRecommend;
    /**
     * 推荐后，首页展示的推荐图
     */
    @TableField("recommend_img")
    private String recommendImg;
    /**
     * 是否上架:    F:否,  T:是
     */
    @TableField("if_shelf")
    private String ifShelf;
    /**
     * 状态
     */
    @TableField("status")
    private String status;
    
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

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getMoreImg() {
        return moreImg;
    }

    public void setMoreImg(String moreImg) {
        this.moreImg = moreImg;
    }

    public String getExhibitionAttr() {
        return exhibitionAttr;
    }

    public void setExhibitionAttr(String exhibitionAttr) {
        this.exhibitionAttr = exhibitionAttr;
    }

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public String getIfVideo() {
        return ifVideo;
    }

    public void setIfVideo(String ifVideo) {
        this.ifVideo = ifVideo;
    }

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
    }

    public String getRecommendImg() {
        return recommendImg;
    }

    public void setRecommendImg(String recommendImg) {
        this.recommendImg = recommendImg;
    }

    public String getIfShelf() {
        return ifShelf;
    }

    public void setIfShelf(String ifShelf) {
        this.ifShelf = ifShelf;
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

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public String getIfFree() {
        return ifFree;
    }

    public void setIfFree(String ifFree) {
        this.ifFree = ifFree;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
        "id=" + id +
        ", catId=" + catId +
        ", catName=" + catName +
        ", name=" + name +
        ", thumbImg=" + thumbImg +
        ", moreImg=" + moreImg +
        ", exhibitionAttr=" + exhibitionAttr +
        ", desFile=" + desFile +
        ", saleNum=" + saleNum +
        ", ifVideo=" + ifVideo +
        ", ifRecommend=" + ifRecommend +
        ", recommendImg=" + recommendImg +
        ", ifShelf=" + ifShelf +
        ", seqNum=" + seqNum +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
