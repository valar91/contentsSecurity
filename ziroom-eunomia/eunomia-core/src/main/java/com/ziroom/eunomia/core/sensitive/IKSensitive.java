package com.ziroom.eunomia.core.sensitive;

import com.google.common.collect.Lists;
import com.ziroom.eunomia.common.util.StrUtils;
import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.core.segment.EunoSegmenter;
import com.ziroom.eunomia.core.support.IKSencitiveSupport;

import java.util.*;
import java.util.stream.Collectors;

public class IKSensitive {

    private static EunoSegmenter SEGMENT = new IKSencitiveSupport();

    public static List<SegmentWord> sgStatistics (String text) {
        return segStatistics(text);
    }

    public static List<SegmentWord> sgFilterNum(String text) {
        return segStatistics(text).stream()
                .filter(segmentWord -> !StrUtils.isSimpleNumCn(segmentWord.getWord()))
                .filter(segmentWord -> !StrUtils.isAlphaEn(segmentWord.getWord()))
                .filter(segmentWord -> !StrUtils.isNumOrDouble(segmentWord.getWord()))
                .collect(Collectors.toList());
    }

    private static List<SegmentWord> segStatistics(String text) {
        List<SegmentWord> words = Collections.EMPTY_LIST;
        Collection<SegmentWord> segmentWords = SEGMENT.segmentStatistics(text);
        if (segmentWords != null) {
            words = Lists.newArrayList(segmentWords);
        }
        return words;
    }

    public static List<SegmentWord> surlyFilter(String text) {
        return segStatistics(text).stream()
                .filter(segmentWord -> !StrUtils.isSimpleNumCn(segmentWord.getWord()))
                .filter(segmentWord -> !StrUtils.isAlphaEn(segmentWord.getWord()))
                .filter(segmentWord -> !StrUtils.isNumOrDouble(segmentWord.getWord()))
                .collect(Collectors.toList());
    }

}
