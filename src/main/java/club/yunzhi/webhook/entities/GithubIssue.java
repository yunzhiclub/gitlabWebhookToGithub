package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubIssue {
  String title;
  String body;
  Integer number;
  String state;
  String html_url;
  GithubChanges changes;

  // github merge_request_comment 事件，有该字段
  // 不添加的话，github机器人会认为是 comment on issue事件
  PullRequest pull_request;

  @Data
  public static class PullRequest {
    String html_url;
  }
}
