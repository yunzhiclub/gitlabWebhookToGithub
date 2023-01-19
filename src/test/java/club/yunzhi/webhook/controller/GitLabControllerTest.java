package club.yunzhi.webhook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(GitLabControllerTest.class);

    @Autowired
    GitLabController gitLabController;


    @Test
    public void pushHook() throws IOException {
    }

    // 测试数据 payload 复制于下面
    String json = "";
}
