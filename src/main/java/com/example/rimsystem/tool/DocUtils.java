package com.example.rimsystem.tool;

import com.deepoove.poi.XWPFTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @auther luyu
 */
public class DocUtils {
//    根据模板创建word文档
    public static void downloadDoc(OutputStream out, String newWordName,String templateDoc, Map dataMap){
//        使用类加载器加载资源路径，找的是class文件的根路径，即target的绝对路径。为什么用这个，因为这种方法即使在生产环境中
//        当然也可以使用io流来读取，但是io流用的是绝对位置，当换一个环境时，有很大的概率无效
//        也是有效的，springboot自带的tomact，会以jar包的形式上传到服务器，因此我们无法直接访问里面的类和资源，只能通过提供类加载器
//        获取字节码文件资源
        String path = ClassUtils.class.getClassLoader().getResource("").getPath();
//        文档解析类,将参数传入
        XWPFTemplate template = XWPFTemplate.compile(path+templateDoc).render(dataMap);
//        将解析完的文件放到输出流中
        try {
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
