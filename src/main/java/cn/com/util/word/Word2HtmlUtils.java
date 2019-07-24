package cn.com.util.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * @author zhum
 * @date 2019年7月24日 15:35:15
 * @description 将Word转换为html，适用于doc和docx文档
 */
public class Word2HtmlUtils {

    public static void main(String[] args) {
//        String filepath = "C:\\Users\\xxx\\Desktop\\";
//        String fileName = "xxxx.docx";
//        String htmlName = "test.html";
        //wordDocx2Html(filepath,fileName,htmlName);


        String filepath = "C:\\Users\\xxx\\Desktop\\";
        String imagepath = "C:\\Users\\xxx\\Desktop\\image";
        String fileName = "xxxx.doc";
        String htmlName = "xxxx.html";
        wordDoc2Html(filepath,imagepath,fileName,htmlName);

    }


    /**
     * @author zhum
     * @date 2019年7月4日 10:48:00
     * @description docx格式的word转换为html
     * @param filepath Word文件所在 和 生成的html文件保存 的绝对路径 例：C:\Users\zhum\Desktop\
     * @param fileName Word文件的名称 例：NTM法官工作平台数据库设计v1.4.9.docx
     * @param htmlName HTML的名称 例：test.html
     * @return
     */
    public static boolean wordDocx2Html(String filepath,String fileName,String htmlName){
        boolean flag = true;
        final String file = filepath + fileName;
        File f = new File(file);
        try {
            if (!f.exists()) {
                flag = false;
                System.out.println("Sorry File does not Exists!");
            } else {
                if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                    // 1) 加载word文档生成 XWPFDocument对象
                    InputStream in = new FileInputStream(f);
                    XWPFDocument document = new XWPFDocument(in);

                    // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                    File imageFolderFile = new File(filepath);
                    XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
                    options.setExtractor(new FileImageExtractor(imageFolderFile));
                    options.setIgnoreStylesIfUnused(false);
                    options.setFragment(true);

                    // 3) 将 XWPFDocument转换成XHTML
                    OutputStream out = new FileOutputStream(new File(filepath + htmlName));
                    XHTMLConverter.getInstance().convert(document, out, options);

                    //也可以使用字符数组流获取解析的内容
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                XHTMLConverter.getInstance().convert(document, baos, options);
//                String content = baos.toString();
//                System.out.println(content);
//                 baos.close();
                } else {
                    flag = false;
                    System.out.println("Enter only MS Office 2007+ files");
                }
            }
        } catch (IOException e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * @author zhum
     * @date 2019年7月4日 11:17:17
     * @description doc格式的word转换为html
     * @param filepath Word文件所在 和 生成的html文件保存 的绝对路径 例：C:\Users\zhum\Desktop\
     * @param imagepath 保存Word文档中的图片 例：C:\\Users\\zhum\\Desktop\\image
     * @param fileName Word文件的名称 例：NTM法官工作平台数据库设计v1.4.9.docx
     * @param htmlName HTML的名称 例：test.html
     * @return
     */
    public static boolean wordDoc2Html(String filepath,final String imagepath,String fileName,String htmlName){
        boolean flag = true;

        final String file = filepath + fileName;

        try {
            InputStream input = new FileInputStream(new File(file));
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //设置图片存放的位置
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    File imgPath = new File(imagepath);
                    if(!imgPath.exists()){//图片目录不存在则创建
                        imgPath.mkdirs();
                    }
                    File file = new File(imagepath + suggestedName);
                    try {
                        OutputStream os = new FileOutputStream(file);
                        os.write(content);
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return imagepath + suggestedName;
                }
            });

            //解析word文档
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();

            File htmlFile = new File(filepath + htmlName);
            OutputStream outStream = new FileOutputStream(htmlFile);

            //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        OutputStream outStream = new BufferedOutputStream(baos);

            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");

            serializer.transform(domSource, streamResult);

            //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
            outStream.close();
        } catch (IOException e){
            flag = false;
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            flag = false;
            e.printStackTrace();
        } catch (TransformerException e) {
            flag = false;
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


}
