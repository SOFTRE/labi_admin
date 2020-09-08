package com.labi.modular.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.labi.common.constant.Const;
import com.labi.modular.biz.dao.LProductAttrMapper;
import com.labi.modular.biz.dao.LProductMapper;
import com.labi.modular.biz.model.*;
import com.labi.modular.biz.service.LIProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LIProductServiceImpl implements LIProductService {

    @Autowired
    private LProductMapper lProductMapper;

    @Autowired
    private LProductAttrMapper productAttrMapper;

    @Override
    public List<Product> findProductList(Product product) {
        return lProductMapper.findProduct(product);
    }

    @Transactional(readOnly = false,rollbackFor=Exception.class)
    @Override
    public void saveProduct(Product product, String attrValueArrStr) {
        product.setSaleNum(0); // 有默认值,不用初始化
        product.setStatus(Const.ACTIVE);
        product.setIfRecommend(Const.FALSE);
        product.setCreatetime(new Date());
        product.setIfVideo(Const.FALSE);

//       if (ShiroKit.getUser().getDeptId()!=null&&!"".equals(ShiroKit.getUser().getDeptId())){
//           product.setType(ShiroKit.getUser().getDeptId());
//       }
        product.setOprtime(product.getCreatetime());
        lProductMapper.saveProduction(product);
        if (StringUtils.isNotBlank(attrValueArrStr)){
            JSONArray objects = JSON.parseArray(attrValueArrStr);
            for (Object object : objects) {
                JSONObject jsonObject = (JSONObject) object;
                ProdAttr prodAttr = new ProdAttr();
                prodAttr.setProdId(product.getId());
                prodAttr.setAttrId(jsonObject.getInteger("attrId"));
                prodAttr.setAttrOptionId(jsonObject.getInteger("valueId"));
                prodAttr.setAttrName(jsonObject.getString("attrName"));
                prodAttr.setOptionName(jsonObject.getString("optionName"));
                prodAttr.setCreatetime(new Date());
                prodAttr.setStatus(Const.ACTIVE);
                productAttrMapper.saveAttr(prodAttr);
            }
        }
    }

    @Override
    public Boolean deleteByIds(String ids) {
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : split) {
            idList.add(Integer.parseInt(s));
        }
        Integer num = lProductMapper.deleteBatch(idList);
        if (num == split.length){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public Boolean updateProduct(Product product, String attrValueArrStr) {
        product.setOprtime(new Date());
        Boolean updateProductSuccess = lProductMapper.updateProduct(product);
        JSONArray attrValueJSON = JSON.parseArray(attrValueArrStr);
        List<Integer> attrIdList = new ArrayList<>();
        for (Object object : attrValueJSON) {
            JSONObject attrValue = (JSONObject) object;
            Integer attrId = attrValue.getInteger("attrId");
            Integer valueId = attrValue.getInteger("valueId");
            String attrName = attrValue.getString("attrName");
            String optionName = attrValue.getString("optionName");
            ProdAttr prodAttr = new ProdAttr();
            prodAttr.setProdId(product.getId());
            prodAttr.setAttrOptionId(valueId);
            ProdAttr prodAttrOne = lProductMapper.selectOneAttr(prodAttr);
            if (prodAttrOne == null || "".equals(prodAttrOne)){
                ProdAttr prodAttr1 = new ProdAttr();
                prodAttr1.setProdId(product.getId());
                prodAttr1.setAttrId(attrId);
                prodAttr1.setAttrOptionId(valueId);
                prodAttr1.setAttrName(attrName);
                prodAttr1.setOptionName(optionName);
                prodAttr1.setStatus(Const.ACTIVE);
                prodAttr1.setCreatetime(product.getOprtime());
                lProductMapper.saveProdAttr(prodAttr1);
                attrIdList.add(prodAttr1.getId());
            }else {
                ProdAttr prodAttr1 = new ProdAttr();
                prodAttr1.setId(prodAttrOne.getId());
                prodAttr1.setAttrName(attrName);
                prodAttr1.setOptionName(optionName);
                prodAttr1.setStatus(Const.ACTIVE);
                lProductMapper.updateProductAttrById(prodAttr1);
                attrIdList.add(prodAttr1.getId());
            }
        }
       Boolean updateStatusSuccess =  lProductMapper.updateStatusByIds(product.getId(),Const.DELETE,attrIdList);

        if (updateProductSuccess&&updateStatusSuccess){
            return true;
        }

        return false;
    }

    @Override
    public List<ProdAttr> findProductListAttr(Integer prodId) {
        return productAttrMapper.findAttrsByProdId(prodId);
    }


}
