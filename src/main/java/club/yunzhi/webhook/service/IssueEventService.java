package club.yunzhi.webhook.service;
import club.yunzhi.webhook.entities.GithubEvent;
import club.yunzhi.webhook.request.GitLabPushRequest;
import club.yunzhi.webhook.request.GithubIssueRequest;
import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service()
@Slf4j
public class IssueEventService implements EventService {

  private static final Logger logger = LoggerFactory.getLogger(PullRequestEventService.class);

  private final ConvertEntityService convertEntityService;
  private final GithubMessage githubMessage;
  public IssueEventService(ConvertEntityService convertEntityService,
                           GithubMessage githubMessage) {
    this.convertEntityService = convertEntityService;
    this.githubMessage = githubMessage;
  }

  @Override
  public String getEventKey() {
    return "Issue Hook";
  }


  @Override
  public void handleEvent(String json, String access_token) throws IOException {
    GitlabIssueRequest gitlabIssueRequest = covertJson(json);
    GithubIssueRequest githubIssueRequest = new GithubIssueRequest();
    switch (gitlabIssueRequest.getObject_attributes().getAction()) {
      case "close":
        githubIssueRequest.setAction("closed");
        break;
      case "open":
        githubIssueRequest.setAction("opened");
        break;
      case "reopen":
        githubIssueRequest.setAction("reopened");
        break;
      case "update":
        logger.info("检测到Issue.update事件，Github机器人不进行推送");
        break;
      default:
        logger.info("action:" + gitlabIssueRequest.getObject_attributes().getAction() + "暂不支持");
        break;
    }
    githubIssueRequest.setIssue(convertEntityService.getIssueFromGitlabToGithub(gitlabIssueRequest.getObject_attributes()));
    githubIssueRequest.setRepository(convertEntityService.getRepositoryFromGitlabToGithub(gitlabIssueRequest.getRepository()));
    githubIssueRequest.setSender(convertEntityService.getSender(gitlabIssueRequest.getUser().getUsername()));
    githubIssueRequest.setChanges(convertEntityService.getChangesFromGitlabToGithub(gitlabIssueRequest.getChanges()));
    githubMessage.sendRequest(githubIssueRequest, GithubEvent.issues, access_token);
  }

  /**
   * 反序列化——把字节恢复为Java对象
   */
  private GitlabIssueRequest covertJson(String json) throws IOException {
    return JsonUtil.deserializeFromJson(json, GitlabIssueRequest.class);
  }
}
