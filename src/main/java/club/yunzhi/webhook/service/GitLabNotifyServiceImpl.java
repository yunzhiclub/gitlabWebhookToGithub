package club.yunzhi.webhook.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于统一处理各类型请求
 */
@Service
public class GitLabNotifyServiceImpl implements GitLabNotifyService {
  /**
   * 用于缓存X-Gitlab-Token
   */
  private String secret;

  private Map<String, EventService> map = new HashMap<>();
  ;

  public GitLabNotifyServiceImpl(PushEventService pushEventService,
                                 IssueEventService issueEventService) {
    this.addService(issueEventService);
    this.addService(pushEventService);
  }

  @Override
  public void handleEventData(String json, String eventName, String secret) throws IOException {
    //缓存secret
    this.secret = secret;
    EventService eventService = this.map.get(eventName);
    eventService.handleEvent(json);
  }

  @Override
  public String getDingSecret() {
    return secret;
  }

  /**
   * 添加映射
   */
  private void addService(EventService eventService) {
    this.map.putIfAbsent(eventService.getEventKey(), eventService);
  }
}
