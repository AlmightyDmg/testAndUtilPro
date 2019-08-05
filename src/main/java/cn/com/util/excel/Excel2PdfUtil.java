package cn.com.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.License;

/**
 * @author zhum
 * @date 2019年8月5日 17:08:12
 * @description excel转PDF
 */
public class Excel2PdfUtil {


    public static void main(String[] args) {
        String excelPath = "C:/Users/xxx/Desktop/test.xlsx";
        String outPath = "C:/Users/xxx/Desktop/1112.pdf";
        //excel转pdf
        excel2pdf(excelPath, outPath);
    }


    /**
     * @author zhum
     * @description excel转pdf 支持xls和xlsx两种格式 （注意：由于没有授权，会有水印）
     * @param excelPath excel的全路径
     * @param pdfPath 输出的pdf的全路径
     */
    public static void excel2pdf(String excelPath, String pdfPath) {
        /**
         * 由于搞不到excel转pdf 的license 去除不掉水印 所以决定 合成之后再合成一张空白图片  去除水印
         */
        //if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
        //    return;
        //}
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(excelPath);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @author zhum
     * @description word转pdf  该方法也会丢失Word原有格式
     * @param wordPath 需要被转换的word全路径带文件名
     * @param pdfPath 转换之后pdf的全路径带文件名
     */
    public static void doc2pdf(String wordPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath); //新建一个pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath); //Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            os.close();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @author zhum
     * @description 获得授权 如果有的话
     * @return
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Excel2PdfUtil.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
