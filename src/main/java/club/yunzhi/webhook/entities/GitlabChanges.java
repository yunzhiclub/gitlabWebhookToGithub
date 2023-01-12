package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GitlabChanges {

  Description description;
  Title title;
  public Assignees assignees;
  public Labels labels;

  @Data
  public static class Assignees {
    User[] previous;
    User[] current;
  }

  @Data
  public static class Description {
    String previous;
  }

  @Data
  public static class Title {
    String previous;
  }
}
