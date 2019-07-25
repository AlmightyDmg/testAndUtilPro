package cn.com.util.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhum
 * @date 2019年7月25日 17:02:45
 * @description 获得http开头的网络图片流
 */
public class GetNetImageFileUtil {

    /**
     *
     * @description  获取网络图片的流  只能使用http开头的图片
     * @param url http://127.0.0.1:8080/xxx//xxx/2018/xxxxx.png
     * @author zhumeng
     * @date 2019年5月13日13:57:45
     */
    public static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            //System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }
}