package club.yunzhi.webhook.schedule;

import club.yunzhi.webhook.entities.GitlabRequest;
import club.yunzhi.webhook.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    // 间隔5s发送一次
    private final Integer INTERVAL = 5000;

    // 当最后一次请求在当前时间2s内时, 不进行处理
    private final Integer NOT_HANDLE_TIME = 2000;

    /**
     * key： access_token, github钉钉机器人的token
     * value 该项目待处理的GitlabRequest请求
     */
    private final Map<String, Queue<GitlabRequest>> map = new ConcurrentHashMap<>();

    /**
     * 处理队列
     */
    private final Queue<GitlabRequest> queue = new LinkedBlockingQueue<>();
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


    @Scheduled(fixedRate = INTERVAL)
    private void sendRequest() throws IOException {
        // 对每个项目的请求进行处理
        this.map.forEach((key, value) -> {

        });
    }

    /**
     * 处理队列中的请求
     * @param queue 请求队列
     */
    public void handleQueue(Queue<GitlabRequest> queue) throws IOException {
        int size = queue.size();
        // 如果队列没数据,表示这段时间没有请求,直接返回
        if (size == 0) {
            return;
        }
        Iterator<GitlabRequest> iterator = queue.iterator();

        while (iterator.hasNext()) {
            // 获取头部元素
            GitlabRequest gitlabRequest = iterator.next();
            // 是否继续进行处理
            if (IsNotHandle(gitlabRequest.getReceivedTime())) {
                break;
            }
            // 出栈
            iterator.remove();
            String resultJson = "";
            // 获取处理合并事件后的json
            resultJson = this.combineEventService.handleEvent(this.queue, gitlabRequest);

            if (resultJson == null) {
                logger.info("事件合并，不发送该事件");
            }
            this.gitLabNotifyService.handleEventData(resultJson, gitlabRequest.getEventName(), gitlabRequest.getSecret());
        }
    }



    /**
     * 将请求添加到hashMap中进行处理
     */
    @Async
    public void putIntoMap(String json, String eventName, String secret) {
        Assert.notNull(json, "json不能为空");
        Assert.notNull(eventName, "eventName不能为空");
        Assert.notNull(secret, "secret不能为空");

        String accessToken = EventService.getAccessToken(secret, settingService);

        // 找到对应项目的请求队列
        Queue<GitlabRequest> toAddQueue = this.map.get(accessToken);
        // 若该项目的请求队列不存在，新增队列
        if (toAddQueue == null) {
            toAddQueue = new LinkedBlockingQueue<>();
            this.map.put(accessToken, queue);
        }

        GitlabRequest gitlabRequest = new GitlabRequest();
        gitlabRequest.setJson(json);
        gitlabRequest.setEventName(eventName);
        gitlabRequest.setSecret(secret);
        gitlabRequest.setReceivedTime(new Timestamp(System.currentTimeMillis()));
        // 添加进队列
        toAddQueue.offer(gitlabRequest);
    }

    /**
     * 是否继续处理
     * 当最后一次请求在当前时间 NOT_HANDLE_TIME 内时, 不进行处理
     *
     * @return true 不处理 false 处理
     */
    private Boolean IsNotHandle(Timestamp receivedTime) {
        return System.currentTimeMillis() - NOT_HANDLE_TIME < receivedTime.getTime();
    }
}
