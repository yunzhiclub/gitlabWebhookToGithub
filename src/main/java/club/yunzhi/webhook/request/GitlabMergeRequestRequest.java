package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GitlabChanges;
import club.yunzhi.webhook.entities.GitlabIssue;
import club.yunzhi.webhook.entities.GitlabMergeRequest;
import club.yunzhi.webhook.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitlabMergeRequestRequest extends ParentRequest {
  private GitlabMergeRequest object_attributes;
  private User user;
}
