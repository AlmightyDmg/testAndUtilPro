package cn.com.util.image;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description 图片转成pdf
 * @date 2019年5月13日11:55:09
 * @author zhum
 */
public class TransformImg2PdfUtil {


    public static void main(String[] args) {
        String imgFilePath = "C:/Users/xxx/Desktop/20190329160407039.jpeg";
        String pdfFilePath = "C:/Users/xxx/Desktop/20190329160407039.pdf";
        boolean b = transformImg2Pdf(imgFilePath, pdfFilePath);
    }


    /**
     *
     * @description: 图片转pdf  目前已知 支持 png，jpg和jpeg格式图片
     * @author zhumeng
     * @date May 13, 2019 11:44:16 AM
     *
     * @param imgFileBytes  输入的图片的字节数组
     * @param pdfFilePath  输出的PDF的绝对路径
     * @return
     */
    public static boolean transformImg2Pdf(byte[] imgFileBytes, String pdfFilePath){
        boolean success = true;
        try {
            // 读取一个图片
            Image image = Image.getInstance(imgFileBytes);
            success = transform(image, pdfFilePath);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     *
     * @Description:图片转pdf  目前已知 支持 png，jpg和jpeg格式图片
     * @author zhumeng
     * @date May 13, 2019 11:44:16 AM
     *
     * @param imgFilePath  输入的图片的绝对路径
     * @param pdfFilePath  输出的PDF的绝对路径
     * @return
     */
    public static boolean transformImg2Pdf(String imgFilePath, String pdfFilePath){
        File file=new File(imgFilePath);
        boolean success = true;
        if(file.exists()){
            try {
                Image image = Image.getInstance(imgFilePath);
                transform(image,pdfFilePath);
            } catch (BadElementException e) {
                success = false;
                e.printStackTrace();
            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            }
        }else{
            success = false;
        }
        return success;
    }

    private static boolean  transform(Image image,String pdfFilePath){
        Document document = new Document();
        FileOutputStream fos = null;
        boolean success = true;
        try {
            fos = new FileOutputStream(pdfFilePath);
            PdfWriter.getInstance(document, fos);
            // 添加PDF文档的某些信息，比如作者，主题等等
            document.addAuthor("");
            document.addSubject("");
            // 设置文档的大小
            document.setPageSize(PageSize.A4);
            // 打开文档
            document.open();
            // 写入一段文字
            //document.add(new Paragraph("JUST TEST ..."));
            // 读取一个图片
            //Image image = Image.getInstance(imgFilePath);
            float imageHeight=image.getScaledHeight();
            float imageWidth=image.getScaledWidth();
            int i=0;
            //设置图片的宽度和高度不超过纸张的大小
            while(imageHeight>500||imageWidth>500){
                image.scalePercent(100-i);
                i++;
                imageHeight=image.getScaledHeight();
                imageWidth=image.getScaledWidth();
                //System.out.println("imageHeight->"+imageHeight);
                //System.out.println("imageWidth->"+imageWidth);
            }

            image.setAlignment(Image.ALIGN_CENTER);
            //		     //设置图片的绝对位置
            // image.setAbsolutePosition(0, 0);
            // image.scaleAbsolute(500, 400);
            // 插入一个图片
            document.add(image);
        } catch (DocumentException de) {
            success = false;
        } catch (IOException ioe) {
            success = false;
        }
        document.close();
        try {
            fos.flush();
            fos.close();
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }
}