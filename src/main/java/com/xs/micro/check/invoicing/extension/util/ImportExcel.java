/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xs.micro.check.invoicing.extension.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 *
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ImportExcel {

    private static Logger log = LoggerFactory.getLogger(ImportExcel.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数
     *
     * @param fileName  导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数
     *
     * @param file      导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum)
            throws InvalidFormatException, IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     *
     * @param fileName   导入文件
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param fileName  导入文件
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetName 工作表名称
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, String sheetName)
            throws InvalidFormatException, IOException {
        this(new File(fileName), headerNum, sheetName);
    }

    /**
     * 构造函数
     *
     * @param file       导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param file      导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetName 工作表名称
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, String sheetName)
            throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetName);
    }

    /**
     * 构造函数
     *
     * @param multipartFile 导入文件对象
     * @param headerNum     标题行号，数据行号=标题行号+1
     * @param sheetIndex    工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param multipartFile 导入文件对象
     * @param headerNum     标题行号，数据行号=标题行号+1
     * @param sheetName     工作表名称
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, String sheetName)
            throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetName);
    }

    /**
     * 构造函数
     *
     * @param fileName   导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        log.debug("Initialize success.");
    }

    /**
     * 构造函数
     *
     * @param fileName  导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetName 工作表名称
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, String sheetName)
            throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        this.sheet = this.wb.getSheet(sheetName);
        this.headerNum = headerNum;
        log.debug("Initialize success.");
    }

    /**
     * 获取行对象
     *
     * @param rownum
     * @return
     */
    public Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     *
     * @return
     */
    public int getDataRowNum() {
        return headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     *
     * @return
     */
    public int getLastDataRowNum() {
        return this.sheet.getPhysicalNumberOfRows();
    }

    /**
     * 获取最后一个列号
     *
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public String getCellValue(Row row, int column) {
        String val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                // 20160530 增加日期判断和格式化数字为非科学计数法 by gch
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    val = getNumericCellValue(cell);
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    try {
                        val = cell.getStringCellValue();
                    } catch (Exception e) {
                        val = getNumericCellValue(cell);
                    }
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    val = String.valueOf(cell.getBooleanCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
                    val = String.valueOf(cell.getErrorCellValue());
                }
            }
        } catch (Exception e) {
            return StringUtils.trimToEmpty(val);
        }
        return StringUtils.trimToEmpty(val);
    }

    private String getNumericCellValue(Cell cell) {
        String val = "";
        if (DateUtil.isCellDateFormatted(cell)) {
            val = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
        } else {
            double doubleVal = cell.getNumericCellValue();
            long longVal = Math.round(doubleVal);
            if (Double.parseDouble(longVal + ".0") == doubleVal) {
                val = new DecimalFormat("#").format(longVal);
            } else {
                val = new DecimalFormat("#").format(doubleVal);
            }
        }
        return val;
    }

    /**
     * 获取导入数据列表
     */
    public List<String[]> getDataList() {
        List<String[]> dataList = Lists.newArrayList();
        for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
            Row row = this.getRow(i);
            String[] data = new String[row.getLastCellNum()];
            int emptyCount = 0;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                String val = this.getCellValue(row, j);
                data[j] = val;
                if (StringUtils.isBlank(val)) {
                    // 记录空数量
                    emptyCount++;
                }
            }
            if (emptyCount == data.length) {
                // 全空的数据不要
                continue;
            }
            dataList.add(data);
        }
        return dataList;
    }

}
