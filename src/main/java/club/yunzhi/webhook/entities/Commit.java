package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commit {
  private String message;
  private ZonedDateTime timestamp;
  private String url;
  private User author;
  private User committer;

}
