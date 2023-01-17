package club.yunzhi.webhook.controller;

import club.yunzhi.webhook.schedule.NotifySchedule;
import club.yunzhi.webhook.service.GitLabNotifyService;
import club.yunzhi.webhook.util.ResponseUtil;
import club.yunzhi.webhook.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * GitLab对应C层，接受GitLab发出的请求
 */
@RestController
@RequestMapping("")
@Slf4j
public class GitLabController {

    @Autowired
    private NotifySchedule notifySchedule;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pushHook(@RequestBody String json,
                               @RequestHeader(name = "X-Gitlab-Event") String event,
                               @RequestHeader(name = "X-Gitlab-Token", required = false) String secret) {
        if (secret == null) {
            ResponseVo body = ResponseUtil.error(HttpStatus.UNAUTHORIZED,"未在系统中添加secret, 请求失败");
            return  new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
       return notifySchedule.putIntoMap(json, event, secret);
    }
}
