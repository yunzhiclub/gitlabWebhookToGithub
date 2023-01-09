package club.yunzhi.webhook.entities;

import lombok.Data;

@Data
public class GitlabRepository {
  private String name;
  private String url;
  private String description;
  private String homepage;
}
