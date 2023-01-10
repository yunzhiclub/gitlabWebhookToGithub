package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitlabMergeRequest {
  String title;
  String description;
  Integer iid;
  String state;
  String url;
  String action;
}
