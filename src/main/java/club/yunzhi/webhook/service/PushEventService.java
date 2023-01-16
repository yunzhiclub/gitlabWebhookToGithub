package club.yunzhi.webhook.service;
import club.yunzhi.webhook.entities.GithubEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("pushEventService")
@Slf4j
public class PushEventService implements EventService {
  private final GithubMessage githubMessage;
  private final SettingService settingService;

  public PushEventService(GithubMessage githubMessage,
                          SettingService settingService) {
    this.githubMessage = githubMessage;
    this.settingService = settingService;
  }


  @Override
  public String getEventKey() {
    return "Push Hook";
  }

  @Override
  public void handleEvent(String json, String secret) throws IOException {
    String accessToken = EventService.getAccessToken(secret, settingService);

    githubMessage.sendJsonMessage(json, GithubEvent.push, accessToken);
  }

}
