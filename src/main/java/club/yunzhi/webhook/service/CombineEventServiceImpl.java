package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GitlabEvent;
import club.yunzhi.webhook.entities.GitlabRequest;
import club.yunzhi.webhook.request.GitLabCommentRequest;
import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.request.GitlabMergeRequestRequest;
import club.yunzhi.webhook.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CombineEventServiceImpl implements CombineEventService {

    private static final Logger logger = LoggerFactory.getLogger(CombineEventServiceImpl.class);


    @Override
    public String handleEvent(Iterator<GitlabRequest> queueIterator, GitlabRequest gitlabRequest) throws IOException {
        String handleJson = this.commentAndIssueClose(queueIterator, gitlabRequest);
        // 有新的事件 在这里加 handleJson = xxx();
        return handleJson;
    }


    @Override
    public String commentAndIssueClose(Iterator<GitlabRequest> queueIterator, GitlabRequest gitlabRequest) throws IOException {
        String eventName = gitlabRequest.getEventName();
        String json = gitlabRequest.getJson();

        if (Objects.equals(eventName, GitlabEvent.noteHook)) {
            if (!queueIterator.hasNext()) {
                return json;
            }
            GitlabRequest nextRequest = queueIterator.next();
            // 若下一个事件为不为issueClose事件，或不为同一issue 返回原json
            if (!Objects.equals(nextRequest.getEventName(), GitlabEvent.issueHook) || !this.judgeIsIssueClose(nextRequest.getJson())
                    || !this.judgeIsSameIssue(nextRequest.getJson(), json)) {
                return json;
            }
            // 出栈，进行事件合并，不发送issueClose事件
            queueIterator.remove();
            logger.info("进行事件合并");
            return this.setIssueTittleClose(json);
        }
        // 若不进行事件合并 返回原json
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

    /**
     * 判断两个事件是否为同一issue事件
     *
     * @param GitlabIssueRequestJson issue事件json
     * @param GitlabIssueCommentJson comment事件json
     * @return true 是 false 不是
     */
    private Boolean judgeIsSameIssue(String GitlabIssueRequestJson, String GitlabIssueCommentJson) throws IOException {
        GitlabIssueRequest gitlabIssueRequest = EventService.covertJson(GitlabIssueRequestJson, GitlabIssueRequest.class);
        GitLabCommentRequest gitLabCommentRequest = EventService.covertJson(GitlabIssueCommentJson, GitLabCommentRequest.class);

        if (gitLabCommentRequest.getIssue() == null) {
            return false;
        }
        return Objects.equals(gitlabIssueRequest.getObject_attributes().getIid(), gitLabCommentRequest.getIssue().getIid());
    }
}
