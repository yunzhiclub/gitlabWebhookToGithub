package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.*;
import club.yunzhi.webhook.repository.SettingRepository;
import club.yunzhi.webhook.request.GithubIssueCommentRequest;
import club.yunzhi.webhook.request.ParentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于转换实体
 */
@Service()
@Slf4j
public class ConvertEntityService {

  private final
  SettingRepository settingRepository;

  public ConvertEntityService(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  /**
   * 所改变的关键字(以github为基准)
   */
  enum ChangeKey {
    title,
    body
  }


  GithubIssue getIssueFromGitlabToGithub(GitlabIssue gitlabIssue) {
    GithubIssue githubIssue = new GithubIssue();
    githubIssue.setTitle(gitlabIssue.getTitle());
    githubIssue.setBody(gitlabIssue.getDescription());
    githubIssue.setHtml_url(gitlabIssue.getUrl());
    githubIssue.setNumber(gitlabIssue.getIid());
    githubIssue.setState(gitlabIssue.getState());
    return githubIssue;
  }

  GithubIssueCommentRequest.Comment getCommentFromGitlabToGithub(GitlabComment gitlabComment) {
    GithubIssueCommentRequest.Comment comment = new GithubIssueCommentRequest.Comment();
    comment.setBody(gitlabComment.getNote());
    return comment;
  }

  GithubRepository getRepositoryFromGitlabToGithub(GitlabRepository gitlabRepository) {
    GithubRepository githubRepository = new GithubRepository();
    githubRepository.setDescription(gitlabRepository.getDescription());
    githubRepository.setName(gitlabRepository.getName());
    githubRepository.setHtml_url(gitlabRepository.getHomepage());

    return githubRepository;
  }

  Sender getSender(String userName, String secret) {
    Sender sender = new Sender();
    sender.setLogin(userName);
    List<Setting> settings = this.settingRepository.getSettingBySecret(secret);
    if (settings.isEmpty()){
      System.out.println("此Secret无对应Setting");
    } else if(settings.size() > 1) {
      System.out.println("此Secret找到了多个Setting");
    } else {
      sender.setHtml_url(settings.get(0).getGitlabUrl() + "/" + userName);
    }
    return sender;
  }


  private Commit getCommit(ParentRequest.Commit gitLabCommit) {
    Commit githubCommit = new Commit();
    githubCommit.setAuthor(gitLabCommit.getAuthor());
    githubCommit.setCommitter(gitLabCommit.getAuthor());
    githubCommit.setMessage(gitLabCommit.getMessage());
    githubCommit.setTimestamp(gitLabCommit.getZonedDateTime());
    githubCommit.setUrl(gitLabCommit.getUrl());
    return githubCommit;
  }

  List<Commit> getCommits(List<ParentRequest.Commit> gitlabCommits) {
    List<Commit> githubCommits = new ArrayList<>();
    gitlabCommits.forEach(
        gitlabCommit -> {
          githubCommits.add(getCommit(gitlabCommit));
        }
    );
    return githubCommits;
  }

  GithubChanges getChangesFromGitlabToGithub(GitlabChanges gitlabChanges) {
    GithubChanges githubChanges = new GithubChanges();
    if (gitlabChanges != null) {
      if (gitlabChanges.getTitle() != null) {
        this.addChanges(ChangeKey.title, gitlabChanges, githubChanges);
      }
      if (gitlabChanges.getDescription() != null) {
        this.addChanges(ChangeKey.body, gitlabChanges, githubChanges);
      }
    }
    return githubChanges;
  }

  private void addChanges(ChangeKey changeKey, GitlabChanges gitlabChanges, GithubChanges githubChanges) {
    if (changeKey.equals(ChangeKey.title)) {
      githubChanges.setTitle(new GithubChanges.Title(gitlabChanges.getTitle().getPrevious()));
    } else if (changeKey.equals(ChangeKey.body)) {
      githubChanges.setBody(new GithubChanges.Body(gitlabChanges.getDescription().getPrevious()));
    }
  }

  GitHubPullRequest getPullRequestFromMergeRequest(GitlabMergeRequest gitlabMergeRequest) {
    GitHubPullRequest gitHubPullRequest = new GitHubPullRequest();
    gitHubPullRequest.setBody(gitlabMergeRequest.getDescription());
    gitHubPullRequest.setTitle(gitlabMergeRequest.getTitle());
    gitHubPullRequest.setHtml_url(gitlabMergeRequest.getUrl());
    gitHubPullRequest.setNumber(gitlabMergeRequest.getIid());
    gitHubPullRequest.setState(gitlabMergeRequest.getState());
    return gitHubPullRequest;
  }

}
