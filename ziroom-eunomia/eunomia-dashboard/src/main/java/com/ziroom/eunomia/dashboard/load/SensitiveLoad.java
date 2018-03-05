package com.ziroom.eunomia.dashboard.load;

import com.ziroom.eunomia.common.exception.SensitiveException;
import com.ziroom.eunomia.common.model.SensitiveWord;
import com.ziroom.eunomia.dashboard.manager.SensitiveDicManager;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
import com.ziroom.eunomia.dashboard.service.SensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>敏感词热加载器</p>
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
public class SensitiveLoad implements Load{

    @Autowired
    private SensitiveDicManager sensitiveManager;

    @Autowired
    private SensitiveWordService sensitiveWordService;

    private final Timer timer = new Timer("sensitive-db-load");

    private final long delay = 20 * 1000;

    private final long period = 5 * 60 * 60 * 1000; // 5 hour

    private final long lastLoadPeriod = 5 * 60 * 1000; // 5 min

    @Override
    public void load() {
        timer.scheduleAtFixedRate(
                new LoadAllTask(),
                delay,
                period
        );
        timer.scheduleAtFixedRate(
                new LoadLastEditTask(lastLoadPeriod),
                lastLoadPeriod,
                lastLoadPeriod
        );
    }

    class LoadAllTask extends TimerTask {

        @Override
        public void run() {
            log.info("============begin load all sensitive words from mysql===========");
            long start = System.currentTimeMillis();
            try {
                List<SensitiveWordEntity> wordEntities = sensitiveWordService.selectAll();
                if (CollectionUtils.isEmpty(wordEntities)) {
                    throw new SensitiveException("load all sensitive words : none");
                }
                log.info("load all sensitive words size : {}", wordEntities.size());
                wordEntities.stream()
                        .forEach(s -> {
                            sensitiveManager.add(
                                    SensitiveWord.builder()
                                            .enabled(s.enabled())
                                            .fid(s.getFid())
                                            .key(s.getContent().hashCode())
                                            .word(s.getContent())
                                            .build()
                            );
                        });
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.info("============end load all sensitive words from mysql , load time : {}===========", (System.currentTimeMillis() - start));
        }
    }

    class LoadLastEditTask extends TimerTask {

        private long begin = System.currentTimeMillis();

        private long period;

        private final long offset = 2 * 60 * 1000;

        public LoadLastEditTask(long lastLoadPeriod) {
            period = lastLoadPeriod;
        }

        @Override
        public void run() {
            log.info("============begin load modify sensitive words from mysql===========");
            long start = System.currentTimeMillis();
            try {
                List<SensitiveWordEntity> wordEntities = sensitiveWordService.selectByLastModiyTime(new Timestamp(begin-offset),new Timestamp(System.currentTimeMillis()));
                if (CollectionUtils.isEmpty(wordEntities)) {
                    log.info("none modify sensitive words , return");
                    return;
                }
                log.info("modify sensitive words size : {}", wordEntities.size());
                wordEntities.stream()
                        .forEach(s -> {
                            SensitiveWord sensitive = SensitiveWord.builder()
                                    .enabled(s.enabled())
                                    .fid(s.getFid())
                                    .key(s.getContent().hashCode())
                                    .word(s.getContent())
                                    .build();

                            if (!s.enabled()) {
                                sensitiveManager.removeDic(sensitive);
                                return;
                            }
                            sensitiveManager.add(sensitive);
                        });
                begin = System.currentTimeMillis() - period;

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.info("============end load modify sensitive words from mysql , load time : {}===========", (System.currentTimeMillis() - start));
        }
    }
}
