package cn.com.util.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * @description 生成二维码
 * @author zhum
 * @date 2019年8月15日 15:04:21
 */
public class GenerateEWM {
        public static void main(String[] args){
            generateEWM("测试","E:/code1/","qrcode.png",'M','B',7,67 + 12*(7-1),67 + 12*(7-1));
        }


    /**
     * @description 生成二维码
     * @author zhum
     * @date 2019年8月15日 15:18:41
     * @param content 二维码内要包含的内容
     * @param targetPath 生成的二维码保存的路径 E:/code1/
     * @param fileName 二维码的名称 qrcode.png
     * @param jcdj 纠错等级（分为L、M、H三个等级）
     * @param zf N代表数字，A代表a-Z，B代表其它字符
     * @param version 版本,1到40个版本
     * @param width 二维码的宽度
     * @param height 二维码的高度
     * @return
     */
    public static boolean generateEWM(String content,String targetPath,String fileName,char jcdj,char zf,int version,int width,int height){
        boolean flag = false;

        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect(jcdj);//纠错等级（分为L、M、H三个等级）
        qrcode.setQrcodeEncodeMode(zf);//N代表数字，A代表a-Z，B代表其它字符
        qrcode.setQrcodeVersion(version);//版本,1到40个版本

        //设置一下二维码的像素
        //int width = 67 + 12*(7-1);
        //int height = 67 + 12*(7-1);
        //int width = 300;
        //int height = 300;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //绘图
        Graphics2D gs = bufferedImage.createGraphics();
        gs.setBackground(Color.WHITE);
        gs.setColor(Color.BLACK);
        gs.clearRect(0, 0, width, height);//清除下画板内容
        //设置下偏移量,如果不加偏移量，有时会导致出错。
        int pixoff = 2;
        byte[] d = null;
        try {
            d = content.getBytes("gb2312");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        if(d.length > 0 && d.length <120){//字数的限制
            boolean[][] s = qrcode.calQrcode(d);
            for(int i=0;i<s.length;i++){
                for(int j=0;j<s.length;j++){
                    if(s[j][i]){
                        gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
                    }
                }
            }
        }
        gs.dispose();
        bufferedImage.flush();
        try {
            File file1 = new File(targetPath);
            if(!file1.exists()){
                file1.mkdirs();
            }
            ImageIO.write(bufferedImage, "png", new File(targetPath + fileName));
            //生成成功 返回true
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
