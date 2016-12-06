package com.loyalove.baseboot.web.util;

import com.loyalove.baseboot.common.util.CollectionUtils;
import com.loyalove.baseboot.common.util.StringUtils;
import jxl.Cell;
import jxl.Workbook;
import jxl.format.*;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;


public class ExcelExportUtil extends AbstractUtils {

    private static final String HEADER_PARAM_BEGIN_TAG = "${";
    private static final String HEADER_PARAM_END_TAG = "}";

    private static final String LIST_PARAM_BEGIN_TAG = "$DETAIL{";
    private static final String LIST_PARAM_END_TAG = "}";

    private static Map<String, byte[]> fileInputCachMap = new HashMap<String, byte[]>();

    private final int maxSize = 5000;

    /**
     * 简单列表导出
     *
     * @param request
     * @param excelTemplateFile excel模板
     * @param response
     * @param dataList          列表数据
     * @param title             导出文件名
     */
    public static void exportExcelFile(HttpServletRequest request, String excelTemplateFile,
                                       HttpServletResponse response,
                                       List<Map<String, Object>> dataList, String title) {
        exportExcelFile(request, excelTemplateFile, response, null, dataList, title);
    }

    /**
     * 带表头表尾列表导出
     *
     * @param request
     * @param excelTemplateFile 模板
     * @param response
     * @param header            表头表尾数据
     * @param dataList          列表数据
     * @param title             导出文件名
     */
    public static void exportExcelFile(HttpServletRequest request, String excelTemplateFile,
                                       HttpServletResponse response, Map<String, Object> header,
                                       List<Map<String, Object>> dataList, String title) {
        WebApplicationContext wac = (WebApplicationContext) request
                .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ServletContext context = wac.getServletContext();

        InputStream is = getExcelTemplateStream(context, excelTemplateFile);

        try {
            String tempFile = context.getRealPath("/excelTemplate/" + excelTemplateFile);
            Workbook wb = Workbook.getWorkbook(is);
            WritableWorkbook writewb = Workbook.createWorkbook(new File(tempFile), wb);
            WritableSheet sheet = writewb.getSheet(0);
            int rows = sheet.getRows();
            int beginRows = 0;
            List<List<String>> colList = new ArrayList<List<String>>();
            Map<String, String> letterMap = new HashMap<String, String>();
            Map<String, String> lastLetterMap = new HashMap<String, String>();
            for (int i = 0; i < rows; i++) {
                Cell[] cells = sheet.getRow(i);
                for (int k = 0; k < cells.length; k++) {
                    String contents = cells[k].getContents();
                    if (StringUtils.isNotEmpty(contents)) {
                        contents = contents.trim();
                        //表头
                        if (contents.contains(HEADER_PARAM_BEGIN_TAG)
                                && contents.contains(HEADER_PARAM_END_TAG)
                                && contents.indexOf(HEADER_PARAM_BEGIN_TAG) < contents
                                .indexOf(HEADER_PARAM_END_TAG)) {
                            String value = contents.substring(0,
                                    contents.indexOf(HEADER_PARAM_BEGIN_TAG));

                            while (contents.contains(HEADER_PARAM_BEGIN_TAG)
                                    && contents.contains(HEADER_PARAM_END_TAG)) {
                                value += contents.substring(0,
                                        contents.indexOf(HEADER_PARAM_BEGIN_TAG));
                                String fieldName = contents.substring(
                                        contents.indexOf(HEADER_PARAM_BEGIN_TAG)
                                                + HEADER_PARAM_BEGIN_TAG.length(),
                                        contents.indexOf(HEADER_PARAM_END_TAG));
                                if (header != null && header.get(fieldName) != null)
                                    value += String.valueOf(header.get(fieldName));

                                if (contents.length() >= (contents.indexOf(HEADER_PARAM_END_TAG) + HEADER_PARAM_END_TAG
                                        .length()))
                                    contents = contents
                                            .substring(contents.indexOf(HEADER_PARAM_END_TAG)
                                                    + HEADER_PARAM_END_TAG.length());
                                else
                                    contents = "";
                            }

                            if (StringUtils.isNotEmpty(contents))
                                value += contents;

                            sheet.addCell(new Label(k, i, value));
                        }
                        //列表
                        if (contents.contains(LIST_PARAM_BEGIN_TAG)
                                && contents.contains(LIST_PARAM_END_TAG)
                                && contents.indexOf(LIST_PARAM_BEGIN_TAG) < contents
                                .indexOf(LIST_PARAM_END_TAG)) {
                            beginRows = i;
                            List<String> subColList = new ArrayList<String>();
                            String fieldName = null;
                            while (contents.contains(LIST_PARAM_BEGIN_TAG)
                                    && contents.contains(LIST_PARAM_END_TAG)
                                    && contents.indexOf(LIST_PARAM_BEGIN_TAG) < contents
                                    .indexOf(LIST_PARAM_END_TAG)) {
                                fieldName = contents.substring(
                                        contents.indexOf(LIST_PARAM_BEGIN_TAG)
                                                + LIST_PARAM_BEGIN_TAG.length(),
                                        contents.indexOf(LIST_PARAM_END_TAG));
                                subColList.add(fieldName);
                                if (contents.indexOf(LIST_PARAM_BEGIN_TAG) > 0)
                                    letterMap.put(
                                            fieldName + colList.size(),
                                            contents.substring(0,
                                                    contents.indexOf(LIST_PARAM_BEGIN_TAG)));

                                if (contents.length() >= (contents.indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG
                                        .length()))
                                    contents = contents.substring(contents
                                            .indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG.length());
                                else
                                    contents = "";

                            }
                            if (StringUtils.isNotEmpty(fieldName) && StringUtils.isNotEmpty(contents))
                                lastLetterMap.put(fieldName + colList.size(), contents);

                            colList.add(subColList);

                            //contents = contents.substring(contents.indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG.length());
                        }
                    }
                }
            }

            //填入列表数据
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (i > 0)
                        sheet.insertRow(beginRows + i);
                    Map<String, Object> data = dataList.get(i);
                    for (int k = 0; k < colList.size(); k++) {
                        List<String> subColList = colList.get(k);
                        String value = "";
                        for (String aSubColList : subColList) {
                            if (letterMap.get(aSubColList + k) != null)
                                value += letterMap.get(aSubColList + k);

                            if (data.get(aSubColList) != null)
                                value += String.valueOf(data.get(aSubColList));

                            if (lastLetterMap.get(aSubColList + k) != null)
                                value += lastLetterMap.get(aSubColList + k);
                        }

                        sheet.addCell(new Label(k, beginRows + i, value));
                    }
                }
            } else {
                for (int k = 0; k < colList.size(); k++) {
                    sheet.addCell(new Label(k, beginRows, ""));
                }
            }

