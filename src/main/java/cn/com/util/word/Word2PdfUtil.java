package cn.com.util.word;

import java.io.File;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
/**
 * @author zhum
 * @date 2019年7月24日 16:05:29
 * @description Word转换为pdf
 * 				（会丢失word原有的格式，比如word之前一共10行，pdf可能为11行 对格式有要求时慎用）
 */
public class Word2PdfUtil {

	public static void main(String[] args) {
		doc2pdf("C:/Users/zhum/Desktop/1.doc", "C:/Users/zhum/Desktop/21.pdf");
	}
	/**
	 * @date 2019年7月24日 16:52:56
	 * @description 使用Aspose的插件对Word（doc，docx）转换为pdf
	 * 				  由于Aspose为付费插件，所以转出来的pdf会有水印
	 * 				  解决方法为：
	 * 				  1，在网上查找破解的文件
	 * 				  2.可以在转出来的pdf上插入空白图片，将水印掩盖
	 * @param inPath  word 全路径
	 * @param outPath 生成的pdf 全路径
	 * @author zhum
	 */
	public static void doc2pdf(String inPath, String outPath) {

		try {
			String path=outPath.substring(0,outPath.lastIndexOf("/"));
			File file = null;
			file = new File(path);
			if(!file.exists()){//创建文件夹
				file.mkdirs();
			}
			file = new File(outPath);// 新建一个空白pdf文档
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(inPath); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
			// EPUB, XPS, SWF 相互转换
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}