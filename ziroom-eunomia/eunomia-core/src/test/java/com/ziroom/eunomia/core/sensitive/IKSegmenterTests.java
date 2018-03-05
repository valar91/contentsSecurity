//package com.ziroom.eunomia.core.sensitive;
//
//import com.google.common.collect.Sets;
//import com.ziroom.eunomia.common.help.IoResourceHelper;
//import com.ziroom.eunomia.common.util.StrUtils;
//import com.ziroom.eunomia.core.Euno;
//import com.ziroom.eunomia.core.EunomiaCoreApplicationTests;
//import com.ziroom.eunomia.core.ik.config.DefaultConfig;
//import com.ziroom.eunomia.core.ik.dic.Dictionary;
//import com.ziroom.eunomia.core.model.SegmentWord;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.Assert;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Set;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@SpringBootTest(classes = EunomiaCoreApplicationTests.class)
//public class IKSegmenterTests {
//
//    private final static String NORMAL_TEXT = IoResourceHelper.readResourceContent("/text/minus.house.introduction.text");
//
//    private final static Set<String> SENSITIVE_WORDS = Sets.newHashSet("中華人民共和國", "西安", "张少斌");
//
//    @Test
//    public void segmenterTests () throws IOException {
//
//        Dictionary.initial(DefaultConfig.getInstance());
//        Dictionary dic = Dictionary.getSingleton();
//        dic.addWords(SENSITIVE_WORDS);
//
//        Collection<SegmentWord> result = IKSensitive.sgStatistics(NORMAL_TEXT);
//        // assert
//        Assert.notEmpty(result, "segments fail");
//        System.out.println("===============================================");
//        result.stream().forEach(System.out::println);
//        System.out.println("===============================================");
//        System.out.println("===============================================");
//        result.stream()
//                .filter(segmentWord -> !StrUtils.isAlphaEn(segmentWord.getWord()))
//                .filter(segmentWord -> !StrUtils.isNumOrDouble(segmentWord.getWord()))
//                .filter(segmentWord -> !StrUtils.isSimpleNumCn(segmentWord.getWord()))
//                .forEach(System.out::println);
//        System.out.println("===============================================");
//    }
//
//    @Test
//    public void segmenterNoNumcnAndEnTests () throws IOException {
//
//        Dictionary.initial(DefaultConfig.getInstance());
//        Dictionary dic = Dictionary.getSingleton();
//        dic.addWords(SENSITIVE_WORDS);
//
//        Collection<SegmentWord> result = IKSensitive.sgStatistics(NORMAL_TEXT);
//        // assert
//        Assert.notEmpty(result, "segments fail");
//        System.out.println("===============================================");
//        result.stream()
//                .filter(segmentWord -> !StrUtils.isAlphaEn(segmentWord.getWord()))
//                .filter(segmentWord -> !StrUtils.isNumOrDouble(segmentWord.getWord()))
//                .filter(segmentWord -> !StrUtils.isSimpleNumCn(segmentWord.getWord()))
//                .forEach(System.out::println);
//        System.out.println("===============================================");
//    }
//
//    @Test
//    public void surlyFilterTests () throws IOException {
//
//        Dictionary.initial(DefaultConfig.getInstance());
//        Dictionary dic = Dictionary.getSingleton();
//        dic.addWords(SENSITIVE_WORDS);
//
//        Collection<SegmentWord> result = Euno.surlyForSensitive(NORMAL_TEXT);
//        // assert
//        Assert.notEmpty(result, "segments fail");
//        System.out.println("===============================================");
//        result.stream()
//                .forEach(System.out::println);
//        System.out.println("===============================================");
//    }
//
//    @Test
//    public void segmenterNoNumTests () throws IOException {
//
//        Dictionary.initial(DefaultConfig.getInstance());
//        Dictionary dic = Dictionary.getSingleton();
//        dic.addWords(SENSITIVE_WORDS);
//
//        Collection<SegmentWord> result = IKSensitive.sgStatistics(NORMAL_TEXT);
//        // assert
//        Assert.notEmpty(result, "segments fail");
//        result.stream().forEach(System.out::println);
//    }
//
//    @Test
//    public void segmenterNoDicTests () throws IOException {
//
//        Dictionary.initial(DefaultConfig.getInstance());
//        Dictionary dic = Dictionary.getSingleton();
//        //dic.addWords(SENSITIVE_WORDS);
//
//        Collection<SegmentWord> result = IKSensitive.sgStatistics(NORMAL_TEXT);
//        // assert
//        Assert.notEmpty(result, "segments fail");
//        result.stream().forEach(System.out::println);
//    }
//}
