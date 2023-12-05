package com.zhouyf.test;

import com.zhouyf.StartDistributedLockApplication;
import com.zhouyf.task.ResourceTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = StartDistributedLockApplication.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Slf4j
public class TestResourceTask {
    @Autowired
    private ResourceTask resourceTask;

    @Test
    void test(){
        for(int x = 0; x < 10; x++){
            this.resourceTask.handle("zhouyf" + x);
        }
    }
}
