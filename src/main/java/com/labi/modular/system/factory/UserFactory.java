package com.labi.modular.system.factory;

import com.labi.modular.system.transfer.UserDto;
import com.labi.modular.system.model.User;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 *
 * @author lyr
 * @date 2017-05-05 22:43
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
