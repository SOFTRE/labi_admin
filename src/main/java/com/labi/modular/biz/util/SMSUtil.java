package com.labi.modular.biz.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import java.util.ArrayList;

/**
 * 发送短信信息
 *
 * @author lyr
 * @date 2018年5月16日 17:21:16
 */
public class SMSUtil {
    //短信签名
    public static String sign = "拉比天堂";
    /**
     * 分销商提现打款通知
     */
    public static int WITHDRAW_SUCCESS_MSG = 159386;

    /**
     * 分销商提现拒绝通知
     */
    public static int WITHDRAW_FAIL_MSG = 159432;

    /**
     * 分销商审核通过短信
     */
    public static int AGENT_APPLY_SUCCESS_MSG = 157775;

    /**
     * 分销商审核不通过短信
     */
    public static int AGENT_APPLY_FAIL_MSG = 157780;


    //腾讯云sms接口
    private static int appid = 1400089732;
    private static String appkey = "33d17aed2bb7d7b3d43f2a5f7d9f1478";

    //发送模板
    public static boolean sendMsg(String phoneNum, int templateId, ArrayList<String> params) {
        SmsSingleSender singleSender;
        try {
            singleSender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult singleSenderResult;
            singleSenderResult = singleSender.sendWithParam("86", phoneNum, templateId, params, sign, "", "");
//	    	System.out.println(singleSenderResult);
//	    	System.out.println("-------------SMS------------" + phoneNum + "----"  + "-------------SMS------------");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 分销商体现管理 同意后短信提示
     * {1},提现{2}元已通过审核，财务人员将为您打款，请注意查收
     *
     * @param phoneNum
     * @param name
     * @param money
     * @return
     */
    public static boolean sendWithdrawSuccessMsg(String phoneNum, String name, int money) {
        return sendWithdrawMsg(phoneNum, name, money, WITHDRAW_SUCCESS_MSG);
    }

    /**
     * 分销商提现拒绝通知短信
     * {1},提现{2}元未通过审核，请知悉
     *
     * @param phoneNum
     * @param name
     * @param money
     * @return
     */
    public static boolean sendWithdrawFailMsg(String phoneNum, String name, int money) {
        return sendWithdrawMsg(phoneNum, name, money, WITHDRAW_FAIL_MSG);
    }

    public static boolean sendWithdrawMsg(String phoneNum, String name, int money, int templateId) {

        //初始化单发
        SmsSingleSender singleSender;
        try {
            singleSender = new SmsSingleSender(appid, appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(name);
            params.add(String.valueOf(money));
            singleSender.sendWithParam("86", phoneNum, templateId, params, sign, "", "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 分销商审核通过短信
     * {1},恭喜您已通过审核，成为拉比天堂的分销商！
     *
     * @param phoneNum
     * @param name
     * @return
     */
    public static boolean sendAgentApplySuccessMsg(String phoneNum, String name) {
        return sendAgentApplyMsg(phoneNum, name, AGENT_APPLY_SUCCESS_MSG);
    }

    /**
     * 分销商审核通过短信
     * {1},恭喜您已通过审核，成为拉比天堂的分销商！
     *
     * @param phoneNum
     * @param name
     * @return
     */
    public static boolean sendAgentApplyFailMsg(String phoneNum, String name) {
        return sendAgentApplyMsg(phoneNum, name, AGENT_APPLY_FAIL_MSG);
    }

    public static boolean sendAgentApplyMsg(String phoneNum, String name, int templateId) {

        //初始化单发
        SmsSingleSender singleSender;
        try {
            singleSender = new SmsSingleSender(appid, appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(name);
            singleSender.sendWithParam("86", phoneNum, templateId, params, sign, "", "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}