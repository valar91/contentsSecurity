package com.ziroom.eunomia.dashboard.web.api;

import com.ziroom.eunomia.common.model.ResultModel;
import com.ziroom.eunomia.common.model.ResultStatus;
import com.ziroom.eunomia.common.model.dto.SensitiveRequestDto;
import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.dashboard.annotation.Authorization;
import com.ziroom.eunomia.dashboard.annotation.CurrentUser;
import com.ziroom.eunomia.dashboard.annotation.CurrentUserFid;
import com.ziroom.eunomia.dashboard.model.dto.Result;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.model.vo.SensitiveVo;
import com.ziroom.eunomia.dashboard.service.SensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>敏感词对外API服务</p>
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
@RestController
@Slf4j
@RequestMapping(value = "/api/sensitive")
public class SensitiveApi {

    @Autowired
    private SensitiveService sensitiveService;

    /**
     * 过滤文本
     * @param sensitiveRequestDto
     * @param orgFid 调用方Fid
     * @return
     */
    @CrossOrigin
    @Authorization
    @RequestMapping(
            method = {RequestMethod.POST,RequestMethod.GET},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
            )
    public Object sensitive (@RequestBody SensitiveRequestDto sensitiveRequestDto, @CurrentUserFid String orgFid) {

        final String content = sensitiveRequestDto.getContent();

        if (StringUtils.isBlank(content) || StringUtils.isBlank(orgFid)){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.MISS_PARAMETER), HttpStatus.METHOD_NOT_ALLOWED);
        }

        sensitiveRequestDto.setOrgFid(orgFid);

        try {
            List<SegmentWord> segmentWords = sensitiveService.checkAndKeep(sensitiveRequestDto);
            SensitiveVo sensitiveVo = SensitiveVo.builder()
                    .isSensitive(CollectionUtils.isEmpty(segmentWords) ? false : true)
                    .ikList(segmentWords)
                    .build();
            return new ResponseEntity<>(ResultModel.ok(sensitiveVo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROE), HttpStatus.BAD_GATEWAY);
        }
    }



}
