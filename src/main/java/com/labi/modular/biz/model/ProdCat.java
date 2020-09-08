package com.labi.modular.biz.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品类目表
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@TableName("biz_prod_cat")
public class ProdCat extends Model<ProdCat> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类目名称
     */
    @TableField("cat_name")
    private String catName;
    /**
     * 图片
     */
    private String img;
    /**
     * 父id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 根id
     */
    @TableField("root_id")
    private Integer rootId;
    /**
     * 深度
     */
    private Integer depth;
    /**
     * 路径
     */
    private String path;
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
    * 推荐图片
    */
    @TableField("recommend_img")
    private String recommendImg;
    /**
    * 分类推荐商品ID
    */
    @TableField("recommend_prod_id")
    private String recommendProdId;
    /**
     * 推荐商品名称
     */
    @TableField("recommend_prod_name")
    private String recommendProdName;
    /**
    * 是否推荐
    */
    @TableField("if_recommend")
    private String ifRecommend;

    /*
     * 首页推荐图片
     */
    @TableField("recommend_home_img")
    private String recommendHomeImg;
    /*
    * 首页分类推荐商品ID
    */
    @TableField("recommend_home_prod_id")
    private String recommendHomeProdId;
    /*
    * 首页分类推荐商品名称
    */
    @TableField("recommend_home_prod_name")
    private String recommendHomeProdName;
    /*
    * 是否首页推荐
    */
    @TableField("if_home_recommend")
    private String ifHomeRecommend;
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

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRecommendImg() {
        return recommendImg;
    }

    public void setRecommendImg(String recommendImg) {
        this.recommendImg = recommendImg;
    }

    public String getRecommendProdId() {
        return recommendProdId;
    }

    public void setRecommendProdId(String recommendProdId) {
        this.recommendProdId = recommendProdId;
    }

    public String getIfRecommend() {
        return ifRecommend;
    }

    public void setIfRecommend(String ifRecommend) {
        this.ifRecommend = ifRecommend;
    }

    public String getRecommendProdName() {
        return recommendProdName;
    }

    public void setRecommendProdName(String recommendProdName) {
        this.recommendProdName = recommendProdName;
    }

    public String getRecommendHomeImg() {
        return recommendHomeImg;
    }

    public void setRecommendHomeImg(String recommendHomeImg) {
        this.recommendHomeImg = recommendHomeImg;
    }

    public String getRecommendHomeProdId() {
        return recommendHomeProdId;
    }

    public void setRecommendHomeProdId(String recommendHomeProdId) {
        this.recommendHomeProdId = recommendHomeProdId;
    }

    public String getRecommendHomeProdName() {
        return recommendHomeProdName;
    }

    public void setRecommendHomeProdName(String recommendHomeProdName) {
        this.recommendHomeProdName = recommendHomeProdName;
    }

    public String getIfHomeRecommend() {
        return ifHomeRecommend;
    }

    public void setIfHomeRecommend(String ifHomeRecommend) {
        this.ifHomeRecommend = ifHomeRecommend;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProdCat{" +
        "id=" + id +
        ", catName=" + catName +
        ", img=" + img +
        ", parentId=" + parentId +
        ", rootId=" + rootId +
        ", depth=" + depth +
        ", path=" + path +
        ", seqNum=" + seqNum +
        ", status=" + status +
        ", createtime=" + createtime +
        ", oprtime=" + oprtime +
        "}";
    }
}
