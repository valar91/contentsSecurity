package com.ziroom.eunomia.common.help;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>单元测试读取文件流辅助类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年11月09日 14:27
 * @Version 1.0
 * @Since 1.0
 */
public class IoResourceHelper {

    public static String readResourceContent(String classpath) {
        return readResourceContent(new ClassPathResource(classpath));
    }

    public static String readResourceContent(Resource resource) {
        StringBuilder buf = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String line = null;
            while ((line = br.readLine()) != null) {
                buf.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ignore) {
                }
            }
        }
        return buf.toString();
    }

}
