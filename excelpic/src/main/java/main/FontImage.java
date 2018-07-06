package main;

import utils.ExcelSAXParserUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontImage {

    private static String path = "D:/考试成绩.xlsx";

    public static void main(String[] args)  {
        while (true) {
            System.out.println("***************************宝宝专有**********************************************");
            System.out.println("1.修改需要处理的excel文件名称为‘考试成绩.xlsx’");
            System.out.println("2.请将excel文件‘考试成绩.xlsx’放到 D盘 根目录");
            System.out.println("3.指定excel表中含有姓名列的序号：如第一列输入1，然后回车");
            System.out.println("********************************************************************************");
            System.out.println();
            System.out.print("请输入excel表中含有姓名的列序号（数字）===>");
            System.out.println();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int column=0;
            try {
                String str = br.readLine();
                column = Integer.parseInt(str);

            } catch (Exception e) {
                System.out.println("=============================================================================");
                System.out.println();
                System.out.println("*************输入不正确，请输入数字******************************************");
                System.out.println();
                System.out.println();
                System.out.println();
                continue;
            }

            try {
                handlerExcel(path, column);
                Thread.sleep(120000);
                break;
            } catch (Exception e) {
                System.out.println("=============================================================================");
                System.out.println();
                System.out.println("*******************************************************************************");
                System.out.println("1.excel文件名称不正确");
                System.out.println("2.excel文件位置放错了");
                System.out.println("*******************************************************************************");
                System.out.println();
                System.out.println();
            }

        }
    }


    public static void handlerExcel(String path,Integer column)throws Exception {
        ExcelSAXParserUtil saxParserUtil = new ExcelSAXParserUtil();
        saxParserUtil.processOneSheet(path);

        String outFileDir = path.substring(0, path.lastIndexOf("/") + 1) + "pic/";

        File dir = new File(outFileDir);
        if(!dir.exists())
            dir.mkdirs();

        List<String> dataList = saxParserUtil.getDataList();

        int singleWidth = 150;
        int width = dataList.get(0).split(" ").length*singleWidth;
        int height = 64*2;
        Font font = new Font("微软雅黑", Font.PLAIN, 25);

        int len = dataList.size();

        Map<String, Integer> nameMap = new HashMap<String, Integer>();

        System.out.println("正在生成，请稍等......");
        System.out.println();
        for (int i = 1; i < len; i++) {
            // 创建图片
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_BGR);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width, height);
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
            g.setColor(Color.black);// 在换成黑色
            g.setFont(font);// 设置画笔字体
            /** 用于获得垂直居中y */
            Rectangle clip = g.getClipBounds();
            FontMetrics fm = g.getFontMetrics(font);
            int ascent = fm.getAscent();
            int descent = fm.getDescent();
            int y = (clip.height - (ascent + descent)) / 4 + ascent;

            String[] headArr = dataList.get(0).split(" ");

            for (int j = 0; j < headArr.length; j++) {
                g.drawString("————————————",  j * singleWidth, y-16);
                g.drawString(headArr[j],40+j*singleWidth,y+5);
                if (j > 0 && j < headArr.length) {
                    g.drawString("|",j*singleWidth,y);
                }
            }

            y = clip.height - descent -44;
            for (int j = 0; j <headArr.length ; j++) {
                g.drawString("————————————",  j * singleWidth, y);
                if (j > 0 && j < headArr.length) {
                    g.drawString("|",j*singleWidth,y);
                }

            }

             y = clip.height - descent -18;
            String[] dataArr = dataList.get(i).split(" ");

            for (int j = 0; j < dataArr.length; j++) {
                g.drawString(dataArr[j],40+j*singleWidth,y-5);
                if (j > 0 && j < dataArr.length) {
                    g.drawString("|",j*singleWidth,y);
                }
                g.drawString("————————————",  j * singleWidth, y+13);
            }

            //如果有重复名字
            String name = dataArr[column - 1];
            if (!nameMap.containsKey(name)) {
                nameMap.put(name, 0);
            }else{
                nameMap.put(name, nameMap.get(name) + 1);
                name = name+nameMap.get(name);
            }
            File outFile = new File(outFileDir + name + ".png");

            g.dispose();
            ImageIO.write(image, "png", outFile);// 输出png图片


        }

        System.out.println("拆分单条数据成功，生成的新文件保存在目录："+outFileDir);
        System.out.println();
        System.out.println("2分钟后窗口将自动关闭");
        System.out.println("=============================================================================");

    }

    // 根据str,font的样式以及输出文件目录
    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        ImageIO.write(image, "png", outFile);// 输出png图片
    }
}

