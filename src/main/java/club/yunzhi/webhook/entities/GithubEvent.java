package club.yunzhi.webhook.entities;

public enum GithubEvent {
  push,
  issues,
  // 评论事件，gitlab的 issue_comment, merge_request_comment都对应此事件
  issue_comment
}
