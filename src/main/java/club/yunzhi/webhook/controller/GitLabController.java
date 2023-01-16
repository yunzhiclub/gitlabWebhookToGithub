package club.yunzhi.webhook.controller;

import club.yunzhi.webhook.schedule.NotifySchedule;
import club.yunzhi.webhook.service.GitLabNotifyService;
import club.yunzhi.webhook.util.ResponseUtil;
import club.yunzhi.webhook.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * GitLab对应C层，接受GitLab发出的请求
 */
@RestController
@RequestMapping("/oapi.dingtalk.com/robot/send")
@Slf4j
public class GitLabController {

    @Autowired
    private NotifySchedule notifySchedule;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVo pushHook(@RequestBody String json,
                               @RequestHeader(name = "X-Gitlab-Event") String event,
                               @RequestHeader(name = "X-Gitlab-Token") String secret) {
        // 异步处理 直接向gitlab返回ok
        notifySchedule.putIntoMap(json, event, secret);
        return ResponseUtil.ok();
    }
}
