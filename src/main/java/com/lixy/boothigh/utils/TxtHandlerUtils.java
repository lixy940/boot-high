package com.lixy.boothigh.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MR LIS
 * @Description:txt处理工具类
 * @Date: Create in 16:42 2018/5/21
 * @Modified By:
 */
public class TxtHandlerUtils {

    /**
     * @Author: MR LIS
     * @Description: 读取文件，并返回行集合
     * @Date: 16:44 2018/5/21
     * @param path 读取
     * @return
     */
    public static List<String> readFile(String path) {
        List<String> lineList = new ArrayList<>();
        try {
            // 读取到输入流中
            InputStream input = new FileInputStream(path);
            // 创建BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(input,  Charset.forName("UTF-8")));
            String line = null;
            // 按行读取文本，直到末尾
            while ((line = reader.readLine()) != null) {
                // 打印当前行字符串
                lineList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }


    /**
     * @Author: MR LIS
     * @Description: 读取文件，并返回行集合，带缓冲，读取大数据文件时，一次多读取数据回来，减少io开销
     * @Date: 16:44 2018/5/21
     * @param path 读取
     * @return
     */
    public static List<String> readLargeFile(String path) {
        List<String> lineList = new ArrayList<>();
        try {
            // 读取到输入流中
            InputStream input = new FileInputStream(path);
            // 创建BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(input,  Charset.forName("UTF-8")),10 * 1024 * 1024);//10M缓存
            String line = null;
            // 按行读取文本，直到末尾
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    public static void main(String[] args) {
        List<String> strings = readLargeFile("C:\\Users\\admin\\Desktop\\解析文件\\tonghuaqingdan.csv");
    }

}
