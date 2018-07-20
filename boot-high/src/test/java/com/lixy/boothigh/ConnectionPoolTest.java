package com.lixy.boothigh;

import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.utils.GenDBUtils;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: MR LIS
 * @Description:连接数量测试类
 * @Date: Create in 9:43 2018/7/20
 * @Modified By:
 */
public class ConnectionPoolTest {
    private static int threadNum = 30;

    public static void main(String[] args) {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.setDbIp("192.168.19.161");
        dataBaseConfig.setDbPort("3306");
        dataBaseConfig.setDbType("tidb");
        dataBaseConfig.setDbServerName("test");
        dataBaseConfig.setDbUser("root");
        dataBaseConfig.setDbPassword("123456");
        List<Connection> list= new LinkedList<>();
        for (int i = 0; i < threadNum; i++) {
            //单线程调用
//            list.add(GenDBUtils.getConnection(dataBaseConfig));
            //多线程调用
            new Thread( new Runnable() {
                @Override
                public void run() {
                    list.add(GenDBUtils.getConnection(dataBaseConfig));

                }
            }).start();
        }

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {
            Connection con = list.remove(i);
            GenDBUtils.closeConn(con,null,null);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
