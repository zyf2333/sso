package indi.zyf.sso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Mytest {

    private static final Logger log = LoggerFactory.getLogger(Mytest.class);

    private static final String PHANTOM_PATH = "F:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe ";

    //这里我的test.js是保存在G盘下面的phantomjs目录
    private static final String TEST_JS = "G:\\gitRepository\\sso\\src\\main\\resources\\static\\echarts_load.js ";

    public static String downloadImage(String url) {
        String cmdStr = PHANTOM_PATH + TEST_JS + url;
        System.out.println(cmdStr);

        Runtime rt = Runtime.getRuntime();
        try {
            Process process = rt.exec(cmdStr);


            // 获得ping的输出
            InputStream child_in = process.getInputStream();
            InputStreamReader isr=new InputStreamReader(child_in,"gbk");
            int i;
            while((i=isr.read()) != -1){
                System.out.print((char)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("执行phantomjs的指令失败！请检查是否安装有PhantomJs的环境或配置path路径！PhantomJs详情参考这里:http://phantomjs.org", e);
        }
        return "url";
    }

    /**
     * main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        downloadImage("http://127.0.0.1/sso/demo.html");
    }
}
