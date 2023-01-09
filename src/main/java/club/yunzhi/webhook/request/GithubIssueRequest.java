package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GithubIssue;
import club.yunzhi.webhook.entities.GithubRepository;
import club.yunzhi.webhook.entities.GitlabIssue;
import club.yunzhi.webhook.entities.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubIssueRequest {
  String action;
  GithubIssue issue;
  GithubRepository repository;
  Sender sender;
}
