package com.ziroom.eunomia.core;

import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.core.sensitive.IKSensitive;

import java.util.List;

public final class Euno {

    private Euno() {
    }

    public static List<SegmentWord> surlyForSensitive(String text) {

        return IKSensitive.surlyFilter(text);
    }

    public static List<SegmentWord> segment(String text) {

        return IKSensitive.sgStatistics(text);
    }

    public static List<SegmentWord> ikNoNum(String text) {

        return IKSensitive.sgFilterNum(text);
    }

}