            writewb.write();
            writewb.close();
            is.close();

            downExcel(context, response, title, tempFile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("down file error", e);
            }
        }

    }

    private static void downExcel(ServletContext context, HttpServletResponse response,
                                  String title, String tempFile) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error("", e1);
        }
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + title + ".xls");
        OutputStream os = null;
        BufferedInputStream bis = null;

        try {
            InputStream is = new FileInputStream(tempFile);
            bis = new BufferedInputStream(is);
            response.setContentLength(bis.available());
            byte[] buf = new byte[1024];
            int bytes = 0;
            os = response.getOutputStream();
            while ((bytes = bis.read(buf)) != -1) {
                os.write(buf, 0, bytes);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("down file error", e);
            }
        }

        //		try
        //		{
        //			new File(tempFile).delete();
        //		}catch (Exception e)
        //		{
        //			log.error(e.getMessage(), e);
        //		}
    }

    public static InputStream getExcelTemplateStream(ServletContext context,
                                                     String excelTemplateName) {
        ByteArrayInputStream bis = null;
        FileInputStream fis = null;
        byte[] byteArray = null;
        try {
            if (fileInputCachMap.get(excelTemplateName) != null) {
                byteArray = (byte[]) fileInputCachMap.get(excelTemplateName);
            } else {
                fis = new FileInputStream(
                        context.getRealPath("/excelTemplate/" + excelTemplateName));
                byteArray = new byte[fis.available()];
                fis.read(byteArray);
                fis.close();
            }
            fileInputCachMap.put(excelTemplateName, byteArray);
            bis = new ByteArrayInputStream(byteArray);
        } catch (IOException e) {
            logger.error(" file [" + "/print/excelTemplate/" + excelTemplateName + "]....", e);
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }
        return bis;
    }

    /**
     * @param request
     * @param response
     * @param dataList
     * @param headerMap
     * @param title
     * @throws Exception
     */
    public static void exportExcelFile(HttpServletRequest request, HttpServletResponse response,
                                       List<LinkedHashMap<String, Object>> dataList,
                                       LinkedHashMap<String, Object> headerMap, String title)
            throws Exception {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error("", e1);
        }
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + title + ".xls");
        OutputStream os = response.getOutputStream();

        export(os, title, dataList, headerMap);

        try {
            os = response.getOutputStream();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("down file error", e);
            }
        }
    }

    public static void exportsExcel(HttpServletRequest request, HttpServletResponse response,
                                    List<LinkedHashMap<String, Object>> dataList,
                                    LinkedHashMap<String, Object> headerMap, String title)
            throws Exception {

        WebApplicationContext wac = (WebApplicationContext) request
                .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ServletContext context = wac.getServletContext();
        String path = context.getContextPath();
        File tempFile = new File(path + "tempration.xls");

        if (!tempFile.exists()) {
            if (tempFile.createNewFile()) {
                logger.error("导出文件发生异常，原因：创建文件失败");
            }
        }
        write(title, dataList, headerMap, tempFile);
        downExcel(context, response, title, tempFile);
    }

    private static void downExcel(ServletContext context, HttpServletResponse response,
                                  String title, File tempFile) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error("", e1);
        }
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + title + ".xls");
        OutputStream os = null;
        BufferedInputStream bis = null;

        try {
            InputStream is = new FileInputStream(tempFile);
            bis = new BufferedInputStream(is);
            response.setContentLength(bis.available());
            byte[] buf = new byte[1024];
            int bytes = 0;
            os = response.getOutputStream();
            while ((bytes = bis.read(buf)) != -1) {
                os.write(buf, 0, bytes);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("down file error", e);
            }
        }

        try {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * @param title
     * @param dataList
     * @param headerMap
     * @param file
     * @throws Exception
     */
    public static void write(String title, List<LinkedHashMap<String, Object>> dataList,
                             LinkedHashMap<String, Object> headerMap, File file)
            throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        }
        jxl.write.WritableWorkbook wbook = Workbook.createWorkbook(file); // 建立excel文件
        jxl.write.WritableSheet wsheet = wbook.createSheet(title, 0); // sheet名称
        jxl.write.Label wlabel = null; // Excel表格的Cell

        // 设置列名字体
        int charTitle = 12;// 列名字体大小
        jxl.write.WritableFont columnFont = new jxl.write.WritableFont(
                WritableFont.createFont("宋体"), charTitle, WritableFont.BOLD);

        jxl.write.WritableCellFormat columnFormat = new jxl.write.WritableCellFormat(columnFont);
        columnFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        columnFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        columnFormat.setAlignment(Alignment.CENTRE); // 水平对齐
        columnFormat.setWrap(true); // 是否换行
        //columnFormat.setBackground(Colour.GRAY_25);// 背景色暗灰-25%

        int c = -1;
        for (Entry<String, Object> header : headerMap.entrySet()) {
            Object cell = header.getValue();
            ++c;
            wlabel = new jxl.write.Label(c, 0, cell.toString(), columnFormat);
            wsheet.addCell(wlabel);
        }

        // 设置正文字体
        int charNormal = 10;// 标题字体大小
        WritableFont normalFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);
        jxl.write.WritableCellFormat normalFormat = new jxl.write.WritableCellFormat(normalFont);
        normalFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        normalFormat.setAlignment(Alignment.CENTRE);// 水平对齐
        normalFormat.setWrap(true); // 是否换行

        // 根据原数据和map来创建Excel的列名
        int row = 0;
        int col = -1;
        for (LinkedHashMap<String, Object> dataMap : dataList) {
            ++row;
            for (Entry<String, Object> data : dataMap.entrySet()) {
                ++col;
                Object cell = data.getValue();
                wlabel = new jxl.write.Label(col, row, cell == null ? "" : String.valueOf(cell),
                        normalFormat);
                wsheet.addCell(wlabel);
            }
            col = -1;
        }

        wbook.write(); // 写入文件
        wbook.close();
    }

    /* 从数据库读数据，写入Excel
     *
     * @param os
     *            数据流，如果是写本地文件的话，可以是FileOutputStream；
     *            如果是写Web下载的话，可以是ServletOupputStream
     * @param title
     *            Excel工作簿的标题,如果不用的话,可以写null或者""
     * @param rs
     *            数据结果集
     * @param map
     *            数据结果集对应Excel表列名映射:key对应数据结果集的列名，必须是大写； value,目前只能对应Column对象
     * @throws Exception
     *             方法内的父类异常有SQLException和IOException
     */
    public static void export(OutputStream os, String title,
                              List<LinkedHashMap<String, Object>> dataList,
                              LinkedHashMap<String, Object> headerMap) throws Exception {
        jxl.write.WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
        jxl.write.WritableSheet wsheet = wbook.createSheet("第一页", 0); // sheet名称
        jxl.write.Label wlabel = null; // Excel表格的Cell

        // 设置列名字体
        int charTitle = 12;// 列名字体大小
        jxl.write.WritableFont columnFont = new jxl.write.WritableFont(
                WritableFont.createFont("宋体"), charTitle, WritableFont.BOLD);

        jxl.write.WritableCellFormat columnFormat = new jxl.write.WritableCellFormat(columnFont);
        columnFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        columnFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        columnFormat.setAlignment(Alignment.CENTRE); // 水平对齐
        columnFormat.setWrap(true); // 是否换行
        columnFormat.setBackground(Colour.GRAY_25);// 背景色暗灰-25%

        Iterator<String> headerItor = headerMap.keySet().iterator();
        int c = -1;
        while (headerItor.hasNext()) {
            String key = (String) headerItor.next();
            Object cell = headerMap.get(key);
            ++c;
            wlabel = new jxl.write.Label(c, 0, cell.toString(), columnFormat);
            wsheet.addCell(wlabel);
        }
        // 根据原数据和map来创建Excel的列名
        int row = 0;
        int col = -1;
        for (LinkedHashMap<String, Object> dataMap : dataList) {
            ++row;
            for (String s : dataMap.keySet()) {
                ++col;
                Object cell = dataMap.get(s);
                wlabel = new Label(col, row, cell.toString(), columnFormat);
                wsheet.addCell(wlabel);
            }
            col = -1;
        }

        // 设置正文字体
        int charNormal = 10;// 标题字体大小
        WritableFont normalFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);
        jxl.write.WritableCellFormat normalFormat = new jxl.write.WritableCellFormat(normalFont);
        normalFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
        normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直对齐
        normalFormat.setAlignment(Alignment.CENTRE);// 水平对齐
        normalFormat.setWrap(true); // 是否换行
        wbook.write(); // 写入文件
        wbook.close();
        os.flush();
        os.close();
    }

}
