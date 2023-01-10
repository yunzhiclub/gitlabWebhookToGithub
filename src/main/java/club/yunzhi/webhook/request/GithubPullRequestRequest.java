package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GitHubPullRequest;
import club.yunzhi.webhook.entities.GithubIssue;
import club.yunzhi.webhook.entities.GithubRepository;
import club.yunzhi.webhook.entities.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubPullRequestRequest {
  String action;
  GitHubPullRequest pull_request;
  GithubRepository repository;
  Sender sender;
}
