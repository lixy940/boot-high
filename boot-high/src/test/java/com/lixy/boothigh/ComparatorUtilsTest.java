package com.lixy.boothigh;

import com.alibaba.fastjson.JSONObject;
import com.lixy.boothigh.utils.ComparatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 16:05 2018/5/15
 * @Modified By:
 */
public class ComparatorUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ComparatorUtilsTest.class);

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("心灵酒店", 2);
        map.put("香锅酒店", 5);
        map.put("轨迹酒店", 6);
        map.put("汉庭酒店", 3);
        map.put("如家酒店", 3);
        map.put("时间酒店", 5);
        map.put("爱丽丝酒店", 1);

        logger.info("排序前:{}",JSONObject.toJSONString(map));
        Map<String, Integer> sortByDescValue = ComparatorUtils.sortByDescValue(map);
        logger.info("排序后:{}",JSONObject.toJSONString(sortByDescValue));
    }
}
