package test;

import java.util.Calendar;
import java.util.Date;

/**
 * 年龄计算工具
 *
 * @author swu
 * @date 2020-05-20
 */
public class AgeUtils {

    /**
     * 中转
     *
     * @param code
     * @param birthDate
     * @return
     */
    public static String transit(String code, String birthDate) {
        return transit(code, DateUtils.parse(birthDate));
    }

    /**
     * 中转
     *
     * @param code
     * @param birthDate
     * @return
     */
    public static String transit(String code, Date birthDate) {
        return AgeUtils.calAge(birthDate);
    }

    /**
     * 计算周岁，默认6岁以上不显示月 格式(yyyy-MM-dd/yyyy-MM-dd HH:mm:ss/yyyy-MM-dd'T'HH:mm:ss'Z')
     *
     * @param birthDate
     * @return
     */
    public static String calAge(String birthDate) {
        return calAge(DateUtils.parse(birthDate));
    }

    /**
     * 计算周岁，age自定义几岁以上不显示月 格式(yyyy-MM-dd/yyyy-MM-dd HH:mm:ss/yyyy-MM-dd'T'HH:mm:ss'Z')
     *
     * @param birthDate
     * @return
     */
    public static String calAge(String birthDate, int age) {
        return calAge(DateUtils.parse(birthDate), age);
    }

    /**
     * 计算周岁，默认6岁以上不显示月
     *
     * @param birthDate
     * @return
     */
    public static String calAge(Date birthDate) {
        return calAge(birthDate, 6);
    }

    /**
     * 计算周岁，age自定义几岁以上不显示月
     *
     * @param birthDate
     * @param age
     * @return
     */
    public static String calAge(Date birthDate, int age) {
        if (birthDate == null) {
            return "";
        }

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        long dayBirth = birth.get(Calendar.DAY_OF_MONTH);
        long dayNow = now.get(Calendar.DAY_OF_MONTH);
        now.add(Calendar.MONTH, -1);
        long birthDay = dayNow - dayBirth >= 0 ? dayNow - dayBirth : dayNow - dayBirth + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        long allMonth = betweenMonth(birthDate, new Date());
        if (allMonth < 1) {
            return birthDay + "天";
        } else if (allMonth < 12) {
            return allMonth + "个月" + (birthDay != 0 ? (birthDay > 0 ? birthDay : dayNow) + "天" : "");
        } else if (allMonth < age * 12) {
            return (allMonth / 12) + "岁" + ((allMonth % 12) > 0 ? (allMonth % 12) + "个月" : "");
        } else {
            return (allMonth / 12) + "岁";
        }
    }

    /**
     * 计算月份差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private static int betweenMonth(Date startTime, Date endTime) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startTime);
        end.setTime(endTime);

        int yearStart = start.get(Calendar.YEAR); //获取年
        int yearEnd = end.get(Calendar.YEAR);
        int monthStart = start.get(Calendar.MONTH); //月份
        int monthEnd = end.get(Calendar.MONTH);
        int dayStart = start.get(Calendar.DAY_OF_MONTH); //日
        int dayEnd = end.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = yearEnd - yearStart;
        if (monthEnd < monthStart || (monthStart == monthEnd && dayEnd < dayStart))
            yearInterval--;
        // 获取月数差值
        int monthInterval = (monthEnd + 12) - monthStart;
        if (dayEnd < dayStart)
            monthInterval--;
        monthInterval %= 12;
        return Math.abs(yearInterval * 12 + monthInterval);
    }

}
