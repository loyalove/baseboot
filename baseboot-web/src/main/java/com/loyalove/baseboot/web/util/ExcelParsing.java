package com.loyalove.baseboot.app.util;

import com.loyalove.baseboot.common.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParsing {


    public static List<String[]> getXlsList(String filePath) throws IOException {
        List<String[]> list = new ArrayList<String[]>();
        if (WDWUtil.isExcel2003(filePath)) {
            list = extractTextFromXLS2003(filePath);
        }
        if (WDWUtil.isExcel2007(filePath)) {
            list = extractTextFromXLS2007(filePath);
        }
        return list;
    }

    @SuppressWarnings("deprecation")
    public static List<String[]> extractTextFromXLS2003(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        List<String[]> xlsList = new ArrayList<String[]>();
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
            if (null != workbook.getSheetAt(numSheets)) {
                HSSFSheet aSheet = workbook.getSheetAt(numSheets);
                for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    if (null != aSheet.getRow(rowNumOfSheet)) {
                        HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
                        final String[] xls = new String[aRow.getLastCellNum()];
                        for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                            StringBuffer content = new StringBuffer();
                            if (null != aRow.getCell(cellNumOfRow)) {
                                HSSFCell aCell = aRow.getCell(cellNumOfRow);
                                if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    content.append(aCell.getNumericCellValue());
                                } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                                    content.append(aCell.getBooleanCellValue());
                                } else {
                                    content.append(aCell.getStringCellValue());
                                }
                                xls[cellNumOfRow] = content.toString();
                            }
                        }
                        if (inNotNull(xls)) {
                            xlsList.add(xls);
                        }
                    }
                }
            }
        }
        return xlsList;
    }


    public static List<String[]> extractTextFromXLS2007(String filePath) throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook(filePath);
        List<String[]> excelList = new ArrayList<String[]>();
        for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
            XSSFSheet xSheet = xwb.getSheetAt(numSheet);
            if (xSheet == null) {
                continue;
            }
            for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                XSSFRow xRow = xSheet.getRow(rowNum);
                if (xRow == null) {
                    continue;
                }
                final String[] excel = new String[xRow.getLastCellNum()];
                for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
                    StringBuffer content = new StringBuffer();
                    XSSFCell xCell = xRow.getCell(cellNum);
                    if (xCell == null) {
                        continue;
                    }
                    if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                        content.append(xCell.getBooleanCellValue());
                    } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        content.append(xCell.getNumericCellValue());
                    } else {
                        content.append(xCell.getStringCellValue());
                    }
                    excel[cellNum] = content.toString();
                }
                if (inNotNull(excel)) {
                    excelList.add(excel);
                }
            }
        }
        return excelList;
    }


    public static class WDWUtil {
        public static boolean isExcel2003(String filePath) {
            return filePath.matches("^.+\\.(?i)(xls)$");
        }

        public static boolean isExcel2007(String filePath) {
            return filePath.matches("^.+\\.(?i)(xlsx)$");
        }

    }

    public static boolean inNotNull(String[] xls) {
        if (xls == null) {
            return false;
        }
        boolean inNotNull = false;
        for (int i = 0; i < xls.length; i++) {
            if (StringUtils.isNotBlank(xls[i])) {
                inNotNull = true;
            }
        }
        return inNotNull;
    }

}
