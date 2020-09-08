package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.ProdSku;

/**
 * <p>
 * 商品销量单位 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IProdSkuService extends IService<ProdSku> {

    Integer insertSku(ProdSku sku);

}
