package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.*;
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
  GithubChanges changes;
}
