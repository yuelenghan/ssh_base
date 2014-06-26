package com.ghtn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by lh
 * 文件操作相关
 */
public class FileUtil {

    private static Log log = LogFactory.getLog(FileUtil.class);

    /**
     * 得到文件的扩展名
     *
     * @param fileName 文件全名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        if (!StringUtil.isNullStr(fileName)) {
            fileName = fileName.trim();
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 得到文件内容
     *
     * @param path
     * @return
     */
    public static String getFileContent(String path) {
        try {
            if (path == null || path.trim().equals("")) {
                System.out.println("路径为空！");
                return null;
            }
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("文件不存在！");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path)));
            String line = new String();
            StringBuffer temp = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                temp.append(line);
            }
            reader.close();
            return temp.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件没有找到！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取错误！");
        }

        return "";
    }

    /**
     * 读取03版word文件的内容
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_03_Reader(String fileName) throws Exception {
        StringBuffer temp = new StringBuffer();

        FileInputStream fis = new FileInputStream(fileName);
        HWPFDocument doc = new HWPFDocument(fis);
        Range range = doc.getRange();
        int paragraphCount = range.numParagraphs();// 段落
        for (int i = 0; i < paragraphCount; i++) {
            // 遍历段落读取数据
            Paragraph pp = range.getParagraph(i);
            temp.append(pp.text());
        }

        fis.close();
        return temp.toString();
    }

    /**
     * 读取07版word文件的内容
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_07_Reader(String fileName) throws Exception {
        OPCPackage opcPackage = POIXMLDocument.openPackage(fileName);
        POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
        opcPackage.close();
        return ex.getText();
    }

    /**
     * 读取excel文件的内容
     *
     * @param fileName 文件全名
     * @return
     * @throws Exception
     */
    public static String ExcelReader(String fileName) throws Exception {
        String extension = getFileExtension(fileName);
        if (!StringUtil.isNullStr(extension)) {
            if (extension.trim().equals("xlsx")) {
                return ExcelReader(fileName, "2007");
            } else if (extension.trim().equals("xls")) {
                return ExcelReader(fileName, "2003");
            } else {
                throw new Exception("文件类型不对, 必须为excel类型！");
            }
        }
        return "";
    }

    /**
     * 读取excel文件的内容
     *
     * @param fileName 文件全名
     * @param fileType 文件类型，2003或2007
     * @return
     * @throws Exception
     */
    public static String ExcelReader(String fileName, String fileType) throws Exception {
        StringBuffer temp = new StringBuffer();
        FileInputStream fis = new FileInputStream(fileName);
        Workbook wb;
        if (fileType.trim().equals("2003")) {
            wb = new HSSFWorkbook(fis);
        } else if (fileType.trim().equals("2007")) {
            wb = new XSSFWorkbook(fis);
        } else {
            throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
        }

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
                Row r = rowIt.next();
                for (Iterator<Cell> cellIt = r.iterator(); cellIt.hasNext(); ) {
                    Cell cell = cellIt.next();
                    String cellContent = "";

                    //如果是数字类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        cellContent = String
                                .valueOf(cell.getNumericCellValue());
                    }
                    //如果是字符串类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        cellContent = cell.getStringCellValue();
                    }

                    temp.append(cellContent);
                }
            }
        }
        fis.close();
        return getPureText(temp.toString());
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName 文件全名
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> ExcelReaderForList(String fileName) throws Exception {
        return ExcelReaderForList(fileName, 1);
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param startLine 起始行,从1开始
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> ExcelReaderForList(String fileName, int startLine) throws Exception {
        String extension = getFileExtension(fileName);
        if (!StringUtil.isNullStr(extension)) {
            if (extension.trim().equals("xlsx")) {
                return ExcelReaderForList(fileName, "2007", 1);
            } else if (extension.trim().equals("xls")) {
                return ExcelReaderForList(fileName, "2003", 1);
            } else {
                throw new Exception("文件类型不对, 必须为excel类型！");
            }
        }

        return null;
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param fileType  文件类型，2003或2007
     * @param startLine 起始行,从1开始
     * @return 一行为list中的一个元素
     * @throws Exception
     */
    public static List<String[]> ExcelReaderForList(String fileName, String fileType, int startLine) throws Exception {
        if (exists(fileName) && !StringUtil.isNullStr(fileType)) {
            List<String[]> list = new ArrayList<>();

            FileInputStream fis = new FileInputStream(fileName);
            Workbook wb;
            if (fileType.trim().equals("2003")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileType.trim().equals("2007")) {
                wb = new XSSFWorkbook(fis);
            } else {
                throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (wb.getNumberOfSheets() > 1) {
                // 不止一个sheet
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    loopExcelSheet(list, sheet, startLine, sdf);
                }
            } else {
                Sheet sheet = wb.getSheetAt(0);
                loopExcelSheet(list, sheet, startLine, sdf);
            }

            return list;
        } else {
            throw new FileNotFoundException("文件不存在！");
        }

    }

    /**
     * 循环sheet
     *
     * @param list
     * @param sheet
     * @param startLine
     * @param sdf
     */
    private static void loopExcelSheet(List<String[]> list, Sheet sheet, int startLine, SimpleDateFormat sdf) {
        int line = 1; //当前行

        for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
            Row r = rowIt.next();
            if (line < startLine) {
                line++;
                continue;
            }
            int n = 0;
            String[] strArray = new String[r.getLastCellNum()];

            for (Iterator<Cell> cellIt = r.iterator(); cellIt.hasNext(); ) {
                Cell cell = cellIt.next();
                String cellContent = "";

                if (cell != null) {
                    switch (cell.getCellType()) {
                        // 数字
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            //如果是日期
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                cellContent = sdf.format(cell.getDateCellValue());
                            } else {
                                cellContent = String
                                        .valueOf(cell.getNumericCellValue());
                            }
                            break;
                        // 字符串
                        case HSSFCell.CELL_TYPE_STRING:
                            cellContent = cell.getStringCellValue();
                            break;
                        // boolean
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellContent = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellContent = cell.getCellFormula();
                            break;
                        // 空值
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        // 错误
                        case HSSFCell.CELL_TYPE_ERROR:
                            break;
                        default:
                            break;
                    }
                }


                strArray[n] = cellContent;

                n++;
            }

            list.add(strArray);

            line++;
        }

    }

    /**
     * 向文件中写入内容
     *
     * @param text
     * @param path
     */
    public static void writeToFile(String text, String path) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件写入错误！");
        }
    }

    public static void writeToFile(String text, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件写入错误！");
        }
    }

    /**
     * 得到路径path下的所有文件
     *
     * @param path
     * @return
     */
    public static List<File> getAllFiles(File path, List<File> fileList) throws Exception {
        if (path.isFile()) {
            fileList.add(path);
            return fileList;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            getAllFiles(new File(files[i].getCanonicalPath()), fileList);
        }
        return fileList;
    }

    /**
     * 得到path路径下的所有文件夹
     *
     * @param path
     * @param fileList
     * @return
     * @throws Exception
     */
    public static List<File> getAllDirectories(File path, List<File> fileList) throws Exception {
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileList.add(files[i]);
                getAllDirectories(new File(files[i].getCanonicalPath()), fileList);
            }
        }
        return fileList;
    }

    /**
     * 得到纯文本
     *
     * @param text
     * @return
     */
    public static String getPureText(String text) {
        return text.replaceAll("<([^>]*)>", "").replaceAll("\\s*|\t|\r|\n", "").replaceAll("&nbsp;", "");
    }

    /**
     * 删除文件
     *
     * @param file 文件全路径
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param list
     */
    public static void deleteFile(List<File> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                File f = list.get(i);
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param path
     * @throws Exception
     */
    public static void deleteDirectory(File path) {
        if (path.isFile()) {
            path.delete();
        } else {
            File[] files = path.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    deleteDirectory(files[i]);
                }
            }
            path.delete();
        }
    }

    /**
     * 清空文件夹
     *
     * @param path
     */
    public static void clearDirectory(File path) {
        if (path.isFile()) {
            return;
        }
        File[] files = path.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                deleteDirectory(files[i]);
            }
        }

    }

    /**
     * 根据文件名判断文件是否存在
     *
     * @param fileName 文件全名
     * @return
     */
    public static boolean exists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param destFileName 目标文件名
     * @param overlay      如果目标文件存在，是否覆盖, 覆盖:true, 不覆盖:false
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName,
                                   boolean overlay) {
        // 判断原文件是否存在
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            log.error("复制文件失败：原文件" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            log.error("复制文件失败：" + srcFileName + "不是一个文件！");
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在，而且复制时允许覆盖。
            if (overlay) {
                // 删除已存在的目标文件，无论目标文件是目录还是单个文件
                log.warn("目标文件已存在，准备删除它！");
                try {
                    deleteFile(destFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("复制文件失败：删除目标文件" + destFileName + "失败！");
                    return false;
                }
            } else {
                log.error("复制文件失败：目标文件" + destFileName + "已存在！");
                return false;
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                log.warn("目标文件所在的目录不存在，准备创建它！");
                log.debug(destFile.getParentFile());
                if (!destFile.getParentFile().mkdirs()) {
                    log.error("复制文件失败：创建目标文件所在的目录失败！");
                    return false;
                }
            }
        }
        // 准备复制文件
        int byteread = 0;// 读取的位数
        try (InputStream in = new FileInputStream(srcFile);
             OutputStream out = new FileOutputStream(destFile)) {
            // 打开连接到目标文件的输出流
            byte[] buffer = new byte[1024];
            // 一次读取1024个字节，当byteread为-1时表示文件已经读完
            while ((byteread = in.read(buffer)) != -1) {
                // 将读取的字节写入输出流
                out.write(buffer, 0, byteread);
            }
            log.debug("复制单个文件" + srcFileName + "至" + destFileName
                    + "成功！");
            return true;
        } catch (Exception e) {
            log.error("复制文件失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HttpServletResponse对象
     * @return 下载结果
     * @throws java.io.UnsupportedEncodingException 不支持的字符集编码异常
     */
    public static String downloadFile(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // log.debug(ConstantUtil.CONTACTS_TEMPLATE_PATH);
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));

        // TODO : 发布到正式服务器需要修改文件路径
        File file = new File(ConstantUtil.UPLOAD_TEMP_PATH + "/" + fileName);

        try (InputStream is = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return ConstantUtil.SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "文件没有找到！";
        } catch (IOException e) {
            e.printStackTrace();
            return "输入输出错误！";
        }
    }
}

