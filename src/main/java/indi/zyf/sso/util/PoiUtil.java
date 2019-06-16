package indi.zyf.sso.util;

import com.deepoove.poi.XWPFTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiUtil {


    private static final Logger log = LoggerFactory.getLogger(PoiUtil.class);

    /**
     * 项目资源路径.
     */
    private static final String PATH = "F:/poitl-test/web";

    /**
     * word模板路径.
     */
    private static final String DOC_PATH = PATH + "/template/幼儿报告下载模板.docx";

    /**
     * 图片路径.
     */
    private static final String PIC_PATH = PATH + "/template/test/pic.png";

    /**
     * 输出文件及路径.
     */
    private static final String OUTPUT_PATH = "F:/test/poitl_out_word.docx";



    public static void addImageToDoc(){

    }

    public static void demo() {
        try {
            XWPFTemplate template = XWPFTemplate.compile(DOC_PATH).render("");
            FileOutputStream out = new FileOutputStream(OUTPUT_PATH);
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("通过'poi-tl'导出word成功!");
    }
}
