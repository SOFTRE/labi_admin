package com.labi.modular.biz.service;

import com.labi.modular.biz.model.Product;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IProductService extends IService<Product> {

    void insertProduct(Product p,String attrValueArrStr) throws Exception;

    void updateProduct(Product p,String attrValueArrStr) throws Exception;

    void deleteBatch(String ids) throws Exception;
}
