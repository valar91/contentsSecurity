package com.ziroom.eunomia.dashboard.service.impl;

import com.asura.framework.base.util.UUIDGenerator;
import com.google.common.collect.Lists;
import com.ziroom.eunomia.common.exception.SensitiveException;
import com.ziroom.eunomia.common.model.dto.SensitiveRequestDto;
import com.ziroom.eunomia.core.Euno;
import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.core.model.SensitiveOnlineCheck;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import com.ziroom.eunomia.dashboard.log.JsonLogger;
import com.ziroom.eunomia.dashboard.mapper.OrgRequestEntityMapper;
import com.ziroom.eunomia.dashboard.mapper.OrgSensitiveResultEntityMapper;
import com.ziroom.eunomia.dashboard.model.entity.OrgRequestEntity;
import com.ziroom.eunomia.dashboard.model.entity.OrgSensitiveResultEntity;
import com.ziroom.eunomia.dashboard.service.SensitiveService;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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
@Slf4j
@Service
public class SensitiveServiceImpl implements SensitiveService {

    @Autowired
    private OrgSensitiveResultEntityMapper orgSensitiveResultEntityMapper;

    @Autowired
    private OrgRequestEntityMapper orgRequestEntityMapper;

    @Override
    public List<SegmentWord> check(String text) throws Exception {

        List<SegmentWord> segmentWords = Euno.surlyForSensitive(text);

        loged(segmentWords, text);

        return segmentWords;
    }

    private void loged(List<SegmentWord> segmentWords, String text) {
        try{
            //csvFileWriter.write(onlineCheck);
            JsonLogger.log(segmentWords);
            UpsUserVo user = UserUtil.getUpsUserMsg();

            EmployeeEntity employee = user.getEmployeeEntity();

            SensitiveOnlineCheck onlineCheck = SensitiveOnlineCheck.builder()
                    .topic("dashboard")
                    .empCode(employee.getEmpCode())
                    .empName(employee.getEmpName())
                    .content(text)
                    .segmentWords(segmentWords)
                    .currentTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            //log.info(JSON.toJSONString(onlineCheck));
            JsonLogger.log(onlineCheck);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }

    /**
     * 校验文本敏感词，并且存储校验记录
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(timeout = 30, rollbackFor = {SensitiveException.class, Exception.class})
    public List<SegmentWord> checkAndKeep(SensitiveRequestDto requestDto) throws Exception {

        List<SegmentWord> segmentWords = Collections.EMPTY_LIST;
        final String content = requestDto.getContent();

        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }
        try {
            // 组织机构文本内容存储
            final String ordRequestFid = UUIDGenerator.hexUUID();//
            int reqFlag = orgRequestEntityMapper.insertSelective(
                    OrgRequestEntity.builder()
                            .fid(ordRequestFid)
                            .orgFid(requestDto.getOrgFid())
                            .content(content)
                            .build()
            );

            if (! (reqFlag > 0)) {
                throw new SensitiveException("insert org request content fail!");
            }

            // segment and filter and insert
            List<OrgSensitiveResultEntity> orgSensitiveResultEntities;// 过滤结果
            segmentWords = Euno.surlyForSensitive(content);

            if (!CollectionUtils.isEmpty(segmentWords)) {
                // 组织机构文本过滤结果存储
                orgSensitiveResultEntities = Lists.newArrayListWithCapacity(segmentWords.size());
                // 组织机构敏感
                segmentWords.stream()
                        .forEach(s -> {
                            orgSensitiveResultEntities.add(
                                    OrgSensitiveResultEntity.builder()
                                            .fid(UUIDGenerator.hexUUID())
                                            .requestFid(ordRequestFid)
                                            .sensitiveWordFid("")
                                            .sensitiveCount(s.getCount())
                                            .sensitiveWord(s.getWord())
                                            .createDate(Timestamp.valueOf(LocalDateTime.now()))
                                            .build()
                            );
                        });
                if (!CollectionUtils.isEmpty(orgSensitiveResultEntities)) {
                    Integer flag = orgSensitiveResultEntityMapper.insertBatch(orgSensitiveResultEntities);
                    if (flag <= 0) {
                        throw new SensitiveException("roll back! insert org request with sensitive words result fail!");
                    }
                }
            }
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SensitiveException("mybatis fail : " + e.getMessage());
        } catch (BadSqlGrammarException e) {
            log.error(e.getMessage(), e);
            throw new SensitiveException("mybatis sql error : " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SensitiveException(e.getMessage());
        }

        return segmentWords;
    }
}
