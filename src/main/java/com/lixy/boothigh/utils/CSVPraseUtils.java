package com.lixy.boothigh.utils;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MR LIS
 * @Description:csv数据处理工具类
 * @Date: Create in 16:05 2018/5/21
 * @Modified By:
 */
public class CSVPraseUtils {

    private final static Logger logger = LoggerFactory.getLogger(CSVPraseUtils.class);
    /**
     * @Author: MR LIS
     * @Description: 读取csv数据
     * @param: path 路径
     * @param: isHeader 是否读取表头
     * @Date: 16:11 2018/5/21
     * @return
     */
    public static List<String[]> readCSV(String path,boolean isHeader) {
        // 用来保存数据
        List<String[]> csvFileList = new ArrayList<String[]>();
        try {
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            // 跳过表头 如果需要表头的话，这句可以忽略
            if(isHeader)
                reader.readHeaders();
            // 逐行读入数据
            while (reader.readRecord()) {
                csvFileList.add(reader.getValues());
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

    /**
     * @Author: MR LIS
     * @Description: 读取csv数据
     * @param: path 路径
     * @param: isHeader 是否读取表头
     * @param: separator 分隔符
     * @Date: 16:11 2018/5/21
     * @return
     */
    public static List<String[]> readCSV(String path,boolean isHeader,char separator) {
        // 用来保存数据
        List<String[]> csvFileList = new ArrayList<String[]>();
        try {
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(path, separator, Charset.forName("UTF-8"));
            // 跳过表头 如果需要表头的话，这句可以忽略
            if(isHeader)
                reader.readHeaders();
            // 逐行读入数据
            while (reader.readRecord()) {
                csvFileList.add(reader.getValues());
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

    /**
     * @Author: MR LIS
     * @Description: 写数据
     * @Date: 16:19 2018/5/21
     * @param: path 路径
     * @param: hearder 表头
     * @param: dataList 数据
     * @param: separator 分隔符
     * @return
     */
    public static void writeCSV(String csvFilePath,String[] hearder,List<List<String>> dataList,char separator) {
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(csvFilePath, separator, Charset.forName("UTF-8"));
            // 写表头
            csvWriter.writeRecord(hearder);
            //写内容
            for (List<String> row : dataList) {
                csvWriter.writeRecord(row.toArray(new String[0]));
            }

            csvWriter.close();
            logger.info("--------CSV文件已经写入--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Author: MR LIS
     * @Description: 写数据
     * @Date: 16:19 2018/5/21
     * @param: path 路径
     * @param: hearder 表头
     * @param: dataList 数据
     * @return
     */
    public static void writeCSV(String csvFilePath,String[] hearder,List<List<String>> dataList) {
        try {
            // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
            // 写表头
            csvWriter.writeRecord(hearder);
            //写内容
            for (List<String> row : dataList) {
                csvWriter.writeRecord(row.toArray(new String[0]));
            }

            csvWriter.close();
            logger.info("--------CSV文件已经写入--------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        List<List<String>> list = new ArrayList<>();

        String[] header = new String[]{"姓名","学号"};

        List<String> dataList = new ArrayList<>();
        dataList.add("张三");
        dataList.add("999034");
        list.add(dataList);
        dataList = new ArrayList<>();
        dataList.add("雪飞");
        dataList.add("999054");
        list.add(dataList);
        dataList = new ArrayList<>();
        dataList.add("刘文");
        dataList.add("999074");
        list.add(dataList);
        dataList = new ArrayList<>();
        dataList.add("走路");
        dataList.add("999234");
        list.add(dataList);

        writeCSV("F://ceshi1.csv", header,list,':');

    }
}
