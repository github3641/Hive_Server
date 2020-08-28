package org.example.dc.srv.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: WriteExcelUtil
 * Author: Peng Sun
 * Date: 2020/8/21
 * Version: 1.0
 * Description:
 */
public class WriteExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final Logger logger = LoggerFactory.getLogger(WriteExcelUtil.class);

    public static void writeExcel(List<JSONObject> dataList, int cloumnCount, String finalXlsxPath, List<String> topList) {
        OutputStream fos = null;
        Workbook workBook = null;
        Sheet sheet = null;
        int lineNum = 0;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            System.out.println("columnNumCount为：" + columnNumCount);
            // 判断Excel文件是否存在
            File finalXlsxFile = new File(finalXlsxPath);
            if (finalXlsxFile.exists()) {
                //当目标文件存在时，需要将目标文件数据清空并重新写入
                workBook = getWorkbok(finalXlsxFile);
                // sheet 对应一个工作页
                sheet = workBook.getSheetAt(0);
                //删除原有数据，除了属性列
                int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
                System.out.println("清空数据总行数，除属性列：" + rowNumber);
                for (int i = 1; i <= rowNumber; i++) {
                    Row row = sheet.getRow(i);
                    sheet.removeRow(row);
                }
                // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
                fos = new FileOutputStream(finalXlsxPath);
                workBook.write(fos);
                fos.close();
            } else if (!finalXlsxFile.exists()) {
                //导出文件不存在时，新建Excel文件
                workBook = new XSSFWorkbook();
                sheet = workBook.createSheet();
                fos = new FileOutputStream(finalXlsxPath);
                workBook.write(fos);
                fos.close();
            }
            /**
             * 往Excel中写新数据
             */
            List<String> listKey = new ArrayList();
            fos = new FileOutputStream(finalXlsxPath);

            for (int i = 0; i < dataList.size(); i++) {

                JSONObject dataMap = dataList.get(i);
                if (i == 0) {
                    //插入表头
                    Row row0 = sheet.createRow(i);
                    for (int j = 0; j < columnNumCount; j++) {
                        Cell top = row0.createCell(j);
                        top.setCellValue(topList.get(j));
                    }
                }

                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(i + 1);
                // 得到要插入的每一条记录

                for (int j = 0; j < columnNumCount; j++) {
                    // 在一行内循环
                    Cell first = row.createCell(j);
                    first.setCellValue(dataMap.getString(topList.get(j)));
                }
            }
            lineNum = dataList.size();
            System.out.println("写入数据总行数，除属性列 " + lineNum);
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            workBook.write(fos);
            workBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }


    /**
     * 判断Excel的版本,获取Workbook
     *
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

}
