package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubChanges {
  Title title;

  Body body;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Title{
    String from;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Body{
    String from;
  }
}

