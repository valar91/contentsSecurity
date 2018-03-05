package com.ziroom.eunomia.core.support;

import com.google.common.collect.Maps;
import com.ziroom.eunomia.common.exception.SegmentException;
import com.ziroom.eunomia.core.ik.IKSegmenter;
import com.ziroom.eunomia.core.ik.Lexeme;
import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.core.segment.EunoSegmenter;
import lombok.Getter;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class IKSencitiveSupport implements EunoSegmenter {

    @Override
    public List<SegmentWord> segment(String text) throws SegmentException {
        return (List<SegmentWord>) segmentLimit(text,false, 0);
    }

    @Override
    public Set<SegmentWord> segmentDistinct(String text) throws SegmentException {
        return (Set<SegmentWord>) segmentLimit(text,true, 0);
    }

    @Override
    public List<SegmentWord> segmentIgnoreSingle(String text) throws SegmentException {
        return (List<SegmentWord>) segmentLimit(text, false,1);
    }

    @Override
    public Collection<SegmentWord> segmentStatistics(String text) throws SegmentException {
        return segmentGroup(text, 1);
    }

    private Collection<SegmentWord> segmentLimit (String text, boolean isDistinct, int limit) throws SegmentException {
        Collection<SegmentWord> results;
        if (isDistinct) {
            results = new HashSet<>();
        } else {
            results = new ArrayList<>();
        }
        // 细粒度切分
        IKSegmenter ik = new IKSegmenter(
                new StringReader(text), false
        );
        Lexeme ikt = null;
        try {
            ikt = ik.next();
            while (ikt != null) {
                if (ikt.getLength() > limit) {
                    results.add(
                            SegmentWord.builder()
                                    .word(ikt.getLexemeText())
                                    .build()
                    );
                }
                ikt = ik.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Collection<SegmentWord> segmentGroup (String text, int limit) throws SegmentException {
        Aggregator aggregator = Aggregator.newAgg();
        // 细粒度切分
        IKSegmenter ik = new IKSegmenter(
                new StringReader(text), false
        );
        Lexeme ikt = null;
        try {
            ikt = ik.next();
            while (ikt != null) {
                if (ikt.getLength() > limit) {
                    aggregator.add(
                            SegmentWord.builder()
                                    .word(ikt.getLexemeText())
                                    .count(1)
                                    .build()
                    );
                }
                ikt = ik.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aggregator.getStash().values();
    }

    @Getter
    private static class Aggregator {

        private Map<Object, SegmentWord> stash = Maps.newConcurrentMap();

        void add(SegmentWord word) {
            Integer k = key(word);
            SegmentWord stashWord = stash.get(k);
            if (stashWord == null) {
                stash.put(k, word);
            } else {
                merge(stashWord);
            }
        }

        void merge(SegmentWord word) {
            word.increase();
        }

        private Integer key(SegmentWord word) {
            return word.getWord().hashCode();
        }

        static Aggregator newAgg() {
            return new Aggregator();
        }
    }
}
