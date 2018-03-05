package com.ziroom.eunomia.dashboard.service.impl;

import com.asura.framework.base.util.UUIDGenerator;
import com.github.pagehelper.Page;

import com.github.pagehelper.PageHelper;
import com.ziroom.eunomia.dashboard.config.db.ReadOnly;
import com.ziroom.eunomia.dashboard.config.util.UserUtil;
import com.ziroom.eunomia.dashboard.manager.SensitiveDicManager;
import com.ziroom.eunomia.dashboard.mapper.SensitiveOpLogEntityMapper;
import com.ziroom.eunomia.dashboard.mapper.SensitiveWordEntityMapper;
import com.ziroom.eunomia.dashboard.model.dto.SensitiveWordRequest;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveOpLogEntity;
import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
import com.ziroom.eunomia.dashboard.model.valenum.SensitiveStatusEnum;
import com.ziroom.eunomia.dashboard.model.valenum.SensitiveTypeEnum;
import com.ziroom.eunomia.dashboard.service.SensitiveWordService;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>敏感词处理</p>
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
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Autowired
    private SensitiveDicManager sensitiveDicManager;

    @Autowired
    private SensitiveWordEntityMapper sensitiveWordEntityMapper;

    @Autowired
    private SensitiveOpLogEntityMapper sensitiveOpLogEntityMapper;

    /**
     * 
     * 新增敏感词
     * 
     * @author zhangyl2
     * @created 2017年11月15日 15:29
     * @param 
     * @return 
     */
    @Transactional
    @Override
    public int addSensitiveWord(SensitiveWordEntity sensitiveWordEntity) {

        // 审核状态
        int sensitiveWordStatus = 0;
        // 敏感词Fid
        String fid = UUIDGenerator.hexUUID();

        // 黄反词 默认状态：审核通过
        if (sensitiveWordEntity.getSensitiveWordType() == SensitiveTypeEnum.YELLOW_REACTIONARY.getCode()) {

            sensitiveWordStatus = SensitiveStatusEnum.AUDIT_PASSED.getCode();

        } else if (sensitiveWordEntity.getSensitiveWordType() == SensitiveTypeEnum.ADVERTISEMENT.getCode()
                || sensitiveWordEntity.getSensitiveWordType() == SensitiveTypeEnum.COMPETING_PRODUCTS.getCode()) {

            // 广告词 竞品词 默认状态：待审核
            sensitiveWordStatus = SensitiveStatusEnum.PENDING_AUDIT.getCode();
        }

        sensitiveWordEntity.setSensitiveWordStatus(sensitiveWordStatus);
        sensitiveWordEntity.setFid(fid);
        int result = sensitiveWordEntityMapper.addSensitiveWord(sensitiveWordEntity);

        // 保存成功记录日志
        if (result == 1) {

            String remark = String.format("新增：敏感词{%s}：类型{%s}，审核状态{%s}",
                    sensitiveWordEntity.getContent(),
                    SensitiveTypeEnum.getNameBycode(sensitiveWordEntity.getSensitiveWordType()),
                    SensitiveStatusEnum.getNameBycode(sensitiveWordStatus));

            UpsUserVo user = UserUtil.getUpsUserMsg();
            saveLog(sensitiveWordEntity.getFid(), user.getEmployeeEntity().getEmpCode(), user.getEmployeeEntity().getEmpName(), sensitiveWordStatus, sensitiveWordStatus, remark);

            // 审核通过的词添加到词库
            if (sensitiveWordStatus == SensitiveStatusEnum.AUDIT_PASSED.getCode()){
                sensitiveDicManager.add(sensitiveWordEntity);
            }
        }
        return result;
    }


    /**
     * 
     * 修改敏感词
     * 
     * @author zhangyl2
     * @created 2017年11月15日 15:59
     * @param 
     * @return 
     */
    @Transactional
    @Override
    public int updateSensitiveWord(SensitiveWordEntity sensitiveWordEntity) {
        // 查库信息，
        SensitiveWordEntity sensitiveWordEntityDb = sensitiveWordEntityMapper.selectOne(sensitiveWordEntity.getFid());
        int sensitiveWordStatus = sensitiveWordEntityDb.getSensitiveWordStatus();

        // 待审核|审核驳回 才能修改
        if(sensitiveWordStatus == SensitiveStatusEnum.PENDING_AUDIT.getCode() || sensitiveWordStatus == SensitiveStatusEnum.AUDIT_REJECTION.getCode()){
            // 内容|类型 必须有改动
            if(!sensitiveWordEntity.getContent().equals(sensitiveWordEntityDb.getContent())
                    || !sensitiveWordEntity.getSensitiveWordType().equals(sensitiveWordEntityDb.getSensitiveWordType())){

                // 同时修改成 待审核 状态
                sensitiveWordEntity.setSensitiveWordStatus(SensitiveStatusEnum.PENDING_AUDIT.getCode());

                int result = sensitiveWordEntityMapper.updateSensitiveWord(sensitiveWordEntity);
                if (result == 1) {
                    String remark = String.format("修改：敏感词{%s}->{%s}：类型{%s}->{%s}",
                            sensitiveWordEntityDb.getContent(),
                            sensitiveWordEntity.getContent(),
                            SensitiveTypeEnum.getNameBycode(sensitiveWordEntityDb.getSensitiveWordType()),
                            SensitiveTypeEnum.getNameBycode(sensitiveWordEntity.getSensitiveWordType()));

                    UpsUserVo user = UserUtil.getUpsUserMsg();
                    saveLog(sensitiveWordEntity.getFid(), user.getEmployeeEntity().getEmpCode(), user.getEmployeeEntity().getEmpName(),
                            sensitiveWordEntityDb.getSensitiveWordStatus(), sensitiveWordEntity.getSensitiveWordStatus(), remark);

                    return result;
                }
            }
        }

        return 0;
    }

    /**
     * 
     * 删除敏感词，记录日志
     * 
     * @author zhangyl2
     * @created 2017年11月15日 15:03
     * @param 
     * @return 
     */
    @Transactional
    @Override
    public int deleteSensitiveWord(SensitiveWordEntity sensitiveWordEntity, boolean forceDelete){
        SensitiveWordEntity sensitiveWordEntityDb = sensitiveWordEntityMapper.selectOne(sensitiveWordEntity.getFid());
        int originStatus = sensitiveWordEntityDb.getSensitiveWordStatus();
        // 为无条件删除，或只能删除 待审核|审核驳回 的敏感词
        if(forceDelete || (originStatus == SensitiveStatusEnum.PENDING_AUDIT.getCode()
                            || originStatus == SensitiveStatusEnum.AUDIT_REJECTION.getCode())){
            if(sensitiveWordEntityDb.getIsDel() == 0) {
                int result = sensitiveWordEntityMapper.deleteSensitiveWord(sensitiveWordEntity);
                if (result == 1) {
                    String remark = String.format("删除：敏感词{%s}：类型{%s}",
                            sensitiveWordEntityDb.getContent(),
                            SensitiveTypeEnum.getNameBycode(sensitiveWordEntityDb.getSensitiveWordType()));
                    UpsUserVo user = UserUtil.getUpsUserMsg();
                    saveLog(sensitiveWordEntity.getFid(), user.getEmployeeEntity().getEmpCode(), user.getEmployeeEntity().getEmpName(),
                            sensitiveWordEntityDb.getSensitiveWordStatus(), sensitiveWordEntityDb.getSensitiveWordStatus(), remark);

                    // 从词库移除
                    sensitiveDicManager.removeDic(sensitiveWordEntityDb);
                }
                return result;
            }
        }
        return 0;
    }

    /**
     *
     * 分页查敏感词
     *
     * @author zhangyl2
     * @created 2017年11月01日 20:55
     * @param
     * @return
     */
    @ReadOnly
    @Override
    public Page<SensitiveWordEntity> selectByPage(SensitiveWordRequest sensitiveWordRequest) {
        PageHelper.startPage(sensitiveWordRequest.getPage(), sensitiveWordRequest.getLimit());
        return sensitiveWordEntityMapper.selectByPage(sensitiveWordRequest);
    }

    /**
     * 
     * 审核更新词库状态
     * 
     * @author zhangyl2
     * @created 2017年11月01日 19:22
     * @param 
     * @return 
     */
    @Transactional
    @Override
    public int auditSensitiveWord(SensitiveWordEntity sensitiveWordEntity) {
        // 查库信息，
        SensitiveWordEntity sensitiveWordEntityDb = sensitiveWordEntityMapper.selectOne(sensitiveWordEntity.getFid());
        int fromStatus = sensitiveWordEntityDb.getSensitiveWordStatus();

        int toStatus = sensitiveWordEntity.getSensitiveWordStatus();

        // 是否有效操作
        boolean isValid;

        // 从 待审核 更新到 审核通过/审核驳回
        if(fromStatus != toStatus && fromStatus == SensitiveStatusEnum.PENDING_AUDIT.getCode()){
            isValid = true;
        // 从 审核驳回 更新到 审核通过
        }else if(fromStatus == SensitiveStatusEnum.AUDIT_REJECTION.getCode()
                && toStatus == SensitiveStatusEnum.AUDIT_PASSED.getCode()){
            isValid = true;
        }else{
            isValid = false;
        }

        if(isValid){
            int result = sensitiveWordEntityMapper.auditSensitiveWord(sensitiveWordEntity);
            if (result == 1) {
                String remark = String.format("审核：敏感词{%s}：类型{%s}，状态{%s}->{%s}",
                        sensitiveWordEntityDb.getContent(),
                        SensitiveTypeEnum.getNameBycode(sensitiveWordEntityDb.getSensitiveWordType()),
                        SensitiveStatusEnum.getNameBycode(fromStatus),
                        SensitiveStatusEnum.getNameBycode(toStatus));

                UpsUserVo user = UserUtil.getUpsUserMsg();
                saveLog(sensitiveWordEntity.getFid(), user.getEmployeeEntity().getEmpCode(), user.getEmployeeEntity().getEmpName(), fromStatus, toStatus, remark);

                if (toStatus == SensitiveStatusEnum.AUDIT_PASSED.getCode()) {
                    sensitiveWordEntityDb.setSensitiveWordStatus(toStatus);
                    sensitiveDicManager.add(sensitiveWordEntityDb);
                }

                return result;
            }
        }

        return 0;
    }

    @ReadOnly
    @Override
    public List<SensitiveWordEntity> selectAll() {
        return sensitiveWordEntityMapper.selectAll();
    }

    @ReadOnly
    @Override
    public List<SensitiveWordEntity> selectByLastModiyTime(Timestamp start, Timestamp end) {
        return sensitiveWordEntityMapper.selectByLastModifytime(start, end);
    }

    /**
     * 保存操作日志
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年11月01日 17:10
     */
    private int saveLog(String sensitiveWordFid, String empCode, String empName, Integer fromStatus, Integer toStatus, String remark) {
        SensitiveOpLogEntity logEntity = new SensitiveOpLogEntity();
        logEntity.setFid(UUIDGenerator.hexUUID());
        logEntity.setSensitiveWordFid(sensitiveWordFid);
        logEntity.setEmpCode(empCode);
        logEntity.setEmpName(empName);
        logEntity.setFromStatus(fromStatus);
        logEntity.setToStatus(toStatus);
        logEntity.setRemark(remark);
        return sensitiveOpLogEntityMapper.insert(logEntity);
    }
}
