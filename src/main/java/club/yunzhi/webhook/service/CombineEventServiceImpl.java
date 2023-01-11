package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GitlabEvent;
import club.yunzhi.webhook.request.GitLabCommentRequest;
import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.request.GitlabMergeRequestRequest;
import club.yunzhi.webhook.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class CombineEventServiceImpl implements CombineEventService {

    private static final Logger logger = LoggerFactory.getLogger(CombineEventServiceImpl.class);

    /**
     * key： access_token, github钉钉机器人的token
     * value GitlabEvent,  gitlab事件
     */
    private static final HashMap<String, String> hashMap = new HashMap<>();

    @Override
    public String handleEvent(String json, String eventName, String access_token) throws IOException {
        String handleJson = this.commentAndIssueClose(json, eventName, access_token);
        // 有新的事件 在这里加 handleJson = xxx();
        return handleJson;
    }


    @Override
    public String commentAndIssueClose(String json, String eventName, String access_token) throws IOException {

        if (Objects.equals(eventName, GitlabEvent.issueHook)) {
            // 若issue事件前面有comment事件，且为issue为close事件 则不发送该事件
            if (Objects.equals(hashMap.get(access_token), GitlabEvent.noteHook) && this.judgeIsIssueClose(json)) {
                hashMap.put(access_token, GitlabEvent.issueHook);
                // 返回null 表示不发送
                return null;
            }
        } else if (Objects.equals(eventName, GitlabEvent.noteHook)) {
            try {
                hashMap.put(access_token, GitlabEvent.noteHook);
                // 因为评论事件先于issue事件发送， 所以评论事件等待两秒
                TimeUnit.SECONDS.sleep(2);
                // 若2s后存在issue事件 则事件合并
                if (Objects.equals(hashMap.get(access_token), GitlabEvent.issueHook)) {
                    hashMap.put(access_token, "");
                    return this.setIssueTittleClose(json);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // 清空value
        hashMap.put(access_token, "");
        return json;
    }

    /**
     * 设置issueTittle，前面加上已关闭
     *
     * @return json
     */
    private String setIssueTittleClose(String json) throws IOException {
        GitLabCommentRequest gitLabCommentRequest = EventService.covertJson(json, GitLabCommentRequest.class);
        String tittle = gitLabCommentRequest.getIssue().getTitle();
        // 设置标题 并返回json数据
        gitLabCommentRequest.getIssue().setTitle("（已关闭）" + tittle);
        return JsonUtil.serializeToJson(gitLabCommentRequest, true);
    }

    /**
     * 判断是否为issue close事件
     *
     * @return 是否为issue close事件
     */
    private Boolean judgeIsIssueClose(String json) throws IOException {
        GitlabIssueRequest gitlabIssueRequest = EventService.covertJson(json, GitlabIssueRequest.class);
        return Objects.equals(gitlabIssueRequest.getObject_attributes().getAction(), "close");
    }
}
