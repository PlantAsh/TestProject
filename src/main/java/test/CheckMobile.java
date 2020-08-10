package test;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验手机号
 *
 * @author swu
 * @date 2020-08-10
 */
public class CheckMobile {

    /**
     * 模拟DB
     */
    private static final List<String> MOBILE_LIST = new ArrayList<>();

    /**
     * 中国移动：China Mobile
     * 13[4-9],147,148,15[0-2,7-9],165,170[3,5,6],172,178,18[2-4,7-8],19[5,7,8]
     */
    private static final String CM_NUM = "^((13[4-9])|(14[7-8])|(15[0-2,7-9])|(165)|(178)|(18[2-4,7-8])|(19[5,7,8]))\\d{8}|(170[3,5,6])\\d{7}$";

    /**
     * 中国联通：China Unicom
     * 130,131,132,145,146,155,156,166,167,170[4,7,8,9],171,175,176,185,186,196
     */
    private static final String CU_NUM = "^((13[0-2])|(14[5,6])|(15[5-6])|(16[6-7])|(17[1,5,6])|(18[5,6])|(196))\\d{8}|(170[4,7-9])\\d{7}$";

    /**
     * 中国电信：China Telecom
     * 133,149,153,162,170[0,1,2],173,174[0-5],177,180,181,189,19[0,1,3,9]
     */
    private static final String CT_NUM = "^((133)|(149)|(153)|(162)|(17[3,7])|(18[0,1,9])|(19[0,1,3,9]))\\d{8}|((170[0-2])|(174[0-5]))\\d{7}$";

    /**
     * 中国广电：China Broadcasting Network
     * 192
     */
    private static final String CBN_NUM = "^((192))\\d{8}$";

    public static String check(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "手机号为空";
        }
        mobile = mobile.replaceAll("\\s", "");
        byte[] all = mobile.getBytes();
        for (byte b : all) {
            if (b < 48 || b > 57) {
                return "本手机号无法注册，为非法手机号";
            }
        }

        if (!mobile.matches(CM_NUM) && !mobile.matches(CU_NUM) && !mobile.matches(CT_NUM) && !mobile.matches(CBN_NUM)) {
            return "此手机号码为中国大陆非法手机号码";
        }

        if (MOBILE_LIST.contains(mobile)) {
            return "此手机号已经被其他用户注册";
        }
        MOBILE_LIST.add(mobile);

        return "此手机号注册成功";
    }

    public static void main(String[] args) {
        System.out.println("138 1234 1234最终结果：" + check("138 1234 1234"));
        System.out.println("13812345abc最终结果：" + check("13812345abc"));
        System.out.println("13812345678最终结果：" + check("13812345678"));
        System.out.println("138 1234 5678最终结果：" + check("138 1234 5678"));
        System.out.println("98765432101最终结果：" + check("98765432101"));
    }

}
