package club.yunzhi.webhook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于统一处理各类型请求
 */
@Service
public class GitLabNotifyServiceImpl implements GitLabNotifyService {

  private Map<String, EventService> map = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(GitLabNotifyServiceImpl.class);

  private final CombineEventService combineEventService;


  public GitLabNotifyServiceImpl(PushEventService pushEventService,
                                 IssueEventService issueEventService,
                                 CommentEventService commentEventService,
                                 PullRequestEventService pullRequestEventService,
                                 CombineEventService combineEventService) {
    this.addService(issueEventService);
    this.addService(pushEventService);
    this.addService(commentEventService);
    this.addService(pullRequestEventService);
    this.combineEventService = combineEventService;
  }

  @Override
  public void handleEventData(String json, String eventName,String secret) throws IOException {

    EventService eventService = this.map.get(eventName);

    if(eventService == null) {
      logger.info("未添加对应 " + eventName + " service");
      return;
    }
      eventService.handleEvent(json, secret);

  }


  /**
   * 添加映射
   */
  private void addService(EventService eventService) {
    this.map.putIfAbsent(eventService.getEventKey(), eventService);
  }
}
