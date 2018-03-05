package com.ziroom.eunomia.dashboard.service;

import com.ziroom.eunomia.common.model.dto.SensitiveRequestDto;
import com.ziroom.eunomia.core.model.SegmentWord;

import java.util.List;

/**
 * <p>敏感词逻辑</p>
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
public interface SensitiveService {

    List<SegmentWord> check(String text) throws Exception;

    /***
     * 校验文本敏感词，并且存储校验记录
     * @param requestDto
     * @return
     * @throws Exception
     */
    List<SegmentWord> checkAndKeep(SensitiveRequestDto requestDto) throws Exception;
}
