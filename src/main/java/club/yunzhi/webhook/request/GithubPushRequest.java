package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.Commit;
import club.yunzhi.webhook.entities.GithubRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubPushRequest {
  private List<Commit> commits;
  private Commit head_commit;
  private String url;
  private GithubRepository repository;
  private String body = "";
}
