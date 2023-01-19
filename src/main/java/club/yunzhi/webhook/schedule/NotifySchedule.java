package club.yunzhi.webhook.schedule;

import club.yunzhi.webhook.entities.GitlabRequest;
import club.yunzhi.webhook.service.*;
import club.yunzhi.webhook.util.ResponseUtil;
import club.yunzhi.webhook.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 向钉钉推送消息
 */
@Service
public class NotifySchedule {
    // 当最后一次请求在当前时间10s内时, 不进行处理
    private final Integer NOT_HANDLE_TIME = 10000;

    /**
     * key： access_token, github钉钉机器人的token
     * value 该项目待处理的GitlabRequest请求,类型为双端队列
     */
    private final Map<String, Deque<GitlabRequest>> map = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(GitLabNotifyServiceImpl.class);

    private final CombineEventService combineEventService;

    private final GitLabNotifyService gitLabNotifyService;

    private final SettingService settingService;


    public NotifySchedule(CombineEventService combineEventService,
                          GitLabNotifyService gitLabNotifyService,
                          SettingService settingService) {
        this.combineEventService = combineEventService;
        this.gitLabNotifyService = gitLabNotifyService;
        this.settingService = settingService;
    }

    // 间隔20s处理项目请求
    @Scheduled(fixedRate = 20000)
    private void sendRequest() {
        // 对每个项目的请求队列进行处理
        this.map.forEach((key, value) -> {
            try {
                this.handleQueue(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 处理队列中的请求
     * @param deque 请求队列
     */
    public void handleQueue(Deque<GitlabRequest> deque) throws IOException {
        int size = deque.size();
        // 如果队列没数据,表示这段时间没有请求,直接返回
        if (size == 0) {
            return;
        }
        // 获取队尾元素
        GitlabRequest queueLast = deque.getLast();
        // 是否继续进行处理
        if (IsNotHandle(queueLast.getReceivedTime())) {
            logger.info("最后一次请求在当前时间在10s内，不进行处理");
            return;
        }

        Iterator<GitlabRequest> iterator = deque.iterator();

        while (iterator.hasNext()) {
            // 获取头部元素
            GitlabRequest gitlabRequest = iterator.next();
            // 出栈
            iterator.remove();
            String resultJson;
            // 获取处理合并事件后的json
            resultJson = this.combineEventService.handleEvent(iterator, gitlabRequest);

            if (resultJson == null) {
                logger.info("事件合并，不发送该事件");
                return;
            }
            this.gitLabNotifyService.handleEventData(resultJson, gitlabRequest.getEventName(), gitlabRequest.getSecret());
        }
    }



    /**
     * 将请求添加到hashMap中进行处理
     */
    public ResponseEntity<ResponseVo> putIntoMap(String json, String eventName, String secret) {
        Assert.notNull(json, "json不能为空");
        Assert.notNull(eventName, "eventName不能为空");
        String accessToken = EventService.getAccessToken(secret, settingService);

        if(Objects.equals(accessToken, "")) {
            ResponseVo body = ResponseUtil.error(HttpStatus.BAD_REQUEST,"该请求的secret不存在于数据库中");
            return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        // 找到对应项目的请求队列 若该项目的请求队列不存在，新增队列
        Deque<GitlabRequest> toAddQueue = this.map.computeIfAbsent(accessToken, k -> new LinkedList<>());

        GitlabRequest gitlabRequest = new GitlabRequest();
        gitlabRequest.setJson(json);
        gitlabRequest.setEventName(eventName);
        gitlabRequest.setSecret(secret);
        gitlabRequest.setReceivedTime(new Timestamp(System.currentTimeMillis()));
        // 添加进队列
        toAddQueue.offer(gitlabRequest);

        return new ResponseEntity<>(ResponseUtil.ok(), HttpStatus.OK);
    }

    /**
     * 是否继续处理
     * 当最后一次请求在当前时间 10s 内时, 不进行处理
     *
     * @return true 不处理 false 处理
     */
    private Boolean IsNotHandle(Timestamp receivedTime) {
        return System.currentTimeMillis()  < receivedTime.getTime() + NOT_HANDLE_TIME;
    }
}
