package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GithubEvent;
import club.yunzhi.webhook.entities.Label;
import club.yunzhi.webhook.entities.User;
import club.yunzhi.webhook.request.GitLabPushRequest;
import club.yunzhi.webhook.request.GithubIssueCommentRequest;
import club.yunzhi.webhook.request.GithubIssueRequest;
import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service()
@Slf4j
public class IssueEventService implements EventService {

  private static final Logger logger = LoggerFactory.getLogger(PullRequestEventService.class);

  private final ConvertEntityService convertEntityService;
  private final GithubMessage githubMessage;
  private final SettingService settingService;

  public IssueEventService(ConvertEntityService convertEntityService,
                           GithubMessage githubMessage,
                           SettingService settingService) {
    this.convertEntityService = convertEntityService;
    this.githubMessage = githubMessage;
    this.settingService = settingService;
  }

  @Override
  public String getEventKey() {
    return "Issue Hook";
  }


  @Override
  public void handleEvent(String json, String secret) throws IOException {
    String accessToken = EventService.getAccessToken(secret, settingService);
    GitlabIssueRequest gitlabIssueRequest = covertJson(json);
    GithubIssueRequest githubIssueRequest = new GithubIssueRequest();
    boolean sendMessage = true;
    if (gitlabIssueRequest.getObject_attributes().action != null) {
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
          sendAssigneeOrLabelsMessage(gitlabIssueRequest, accessToken);
          sendMessage = false;
          break;
        default:
          sendMessage = false;
          logger.info("action:" + gitlabIssueRequest.getObject_attributes().getAction() + "暂不支持");
          break;
      }
    } else {
      logger.info("action为null, 暂不支持该事件");
      sendMessage = false;
    }

    if (sendMessage) {
      githubIssueRequest.setIssue(convertEntityService.getIssueFromGitlabToGithub(gitlabIssueRequest.getObject_attributes()));
      githubIssueRequest.setRepository(convertEntityService.getRepositoryFromGitlabToGithub(gitlabIssueRequest.getRepository()));
      githubIssueRequest.setSender(convertEntityService.getSender(gitlabIssueRequest.getUser().getUsername(), secret));
      githubIssueRequest.setChanges(convertEntityService.getChangesFromGitlabToGithub(gitlabIssueRequest.getChanges()));
      githubMessage.sendRequest(githubIssueRequest, GithubEvent.issues, accessToken);
    }
  }

  private void sendAssigneeOrLabelsMessage(GitlabIssueRequest gitlabIssueRequest, String access_token) {
    GithubIssueCommentRequest githubIssueCommentRequest = new GithubIssueCommentRequest();
    githubIssueCommentRequest.setIssue(convertEntityService.getIssueFromGitlabToGithub(gitlabIssueRequest.getObject_attributes()));
    githubIssueCommentRequest.setSender(convertEntityService.getSender(gitlabIssueRequest.getUser().getUsername(), access_token));
    githubIssueCommentRequest.setRepository(convertEntityService.getRepositoryFromGitlabToGithub(gitlabIssueRequest.getRepository()));
    GithubIssueCommentRequest.Comment comment = new GithubIssueCommentRequest.Comment();

    String body = "";
    if (gitlabIssueRequest.getChanges().assignees != null) {
      if (gitlabIssueRequest.getChanges().getAssignees().getCurrent().length != 0 || gitlabIssueRequest.getChanges().getAssignees().getPrevious().length != 0) {
        User[] previousUser = gitlabIssueRequest.getChanges().getAssignees().getPrevious();
        User[] currentUser = gitlabIssueRequest.getChanges().getAssignees().getCurrent();

        if (previousUser.length == 0) {
          body = body + "分配给" + currentUser[0].getUsername();
        } else if (currentUser.length == 0) {
          body = body + "取消分配给" + previousUser[0].getUsername();
        } else {
          body = "由分配给" + previousUser[0].getUsername() + "改为" + currentUser[0].getUsername();
        }

      }
    }
    if (gitlabIssueRequest.getChanges().labels != null) {
      if (!(gitlabIssueRequest.getChanges().getLabels().getPrevious().isEmpty() && gitlabIssueRequest.getChanges().getLabels().getCurrent().isEmpty())) {
        List<Label> previousLabels = gitlabIssueRequest.getChanges().getLabels().getPrevious();
        List<Label> currentLabels = gitlabIssueRequest.getChanges().getLabels().getCurrent();

        if (!previousLabels.isEmpty()) {
          StringBuilder titles = new StringBuilder();
          previousLabels.forEach(label -> {
            if (previousLabels.indexOf(label) != 0) {
              titles.append("、");
            }
            titles.append(label.getTitle());
          });
          body = body + "Labels由" + titles;
        } else {
          body = body + "Labels";
        }

        StringBuilder titles = new StringBuilder();
        if (currentLabels.isEmpty()) {
          body = body + "更改为空";
        } else {
          titles.append("更改为");
          StringBuilder finalTitles = titles;
          currentLabels.forEach(label -> {
            if (currentLabels.indexOf(label) != 0) {
              finalTitles.append("、");
            }
            finalTitles.append(label.getTitle());
          });
          body = body + finalTitles;
        }
      }
    }

    comment.setBody(body);
    githubIssueCommentRequest.setComment(comment);
    if (!Objects.equals(body, "")) {
      githubMessage.sendRequest(githubIssueCommentRequest, GithubEvent.issue_comment, access_token);
    } else {
      System.out.println("body为空，此处更新暂不支持推送");
    }
  }

  /**
   * 反序列化——把字节恢复为Java对象
   */
  private GitlabIssueRequest covertJson(String json) throws IOException {
    return JsonUtil.deserializeFromJson(json, GitlabIssueRequest.class);
  }
}
