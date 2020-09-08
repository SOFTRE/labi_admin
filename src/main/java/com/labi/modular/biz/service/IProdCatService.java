package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.ProdCat;

/**
 * <p>
 * 商品类目表 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IProdCatService extends IService<ProdCat> {

    void updateByCatId(ProdCat prodCat);
}
