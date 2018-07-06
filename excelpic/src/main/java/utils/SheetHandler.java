package utils;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 14:08 2018/4/9
 * @Modified By:
 */

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * 自定义解析处理器 See org.xml.sax.helpers.DefaultHandler javadocs
 */
public class SheetHandler extends DefaultHandler {
    private String cs;
    private StylesTable stylesTable;

    private SharedStringsTable sst;
    //    private StringBuilder lastContents = new StringBuilder();
    private String lastContents;
    private boolean nextIsString;

    private List<String> rowlist = new ArrayList<String>();
    private int curRow = 0;
    private int curCol = 0;

    // 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null, ref = null;
    // 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
    private String maxRef = null;

    private CellDataType nextDataType = CellDataType.SSTINDEX;
    private final DataFormatter formatter = new DataFormatter();
    private short formatIndex;
    private String formatString;

    private Map map = new HashMap();;

    /**
     * 定义的验证通过的数据
     */
    private List<String> dataList = new ArrayList();
    /**
     * 定义验证失败的数据，包含重复和空等验证
     */
    private List<String> failList = new ArrayList();
    /**
     * 验证通过的set
     */
    private static Set<String> passRepeatSet = new HashSet<String>();

    /**
     * 存储用于验证重复的key的列
     */
    private Integer[] colIndexList;


    // 用一个enum表示单元格可能的数据类型
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    public SheetHandler(SharedStringsTable sst, StylesTable stylesTable, List dataList) {
        this.sst = sst;
        this.dataList = dataList;
        this.stylesTable= stylesTable;
    }

    /**
     * 解析一个element的开始时触发事件
     */
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {

        map.put("flag", "start");
        // c => cell
        if (name.equals("c")) {
            // 前一个单元格的位置
            if (preRef == null) {
                preRef = attributes.getValue("r");
            } else {
                preRef = ref;
            }
            // 当前单元格的位置
            ref = attributes.getValue("r");

            this.setNextDataType(attributes);

            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if (cellType == null) { //处理空单元格问题
                nextIsString = true;
                cs = "x";
            } else if (cellType != null && cellType.equals("s")) {
                cs = "s";
                nextIsString = true;
            } else {
                nextIsString = false;
                cs = "";
            }

        }
        // Clear contents cache
        lastContents = "";
    }

    /**
     * 根据element属性设置数据类型
     *
     * @param attributes
     */
    public void setNextDataType(Attributes attributes) {

        nextDataType = CellDataType.NUMBER;
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");
        if ("b".equals(cellType)) {
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) {
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }
        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();
            if ("m/d/yy" == formatString) {
                nextDataType = CellDataType.DATE;
                // full format is "yyyy-MM-dd hh:mm:ss.SSS";
                formatString = "yyyy-MM-dd";
            }
            if (formatString == null) {
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }

    /**
     * 解析一个element元素结束时触发事件
     */
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        String flag =(String)map.get("flag");

        if (nextIsString) {
            if ("s".equals(cs)) {
                int idx = Integer.parseInt(lastContents.toString());
                lastContents =new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }
            if ("c".equals(name) && "x".equals(cs)) {
                if("start".equals(flag)){
                    rowlist.add(curCol, "");
                    curCol++;
                }

            }
        }

        map.put("flag", "end");

        // v => contents of a cell
        // Output after we've seen the string contents
        if ("v".equals(name) || "t".equals(name)) {
            String value = this.getDataValue(lastContents.trim(), "");
            // 补全单元格之间的空单元格
            if (!ref.equals(preRef)) {
                int len = countNullCell(ref, preRef);
                for (int i = 0; i < len; i++) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
            }
            rowlist.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                String value = "";
                // 默认第一行为表头，以该行单元格数目为最大数目
                if (curRow == 0) {
                    maxRef = ref;
                }
                // 补全一行尾部可能缺失的单元格
                if (maxRef != null) {
                    int len = countNullCell(maxRef, ref);

                    for (int i = 0; i <= len; i++) {
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                }

                /**
                 * 1.拼接一行的数据，此处属于一行结束，也可在此对一行数据直接进行处理，建议在外面处理，减少侵入
                 * 2.此处亦可以对数据进行过滤，对不符合条件的数据进行过滤及重复校验，导入数据时需要验证一些值
                 **/
                for (int i = 0; i < rowlist.size(); i++) {
                    if (rowlist.get(i).contains(" ")) {
                        value += "\"" + rowlist.get(i) + "\" ";

                    } else {
                        if (i == rowlist.size() - 1) {
                            value += rowlist.get(i);
                        } else {
                            value += rowlist.get(i) + " ";
                        }
                    }
                }

                // 加换行符
//                value += "\n";
                // 如果没有重复将其放入返回结果集中
    /*            if(!validateRepeat(rowlist)){
                    dataList.add(value);
                }
*/
                dataList.add(value);
                //行数加1，并重置一些数据
                curRow++;
                rowlist.clear();
                curCol = 0;
                preRef = null;
                ref = null;

            }
        }
    }

