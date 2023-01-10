package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GitlabRepository;
import club.yunzhi.webhook.entities.User;
import lombok.Data;

import java.security.Timestamp;
import java.sql.Time;
import java.time.ZonedDateTime;

/**
 * GitLab请求
 */
@Data
public abstract class ParentRequest {
  private String objectKind;
  private String projectId;
  private Project project;
  private GitlabRepository repository;

  @Data
  public static class Project{
    private Long id;
    private String name;
    private String description;
    private String webUrl;
    private String avatarUrl;
    private String gitSshUrl;
    private String gitHttpUrl;
    private String namespace;
    private String visibilityLevel;
    private String pathWithNamespace;
    private String defaultBranch;

  }

  @Data
  public static class Commit{
    private String id;
    private String message;
    private ZonedDateTime zonedDateTime;
    private User author;
    private String url;

  }
}
