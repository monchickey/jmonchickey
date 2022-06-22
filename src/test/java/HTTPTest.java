import com.monchickey.net.HTTPResponse;
import com.monchickey.net.HTTPUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class HTTPTest {

    @Test
    public void testHTTPGet() {

        String data = HTTPUtil.simpleGet("https://www.163.com", "utf-8");
        System.out.println(data);
        Assertions.assertNotEquals(data, null);
        System.out.println(data.length());

        System.out.println("===============================================");

        HTTPResponse response = HTTPUtil.get(
                "https://www.baidu.com/s", "wd=%E7%99%BE%E5%BA%A6%E7%BD%91%E7%9B%98",
                null, "utf-8");
        Assertions.assertNotEquals(response, null);
        System.out.println("headers: ");
        for(Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue().get(0));
        }
        System.out.println("body: ");
        System.out.println(response.getContent());
        System.out.println(response.getContent().length());
    }

    @Test
    public void testHTTPPost() {
        HTTPResponse response = HTTPUtil.post(
                "http://127.0.0.1:8980", "post body.",
                null, "utf-8");
        Assertions.assertNotEquals(response, null);
        System.out.println("headers: ");
        for(Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue().get(0));
        }
        System.out.println("body: ");
        System.out.println(response.getContent());
        System.out.println(response.getContent().length());
    }

    @Test
    public void testImageDownload() {
        int status = HTTPUtil.downloadImage("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png",
                "/tmp", "baidu.png", 10);
        Assertions.assertEquals(status, 0);
        System.out.println("保存成功!");
    }

}