    /**
     * 根据数据类型获取数据
     *
     * @param value
     * @param thisStr
     * @return
     */
    public String getDataValue(String value, String thisStr)

    {
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                thisStr = first == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                thisStr = "\"ERROR:" + value.toString() + '"';
                break;
            case FORMULA:
                thisStr = '"' + value.toString() + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(
                        value.toString());
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX:
                String sstIndex = value.toString();
                thisStr = value.toString();
                break;
            case NUMBER:
                if (formatString != null) {
                    thisStr = formatter.formatRawCellContents(
                            Double.parseDouble(value), formatIndex,
                            formatString).trim();
                } else {
                    thisStr = value;
                }
                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE:
                try {
                    thisStr = formatter.formatRawCellContents(
                            Double.parseDouble(value), formatIndex,
                            formatString);
                } catch (NumberFormatException ex) {
                    thisStr = value.toString();
                }
                thisStr = thisStr.replace(" ", "");
                break;
            default:
                thisStr = "";
                break;
        }
        return thisStr;
    }

    /**
     * 获取element的文本数据
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        lastContents += new String(ch, start, length);
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     *
     * @param ref
     * @param preRef
     * @return
     */
    public int countNullCell(String ref, String preRef) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0] - letter_1[0]) * 26 * 26
                + (letter[1] - letter_1[1]) * 26
                + (letter[2] - letter_1[2]);
        return res - 1;
    }

    /**
     * 字符串的填充
     *
     * @param str
     * @param len
     * @param let
     * @param isPre
     * @return
     */
    String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }

    /**
     * @Author: MR LIS
     * @Description: 根据列序号，指定需要根据那几列的值作为key放入set集合如：[1,2,4],将第1，2,4列的值以下划线的方式作为key放入
     * @Date: 17:09 2018/4/9
     * @return
     */
    private boolean validateRepeat(List<String> rowlist){
        String key = assembleKey(rowlist);

        //如果不包含，就将其放入passRepeatSet,并返回false
        if(!passRepeatSet.contains(key)){
            passRepeatSet.add(key);
            return false;
        }


        String str = "由";
        int size = rowlist.size();
        for (int i = 0; i < size; i++) {
            if(i==size-1){
                str += "【"+rowlist.get(i)+"】确定的唯一性已存在，重复数据" ;
            }else {
                str += "【"+rowlist.get(i)+"】、" ;
            }
        }

        //添加到失败
        addFailInfo(rowlist);

        return true;

    }

    /**
     * @Author: MR LIS
     * @Description: 拼装[1,2,4],将第1，2,4列的值以下划线的方式作为key
     * @Date: 17:32 2018/4/9
     * @return
     */
    private String assembleKey(List<String> rowlist){
        String key = "";
        int length = colIndexList.length;
        for (int i = 0; i <length; i++) {
            if(i==length-1){
                key += rowlist.get(colIndexList[i]);
            }else{
                key += rowlist.get(colIndexList[i]) + "_";
            }
        }

        return key;
    }

    /**
     * 添加重复信息到列表
     * @param rowlist
     */
    private void addFailInfo(List<String> rowlist){
        String key = "由";
        int length = colIndexList.length;
        for (int i = 0; i <length; i++) {
            if(i==length-1){
                key += "【"+rowlist.get(colIndexList[i])+"】确定的唯一性已存在，重复数据";
            }else{
                key += "【"+rowlist.get(colIndexList[i])+"】、";
            }
        }

        failList.add(key);

    }


}