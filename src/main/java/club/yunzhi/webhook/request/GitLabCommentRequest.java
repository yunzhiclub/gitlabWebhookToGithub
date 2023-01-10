package club.yunzhi.webhook.request;

import club.yunzhi.webhook.entities.GitlabComment;
import club.yunzhi.webhook.entities.GitlabIssue;
import club.yunzhi.webhook.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weiweiyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitLabCommentRequest extends ParentRequest {

    // 当为issue_comment事件时有issue属性
    // 当为merge_request_comment merge_request 属性，类型兼容GitlabIssue
    GitlabIssue issue;
    GitlabIssue merge_request;

    private GitlabComment object_attributes;

    private User user;

}
