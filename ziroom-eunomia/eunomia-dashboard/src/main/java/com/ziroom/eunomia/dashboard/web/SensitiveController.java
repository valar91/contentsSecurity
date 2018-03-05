package com.ziroom.eunomia.dashboard.web;

import com.ziroom.eunomia.common.model.ResultModel;
import com.ziroom.eunomia.common.model.ResultStatus;
import com.ziroom.eunomia.common.model.dto.SensitiveRequestDto;
import com.ziroom.eunomia.core.model.SegmentWord;
import com.ziroom.eunomia.dashboard.model.vo.SensitiveVo;
import com.ziroom.eunomia.dashboard.service.SensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>敏感词</p>
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
@RestController
@RequestMapping(value = "/sensitive")
public class SensitiveController {

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(
            value = {""},
            method = {RequestMethod.POST}
    )
    public Object sensitive (SensitiveRequestDto sensitiveRequestDto) {

        final String content = sensitiveRequestDto.getContent();

        if (StringUtils.isBlank(content)){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.MISS_PARAMETER), HttpStatus.METHOD_NOT_ALLOWED);
        }

        try {
            List<SegmentWord> segmentWords = sensitiveService.check(content);
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
