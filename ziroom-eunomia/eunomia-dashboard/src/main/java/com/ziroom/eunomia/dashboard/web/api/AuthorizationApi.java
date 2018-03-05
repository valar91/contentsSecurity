package com.ziroom.eunomia.dashboard.web.api;

import com.ziroom.eunomia.common.model.ResultModel;
import com.ziroom.eunomia.common.model.ResultStatus;
import com.ziroom.eunomia.dashboard.manager.TokenManager;
import com.ziroom.eunomia.dashboard.model.TokenModel;
import com.ziroom.eunomia.dashboard.model.dto.Result;
import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
import com.ziroom.eunomia.dashboard.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>api鉴权控制</p>
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
@RequestMapping(value = "/api/oauth2/token")
public class AuthorizationApi {

    @Autowired
    private OrgService orgService;

    @Autowired
    private TokenManager tokenManager;

    /***
     * 获取授权码
     * @param username 登录名
     * @param password 密码
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultModel> tokenPost(String username, String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        OrgEntity org = orgService.findByUsername(username);

        if (org == null ||
                !org.getOrgSecret().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.METHOD_NOT_ALLOWED);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(org.getFid());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResultModel> tokenGet(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        OrgEntity org = orgService.findByUsername(username);

        if (org == null ||
                !org.getOrgSecret().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.METHOD_NOT_ALLOWED);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(org.getFid());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }


}
