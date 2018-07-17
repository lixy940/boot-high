package com.lixy.boothigh.utils;

import java.util.Date;

/**
 * @Author: MR LIS
 * @Description:时间距离展示工具
/*  时间不足1分钟，以“N秒前”计算；
    时间超过1分钟、不足1小时，以“N分钟前”计算；
    时间超过1小时、当天0点之后的，以“N小时前”计算；
    时间在当天0点之前的，昨天0点之后的，以“昨天”显示；
    时间在昨天0点之前的，前天0点之后的，以“前天”显示；
    时间在前天0点之前的，当年范围内的，以“N月N日”显示；
    时间在去年及以前的，以“N年N月N日”显示；
 * @Date: Create in 10:43 2018/7/17
 * @Modified By:
 */
public class ShowTimeTools {


    /**
     * 根据时间字符串，进行时间的展示
     *
     * @param dateTimeStr
     * @return
     * @Author: MR LIS
     * @Date: 10:46 2018/7/17
     */
    public static String calculateTime(String dateTimeStr) {

        /**
         *输入时间
         */
        Date inputDate = DateTimeUtil.parse(dateTimeStr, DateTimeUtil.FORMAT_TIME);

        /**
         * 当前时间
         */
        String currentTimeStr = DateTimeUtil.getCurrentTime();

        /**
         * 当天00:00:00
         */
        Date currentZeroDate = DateTimeUtil.parse(currentTimeStr.split(" ")[0] + " 00:00:00", DateTimeUtil.FORMAT_TIME);

        //输入时间分割得到对应的年月日数组
        String[] inputDateArr = dateTimeStr.split(" ")[0].split("-");
        String[] currentDateArr = currentTimeStr.split(" ")[0].split("-");

        /**
         * 是否超过一年
         */
        Integer diffYear = Integer.parseInt(currentDateArr[0])-Integer.parseInt(inputDateArr[0]) ;
        if (diffYear > 0) {
            return inputDateArr[0] + "年" + inputDateArr[1] + "月" + inputDateArr[2] + "日";
        }

        //计算前天
        Date after2Days = DateTimeUtil.addDay(inputDate, 2);
        //判断是否在前天0点之前
        if (after2Days.before(currentZeroDate)) {
            return inputDateArr[1] + "月" + inputDateArr[2] + "日";
        }

        Date after1Days = DateTimeUtil.addDay(inputDate, 1);
        //判断是否在昨天0点之前
        if (after1Days.before(currentZeroDate)) {
            return "前天";
        }

        /**
         * 判断是否在当天0点之前，昨天0点之后
         */
        if (inputDate.before(currentZeroDate)) {
            return "昨天";
        }


        //在当天，计算时分秒

        //时分秒的数组
        String[] inputTimeArr = dateTimeStr.split(" ")[1].split(":");

        //当前时分秒数组
        String[] currentTimeArr = currentTimeStr.split(" ")[1].split(":");



        int l = calSeconds(currentTimeArr[0],currentTimeArr[1],currentTimeArr[2]) - calSeconds(inputTimeArr[0],inputTimeArr[1],inputTimeArr[2]);

        int hourDiff = l/3600;
        /**
         * 是否超过一个小时
         */
        if (hourDiff > 0) {
            return hourDiff + "小时前";
        }

        int minuteDiff =l/60;
        /**
         * 是否超过一分钟
         */
        if (minuteDiff > 0) {
            return minuteDiff + "分钟前";
        }

        int secondDiff = Integer.parseInt(currentTimeArr[2]) - Integer.parseInt(inputTimeArr[2]);
        /**
         * 是否超过一秒钟
         */
        if (secondDiff > 0) {
            return secondDiff + "秒前";
        }

        return "刚刚";

    }

    /**
     * 根据时间，进行时间的展示
     *
     * @param inputDate
     * @return
     * @Author: MR LIS
     * @Date: 12:46 2018/7/17
     */
    public static String calculateTime(Date inputDate) {
        return calculateTime(DateTimeUtil.format(inputDate, DateTimeUtil.FORMAT_TIME));
    }


    private static Integer calSeconds(String hour,String minute,String seconds) {
        return Integer.parseInt(hour)*60*60+Integer.parseInt(minute)*60+Integer.parseInt(minute);
    }

    public static void main(String[] args) {
        System.out.println(ShowTimeTools.calculateTime("2018-07-14 14:24:59"));
    }


}