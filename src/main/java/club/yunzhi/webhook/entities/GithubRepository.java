package club.yunzhi.webhook.entities;

import club.yunzhi.webhook.request.ParentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepository {
  private String name;
  private String html_url;
  private String description;
}

