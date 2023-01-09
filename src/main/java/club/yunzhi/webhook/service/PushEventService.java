package club.yunzhi.webhook.service;
import club.yunzhi.webhook.entities.GithubEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("pushEventService")
@Slf4j
public class PushEventService implements EventService {
  private final GithubMessage githubMessage;

  public PushEventService(
      GithubMessage githubMessage) {
    this.githubMessage = githubMessage;
  }


  @Override
  public String getEventKey() {
    return "Push Hook";
  }

  @Override
  public void handleEvent(String json) throws IOException {
    githubMessage.sendJsonMessage(json, GithubEvent.push);
  }

}
