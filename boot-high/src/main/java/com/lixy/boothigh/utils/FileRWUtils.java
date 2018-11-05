package com.lixy.boothigh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

/**
 * @Author: MR LIS
 * @Description:文件读取写入工具
 * @Date: Create in 10:05 2018/3/30
 * @Modified By:
 */
public class FileRWUtils {

    private final static Logger logger = LoggerFactory.getLogger(FileRWUtils.class);


    /**
     * @Author: MR LIS
     * @Description:
     * @Date: 10:53 2018/3/30
     * @dir 文件目录
     * @fileName 文件名称
     * @return
     */
    public static String readFileRandomAccess(String dir,String fileName){
        StringBuilder sb = new StringBuilder();
        RandomAccessFile raf=null;
        try{
            raf=new RandomAccessFile(dir+fileName, "r");
            //获取RandomAccessFile对象文件指针的位置，初始位置是0
//            System.out.println("RandomAccessFile文件指针的初始位置:"+raf.getFilePointer());
            raf.seek(raf.getFilePointer());//移动文件指针位置
            byte[] buff=new byte[1024];
            //用于保存实际读取的字节数
            int hasRead=0;
            //循环读取
            while((hasRead=raf.read(buff))>0){
                //打印读取的内容,并将字节转为字符串输入
                sb.append(new String(buff, 0, hasRead));

            }

        }catch(IOException e){
            logger.error("file read exception",e.getMessage());
        }finally {
            if(raf!=null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error("RandomAccessFile object close exception",e.getMessage());
                }
            }
        }
        return sb.toString();
    }


    /**
     * @Author: MR LIS
     * @Description:输出内容到指定文件
     * @Date: 10:53 2018/3/30
     * @dir 文件目录
     * @fileName 文件名称
     * @content 内容
     */
    public static void writeToLocalFile(String Dir,String fileName,String content){
        RandomAccessFile raf = null;
        try {
            /**以读写的方式建立一个RandomAccessFile对象**/
             raf = new RandomAccessFile(Dir+fileName, "rw");
            //将记录指针移动到文件最后
            raf.seek(raf.length());
            raf.write(content.getBytes());
            raf.seek(0);
        } catch (IOException e) {
            logger.error("file write exception",e.getMessage());
        }finally {
            if(raf!=null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error("RandomAccessFile object close exception",e.getMessage());
                }
            }
        }

    }

    /**
     * 获取当前月对应的path /2018/10/
     * @return
     */
    public static String getCurrentMonthPath(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = now.getMonthValue() < 10 ? "0" + now.getMonthValue() : String.valueOf(now.getMonthValue());
        return "/"+year+"/"+month+"/";
    }

    /**
     * 获取当前日期对应的path /2018/10/25
     * @return
     */
    public static String getCurrentDayPath(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = now.getMonthValue() < 10 ? "0" + now.getMonthValue() : String.valueOf(now.getMonthValue());
        String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : String.valueOf(now.getDayOfMonth());
        return "/"+year+"/"+month+"/"+day+"/";
    }
}
