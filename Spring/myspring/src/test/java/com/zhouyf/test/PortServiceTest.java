package com.zhouyf.test;

import com.zhouyf.config.HikariDataSourceConfig;
import com.zhouyf.pojo.Post;
import com.zhouyf.service.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HikariDataSourceConfig.class)
public class PortServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortServiceTest.class);
    @Autowired
    private BlogService blogService;
    @Test
    void test(){
        blogService.addPost("减轻偷猎问题","赞比亚的某个村庄采取养蜂业来降低偷猎的影响");
        blogService.addPost("巴以冲突","联合国安理会关注加沙地带的冲突和暴力事件");
        blogService.addPost("创意能改变世界吗？","全球政府担忧外国投资者可以通过ISDS规则，针对减少其利润的新法律或政策起诉它们");
        blogService.addPost("COVID-19疫苗犹豫", "分享‘实时’数据，保持信息简明一致有助于解决疫苗犹豫问题");
        blogService.addPost("女性与战争", "探讨战争对女性的影响，以及女性如何在战争和冲突中发挥作用");
        blogService.addPost("战争罪行", "拜登被视为否认和支持以色列正在进行的战争罪行的首脑");
        blogService.removePost(2);
        blogService.updatePost(6, "战争的罪行");
        List<Post> posts = blogService.showAllPosts();
        for (Post post : posts) {
            System.out.println(post);
        }
    }
}
