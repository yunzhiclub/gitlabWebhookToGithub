package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GithubIssue;
import club.yunzhi.webhook.entities.GithubRepository;
import club.yunzhi.webhook.entities.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weiweiyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubIssueCommentRequest {

    GithubIssue issue;

    Sender sender;

    GithubRepository repository;

    // 评论内容
    Comment comment;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Comment {
        String body;
    }
}
