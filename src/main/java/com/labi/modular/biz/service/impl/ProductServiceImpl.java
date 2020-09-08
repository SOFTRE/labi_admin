package com.labi.modular.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.common.constant.Const;
import com.labi.core.shiro.ShiroKit;
import com.labi.modular.biz.dao.ProdAttrMapper;
import com.labi.modular.biz.dao.ProductMapper;
import com.labi.modular.biz.model.ProdAttr;
import com.labi.modular.biz.model.Product;
import com.labi.modular.biz.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
@Transactional(readOnly = true,rollbackFor=Exception.class)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Resource
    ProductMapper productMapper;

    @Resource
    ProdAttrMapper prodAttrMapper;

    /**
     * 插入商品
     * @param product
     * @param attrValueArrStr
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor=Exception.class)
    @Override
    public void insertProduct(Product product,String attrValueArrStr) throws Exception {

        product.setSaleNum(0);
        product.setStatus(Const.ACTIVE);
        product.setIfRecommend(Const.FALSE);
        product.setCreatetime(new Date());
        product.setIfVideo(Const.FALSE);
        product.setType(ShiroKit.getUser().getDeptId()); //所属公司
        productMapper.insert(product);
        JSONArray attrs = JSON.parseArray(attrValueArrStr);
        for(Object obj:attrs){
            JSONObject json = (JSONObject)obj;
            ProdAttr attr = new ProdAttr();
            attr.setProdId(product.getId());
            attr.setAttrId(json.getInteger("attrId"));
            attr.setAttrOptionId(json.getInteger("valueId"));
            attr.setAttrName(json.getString("attrName"));
            attr.setOptionName(json.getString("optionName"));
            attr.setCreatetime(new Date());
            attr.setStatus(Const.ACTIVE);
            prodAttrMapper.insert(attr);
        }
    }

    /**
     * 修改商品
     * @param product
     * @param attrValueArrStr
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor=Exception.class)
    @Override
    public void updateProduct(Product product,String attrValueArrStr) throws Exception {

        product.setOprtime(new Date());
        productMapper.updateById(product);

        JSONArray arr = JSON.parseArray(attrValueArrStr);
        List<Integer> attrIds = new ArrayList<>();
        for(Object obj:arr){
            JSONObject json = (JSONObject)obj;
            Integer attrId = json.getInteger("attrId");
            Integer valueId = json.getInteger("valueId");
            String attrName = json.getString("attrName");
            String optionName = json.getString("optionName");
            Wrapper<ProdAttr> wrapper = new EntityWrapper<>();
            wrapper.orderBy("createtime",false);
            //查询状态为A的数据
            wrapper.eq("prod_id", product.getId());
            wrapper.eq("attr_id", attrId);
            wrapper.eq("attr_option_id", valueId);

            List<ProdAttr> attrs = prodAttrMapper.selectList(wrapper);

            if(attrs.isEmpty()){
                ProdAttr attr = new ProdAttr();
                attr.setProdId(product.getId());
                attr.setAttrId(attrId);
                attr.setAttrOptionId(valueId);
                attr.setAttrName(attrName);
                attr.setOptionName(optionName);
                attr.setStatus(Const.ACTIVE);
                attr.setCreatetime(new Date());
                prodAttrMapper.insert(attr);
                attrIds.add(attr.getId());
            } else {
                ProdAttr attr = new ProdAttr();
                attr.setId(attrs.get(0).getId());
                attr.setAttrName(attrName);
                attr.setOptionName(optionName);
                attr.setStatus(Const.ACTIVE);

                prodAttrMapper.updateById(attr);
                attrIds.add(attrs.get(0).getId());
            }
        }
        prodAttrMapper.updateStatusByIds(Const.DELETE,product.getId(),attrIds);

    }

    /**
     * 批量删除商品
     * @param ids
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor=Exception.class)
    @Override
    public void deleteBatch(String ids) throws Exception {
        productMapper.deleteBatchByIds(","+ids+",");
    }
}
