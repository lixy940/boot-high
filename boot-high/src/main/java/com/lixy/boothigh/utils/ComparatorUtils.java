package com.lixy.boothigh.utils;

import java.util.*;

/**
 * @Author: MR LIS
 * @Description:排序比较工具
 * @Date: Create in 15:38 2018/5/15
 * @Modified By:
 */
public class ComparatorUtils {
    /**
     * @Author: MR LIS
     * @Description: 根据map对象value正序排
     * @Date: 15:56 2018/5/15
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByASCValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return (e1.getValue()).compareTo(e2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    /**
     * @Author: MR LIS
     * @Description: 根据map对象value倒序排
     * @Date: 15:56 2018/5/15
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByDescValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
