package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GithubEvent;
import club.yunzhi.webhook.entities.GithubIssue;
import club.yunzhi.webhook.request.GitLabCommentRequest;
import club.yunzhi.webhook.request.GithubIssueCommentRequest;
import club.yunzhi.webhook.request.GithubIssueRequest;
import club.yunzhi.webhook.request.GitlabIssueRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service()
@Slf4j
public class CommentEventService implements EventService {
    private final ConvertEntityService convertEntityService;
    private final GithubMessage githubMessage;

    public CommentEventService(ConvertEntityService convertEntityService,
                               GithubMessage githubMessage) {
        this.convertEntityService = convertEntityService;
        this.githubMessage = githubMessage;
    }

    @Override
    public String getEventKey() {
        return "Note Hook";
    }

    @Override
    public void handleEvent(String json, String access_token) throws IOException {
        GitLabCommentRequest gitLabCommentRequest = EventService.covertJson(json, GitLabCommentRequest.class);
        GithubIssueCommentRequest githubIssueCommentRequest = new GithubIssueCommentRequest();

        // 当为issue_comment事件时有issue属性
        // 当为merge_request_comment有merge_request 属性，类型兼容GitlabIssue
        if (gitLabCommentRequest.getIssue() != null) {
            githubIssueCommentRequest.setIssue(convertEntityService.getIssueFromGitlabToGithub(gitLabCommentRequest.getIssue()));
        } else if(gitLabCommentRequest.getMerge_request() != null) {
            githubIssueCommentRequest.setIssue(convertEntityService.getIssueFromGitlabToGithub(gitLabCommentRequest.getMerge_request()));
            // 直接将issue的url作为pullRequest的url
            GithubIssue.PullRequest pullRequest = new GithubIssue.PullRequest();
            pullRequest.setHtml_url(gitLabCommentRequest.getMerge_request().getUrl());
            githubIssueCommentRequest.getIssue().setPull_request(pullRequest);
        }

        githubIssueCommentRequest.setComment(convertEntityService.getCommentFromGitlabToGithub(gitLabCommentRequest.getObject_attributes()));
        githubIssueCommentRequest.setRepository(convertEntityService.getRepositoryFromGitlabToGithub(gitLabCommentRequest.getRepository()));
        githubIssueCommentRequest.setSender(convertEntityService.getSender(gitLabCommentRequest.getUser().getUsername()));
        githubMessage.sendRequest(githubIssueCommentRequest, GithubEvent.issue_comment, access_token);
    }
}
