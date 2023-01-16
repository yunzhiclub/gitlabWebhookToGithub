package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GitHubPullRequest;
import club.yunzhi.webhook.entities.GithubEvent;
import club.yunzhi.webhook.entities.GitlabMergeRequest;
import club.yunzhi.webhook.request.GitLabCommentRequest;
import club.yunzhi.webhook.request.GithubIssueRequest;
import club.yunzhi.webhook.request.GithubPullRequestRequest;
import club.yunzhi.webhook.request.GitlabMergeRequestRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service()
@Slf4j
public class PullRequestEventService implements EventService {

  private static final Logger logger = LoggerFactory.getLogger(PullRequestEventService.class);

  private final ConvertEntityService convertEntityService;
  private final GithubMessage githubMessage;
  private final SettingService settingService;

  public PullRequestEventService(ConvertEntityService convertEntityService,
                                 GithubMessage githubMessage,
                                 SettingService settingService) {
    this.convertEntityService = convertEntityService;
    this.githubMessage = githubMessage;
    this.settingService = settingService;
  }

  @Override
  public String getEventKey() {
    return "Merge Request Hook";
  }

  @Override
  public void handleEvent(String json, String secret) throws IOException {
    String accessToken = EventService.getAccessToken(secret, settingService);
    boolean send = true;
    GitlabMergeRequestRequest gitlabMergeRequestRequest = EventService.covertJson(json, GitlabMergeRequestRequest.class);
    GithubPullRequestRequest githubPullRequestRequest = new GithubPullRequestRequest();
    // action为null 不发送
    if (gitlabMergeRequestRequest.getObject_attributes().getAction() == null) {
      logger.info("action为null,不发送");
      return;
    }
    switch (gitlabMergeRequestRequest.getObject_attributes().getAction()) {
      case "close":
        githubPullRequestRequest.setAction("closed");
        break;
      case "open":
        githubPullRequestRequest.setAction("opened");
        break;
      case "reopen":
        githubPullRequestRequest.setAction("reopened");
        break;
      default:
        send = false;
        logger.info("action:" + gitlabMergeRequestRequest.getObject_attributes().getAction() + "暂不支持");
        break;
    }
    githubPullRequestRequest.setPull_request(convertEntityService.getPullRequestFromMergeRequest(gitlabMergeRequestRequest.getObject_attributes()));
    githubPullRequestRequest.setRepository(convertEntityService.getRepositoryFromGitlabToGithub(gitlabMergeRequestRequest.getRepository()));
    githubPullRequestRequest.setSender(convertEntityService.getSender(gitlabMergeRequestRequest.getUser().getUsername(), secret));
    // gitlab中关闭PR中Action分为merge和close，但是github中只有close，如果只想推送后续的Push信息不推送Merge信息则只需要不接收merge Action即可
    if (send) {
      githubMessage.sendRequest(githubPullRequestRequest, GithubEvent.pull_request,accessToken);
    }
  }
}
