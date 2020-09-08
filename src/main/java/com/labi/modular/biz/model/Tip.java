package com.labi.modular.biz.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 提示
 *
 * @author lyr
 * @date 2018-06-06 10:55:04
 */
@TableName("biz_tip")
public class Tip implements Serializable {
    /*
    * 主键id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /*
    * 标题
    */
    @TableField("title")
    private String title;
    /*
    * 编码
    */
    @TableField("code")
    private String code;
    /*
    * 详情HTML文件
    */
    @TableField("des_file")
    private String desFile;
    /*
    * 创建时间
    */
    @TableField("createtime")
    private Date createtime;
    /*
    * 操作时间
    */
    @TableField("oprtime")
    private Date oprtime;

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

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}