package utils;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 17:42 2018/4/9
 * @Modified By:
 */

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelSAXParserUtil {
    /**
     * 定义的验证通过的数据
     */
    private List<String> dataList = new ArrayList();

    /**
     * 处理一个sheet
     *
     * @param filename
     * @throws Exception
     */
    public void processOneSheet(String filename) throws Exception {

        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        StylesTable stylesTable = r.getStylesTable();
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst,stylesTable);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        pkg.close();
    }

    /**
     * 处理所有sheet
     *
     * @param filename
     * @throws Exception
     */
    public void processAllSheets(String filename) throws Exception {

        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        StylesTable stylesTable = r.getStylesTable();
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst,stylesTable);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        pkg.close();
    }

    /**
     * 获取解析器
     *
     * @param sst
     * @return
     * @throws SAXException
     */
    public XMLReader fetchSheetParser(SharedStringsTable sst, StylesTable stylesTable)
            throws SAXException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst,stylesTable,dataList);
        parser.setContentHandler(handler);
        return parser;
    }


    public List<String> getDataList() {
        return dataList;
    }



    public static void main(String[] args) throws Exception {
        ExcelSAXParserUtil example = new ExcelSAXParserUtil();
        // String str = "Book1";
        String filename = "E:\\TDown\\data.xlsx";
        System.out.println("-- 程序开始 --");
        long time_1 = System.currentTimeMillis();
        try {
            example.processOneSheet(filename);
        } catch (Exception e){

        } finally {
        }

        long time_2 = System.currentTimeMillis();
        System.out.println("-- 程序结束1 --");
        System.out.println("-- 耗时1 --" + (time_2 - time_1) / 1000 + "s");

        //处理每条记录
        doOneRowCheck(example.getDataList());


        System.out.println("-- 程序结束2 --");
        System.out.println("-- 耗时2--" + (time_2 - time_1) / 1000 + "s");

    }



    /**
     * @Author: MR LIS
     * @Description: 处理每行的记录，分割
     * @Date: 14:32 2018/4/9
     * @return
     */
    private static void doOneRowCheck(List<String> dataList) {

        for (String s : dataList) {
            System.out.println(s);
            //分割后，进行每列数据的处理
//            String[] split = s.split(",");
        }
    }


}
