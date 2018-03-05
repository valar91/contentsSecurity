//package com.ziroom.eunomia.dashboard.service;
//
//import com.ziroom.eunomia.dashboard.model.entity.OrgEntity;
//import com.ziroom.eunomia.dashboard.model.entity.SensitiveWordEntity;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.Assert;
//
//import java.util.List;
//
///**
// * <p>应用单元测试</p>
// * todo ： mock 接入，不直接查库
// * todo ： 集成测试，加上内存数据库，并事务回滚
// * <PRE>
// * <BR>    修改记录
// * <BR>-----------------------------------------------
// * <BR>    修改日期         修改人          修改内容
// * </PRE>
// *
// * @Author phil
// * @Date Created in 2017年11月09日 14:27
// * @Version 1.0
// * @Since 1.0
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//public class OrgServiceTests {
//
//    @Autowired
//    private OrgService orgService;
//
//    @Test
//    public void selectAllTest() {
//
//        OrgEntity org = orgService.findByUsername("ziroomyi");
//
//        Assert.notNull(org, "no any sensitive word in store");
//    }
//}
