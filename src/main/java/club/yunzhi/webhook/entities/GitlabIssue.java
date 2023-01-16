package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GitlabIssue {
  String title;
  String description;
  Integer iid;
  String state;
  String url;
  public String action;
}
