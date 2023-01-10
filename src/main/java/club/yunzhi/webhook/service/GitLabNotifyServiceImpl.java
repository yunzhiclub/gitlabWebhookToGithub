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

  private Map<String, EventService> map = new HashMap<>();


  public GitLabNotifyServiceImpl(PushEventService pushEventService,
                                 IssueEventService issueEventService,
                                 CommentEventService commentEventService) {
    this.addService(issueEventService);
    this.addService(pushEventService);
    this.addService(commentEventService);
  }

  @Override
  public void handleEventData(String json, String eventName,String access_token) throws IOException {

    EventService eventService = this.map.get(eventName);

    if(eventService != null) {
      eventService.handleEvent(json, access_token);
    } else {
       System.out.println("未添加对应 "+ eventName + " service");
    }
  }


  /**
   * 添加映射
   */
  private void addService(EventService eventService) {
    this.map.putIfAbsent(eventService.getEventKey(), eventService);
  }
}
