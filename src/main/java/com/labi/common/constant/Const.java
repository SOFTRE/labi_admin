package com.labi.common.constant;

/**
 * 系统常量
 *
 * @author lyr
 * @date 2017年2月12日 下午9:42:53
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 是否管理员
     */
    Integer IS_ADMIN_FLG=0;
    
    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";


    /**
     * 是否上线  T：是
     */
    String TRUE = "T";

    /**
     * 是否上线  F：否
     */
    String FALSE = "F";

    /**
     * 状态 A：正常
     */
    String  ACTIVE = "A";

    /**
     * 状态 D：删除
     */
    String DELETE = "D";

    String WAIT = "wait";

    String SUCCESS = "success";

    String FAIL = "fail";
    /**
     * 无意义的字段值
     */
    int UNKNOWN = -1;

    /***
     * 通知管理 1小程序 2后台管理
     */
    Integer TYPE_XCU=1;
    Integer TYPE_HTGL=2;

    /**
     * expend
     */
    String EXPEND = "expend";

    /**
     * income
     */
    String INCOME = "income";
}
