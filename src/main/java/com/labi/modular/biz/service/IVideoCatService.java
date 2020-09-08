package com.labi.modular.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.labi.modular.biz.model.VideoCat;

/**
 * <p>
 * 视频分类 服务类
 * </p>
 *
 * @author lyr123
 * @since 2018-05-14
 */
public interface IVideoCatService extends IService<VideoCat> {

    void updateByCatId(VideoCat videoCat);

}
