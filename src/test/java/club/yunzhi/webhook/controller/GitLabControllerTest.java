package club.yunzhi.webhook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabControllerTest {

    @Autowired
    GitLabController gitLabController;


    @Test
    public void pushHook() throws IOException {
        // event换成自己要测试的数据
//        gitLabController.pushHook(json, "Note Hook");
    }

    // 测试数据 payload 复制于下面
    String json = "";
}