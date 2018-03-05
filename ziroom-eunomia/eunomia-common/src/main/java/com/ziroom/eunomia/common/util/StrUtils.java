package com.ziroom.eunomia.common.util;

import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年11月28日 14:44
 * @Version 1.0
 * @Since 1.0
 */
public final class StrUtils extends StringUtils {

    private final static char[] NUM_SIMPLE_CN = {'一','二','三','四','五','六','七','八','九','十'};

    /**
     * 判断是否全是简体的数字
     * <em>
     *     有空格将都返回 false
     * </em>
     * <pre>
     * StringUtils.isSimpleNumCn(null)                    = false
     * StringUtils.isSimpleNumCn("")                      = false
     * StringUtils.isSimpleNumCn("一二三")                 = true
     * StringUtils.isSimpleNumCn("一")                    = true
     * StringUtils.isSimpleNumCn("  一")                  = false
     * StringUtils.isSimpleNumCn("一 三")                  = false
     * </pre>
     * @param str
     * @return
     */
    public static boolean isSimpleNumCn(final String str) {
        if (isEmpty(str)) {
            return false;
        }
        final int length = str.length();
        final AtomicInteger show = new AtomicInteger(0);
        for (char cr : str.toCharArray()) {
            for (char nsc : NUM_SIMPLE_CN) {
                if (cr == nsc) {
                    show.incrementAndGet();
                    if (length == show.get()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是会否全是英文数字
     * <em>有空格需要自行扩展</em>
     * @param str
     * @return
     */
    public static boolean isAlphaEn(final String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.matches("[a-zA-Z]+")) {
            return true;
        }
        return false;
    }

    /***
     * 判断是否是数字，浮点型
     * <em>有空格需要自行扩展</em>
     * @param str
     * @return
     */
    public static boolean isNumOrDouble(final String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.matches("^[-\\+]?[.\\d]*$")) {
            return true;
        }
        return false;
    }
}
