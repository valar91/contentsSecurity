package com.ziroom.eunomia.core.segment;

import com.ziroom.eunomia.common.exception.SegmentException;
import com.ziroom.eunomia.core.model.SegmentWord;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/***
 * 分词
 */
public interface EunoSegmenter {

    /**
     * 分词,返回全部
     * @param text
     * @return
     * @throws SegmentException
     */
    List<SegmentWord> segment (String text) throws SegmentException;

    /**
     * 分词，去重
     * @param text
     * @return
     * @throws SegmentException
     */
    Set<SegmentWord> segmentDistinct (String text) throws SegmentException;

    /**
     * 分词结果过滤，长度大于1
     * @param text
     * @return
     * @throws SegmentException
     */
    List<SegmentWord> segmentIgnoreSingle (String text) throws SegmentException;

    /**
     * 分词并统计出现次数
     * @param text
     * @return
     * @throws SegmentException
     */
    Collection<SegmentWord> segmentStatistics (String text) throws SegmentException;
}
