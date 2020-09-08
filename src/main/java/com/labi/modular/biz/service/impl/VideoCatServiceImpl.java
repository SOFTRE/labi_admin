package com.labi.modular.biz.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.labi.modular.biz.dao.ProductMapper;
import com.labi.modular.biz.dao.VideoCatMapper;
import com.labi.modular.biz.model.VideoCat;
import com.labi.modular.biz.service.IVideoCatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 视频分类 服务实现类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
@Service
public class VideoCatServiceImpl extends ServiceImpl<VideoCatMapper, VideoCat> implements IVideoCatService {

    @Resource
    ProductMapper productMapper;

    /**
     * 修改分类信息
     *
     * @param videoCat
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByCatId(VideoCat videoCat) {
        videoCat.setOprtime(new Date());
        updateById(videoCat);
        productMapper.updateVideoCatNameByCatId(videoCat.getId(), videoCat.getName());
    }
}
