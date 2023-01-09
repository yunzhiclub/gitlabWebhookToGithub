package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.*;
import club.yunzhi.webhook.request.ParentRequest;
import lombok.extern.slf4j.Slf4j;
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
  @Value("${gitlabUrl}")
  private String gitlabUrl;

  GithubIssue getIssueFromGitlabToGithub(GitlabIssue gitlabIssue) {
    GithubIssue githubIssue = new GithubIssue();
    githubIssue.setTitle(gitlabIssue.getTitle());
    githubIssue.setBody(gitlabIssue.getDescription());
    githubIssue.setHtml_url(gitlabIssue.getUrl());
    githubIssue.setNumber(gitlabIssue.getIid());
    githubIssue.setState(gitlabIssue.getState());
    return githubIssue;
  }

  GithubRepository getRepositoryFromGitlabToGithub(GitlabRepository gitlabRepository) {
    GithubRepository githubRepository = new GithubRepository();
    githubRepository.setDescription(gitlabRepository.getDescription());
    githubRepository.setName(gitlabRepository.getName());
    githubRepository.setHtml_url(gitlabRepository.getHomepage());

    return githubRepository;
  }

  Sender getSender(String userName) {
    Sender sender = new Sender();
    sender.setLogin(userName);
    sender.setHtml_url(gitlabUrl + "/" + userName);
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

}
