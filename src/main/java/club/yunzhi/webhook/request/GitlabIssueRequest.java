package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitlabIssueRequest extends ParentRequest{
  private GitlabIssue object_attributes;
  private User user;
  private GitlabChanges changes;
}
