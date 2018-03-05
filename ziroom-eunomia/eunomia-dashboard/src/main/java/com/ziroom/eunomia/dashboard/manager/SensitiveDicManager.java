package com.ziroom.eunomia.dashboard.manager;

import com.google.common.collect.Maps;
import com.ziroom.eunomia.common.model.SensitiveWord;
import com.ziroom.eunomia.core.ik.config.DefaultConfig;
import com.ziroom.eunomia.core.ik.dic.Dictionary;
import com.ziroom.eunomia.dashboard.load.SensitiveLoad;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>敏感词管理</p>
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
@Slf4j
@Component
public class SensitiveDicManager {

    @Autowired
    private SensitiveLoad sensitiveLoad;

    // 词典仓库
    private static Dictionary DIC;

    // 词库池
    private final Map<String, SensitiveWord> sensitivePoll = Maps.newConcurrentMap();

    public void add(SensitiveWordEntity s) {

        add(of(s));
    }

    public void add(SensitiveWord sensitive) {

        if (!sensitive.isEnabled()){
            return;
        }

        String skey = key(sensitive);

        if (sensitivePoll.get(skey) != null) {
            return;
        }

        addDic(sensitive);
    }

    // 批量新增敏感词
    private void addDics(List<SensitiveWord> newlyWords) {
        if (!CollectionUtils.isEmpty(newlyWords)) {
            DIC.addWords(
                    wordsOfList(newlyWords)
            );
            newlyWords.clear();
        }
    }

    // 新增词条
    private void addDic(SensitiveWord sensitive) {
        if (sensitive != null && StringUtils.isNoneBlank(sensitive.getWord())) {
            sensitivePoll.put(
                    key(sensitive),
                    sensitive
            );
            DIC.addWord(sensitive.getWord());
        }
    }

    // 批量删除敏感词
    private void removeDics(List<SensitiveWord> disabledWords) {
        if (!CollectionUtils.isEmpty(disabledWords)) {
            DIC.disableWords(
                    wordsOfList(disabledWords)
            );
            disabledWords.clear();
        }
    }

    public void removeDic(SensitiveWordEntity s) {

        SensitiveWord sensitive = of(s);

        if (sensitive != null && StringUtils.isNoneBlank(sensitive.getWord())) {
            sensitivePoll.remove(
                    key(sensitive)
            );
            DIC.disableWord(sensitive.getWord());
        }
    }

    // 删除敏感词
    public void removeDic(SensitiveWord sensitive) {

        if (sensitive != null && StringUtils.isNoneBlank(sensitive.getWord())) {
            sensitivePoll.remove(
                    key(sensitive)
            );
            DIC.disableWord(sensitive.getWord());
        }
    }

    private List<String> wordsOfList(List<SensitiveWord> words) {
        return words.stream()
                .map(SensitiveWord::getWord)
                .distinct()
                .collect(
                        Collectors.toList()
                );
    }

    @PostConstruct
    public void init() {
        dicConfig();
        sensitiveLoad.load();
    }

    private void dicConfig() {
        Dictionary.initial(DefaultConfig.getInstance());
        DIC = Dictionary.getSingleton();
    }

    public SensitiveWord findByKey(int key) {
        return sensitivePoll.get(key);
    }

    // 用Fid做为Key
    private String key(SensitiveWord sensitive) {
        return sensitive.getFid();
    }

    public SensitiveWord of(SensitiveWordEntity s) {

        if (s == null || s.isBlank()) {
            throw new NullPointerException();
        }
        return SensitiveWord.builder()
                .enabled(s.enabled())
                .fid(s.getFid())
                .key(s.getContent().hashCode())
                .word(s.getContent())
                .build();
    }
}
