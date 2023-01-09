package club.yunzhi.webhook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabControllerTest {

    @Autowired
    GitLabController gitLabController;


    @Test
    public void pushHook() throws IOException {
        // event换成自己要测试的数据
        gitLabController.pushHook(json, "Note Hook");
    }

    // 测试数据 payload 复制于下面
    String json = "{\n" +
            "  \"object_kind\": \"note\",\n" +
            "  \"event_type\": \"note\",\n" +
            "  \"user\": {\n" +
            "    \"id\": 6,\n" +
            "    \"name\": \"wei weiyi\",\n" +
            "    \"username\": \"weiweiyi189\",\n" +
            "    \"avatar_url\": \"http://gitlab.mengyunzhi.com:2448/uploads/-/system/user/avatar/6/avatar.png\",\n" +
            "    \"email\": \"[REDACTED]\"\n" +
            "  },\n" +
            "  \"project_id\": 16,\n" +
            "  \"project\": {\n" +
            "    \"id\": 16,\n" +
            "    \"name\": \"测试项目\",\n" +
            "    \"description\": \"\",\n" +
            "    \"web_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "    \"avatar_url\": null,\n" +
            "    \"git_ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "    \"git_http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\",\n" +
            "    \"namespace\": \"wei weiyi\",\n" +
            "    \"visibility_level\": 20,\n" +
            "    \"path_with_namespace\": \"weiweiyi189/myTestProject\",\n" +
            "    \"default_branch\": \"main\",\n" +
            "    \"ci_config_path\": null,\n" +
            "    \"homepage\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "    \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "    \"ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "    \"http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\"\n" +
            "  },\n" +
            "  \"object_attributes\": {\n" +
            "    \"attachment\": null,\n" +
            "    \"author_id\": 6,\n" +
            "    \"change_position\": null,\n" +
            "    \"commit_id\": null,\n" +
            "    \"created_at\": \"2023-01-08 11:20:20 UTC\",\n" +
            "    \"discussion_id\": \"f5d1ee40a1d5db7599f5e950d6cba47fb4122927\",\n" +
            "    \"id\": 3115,\n" +
            "    \"line_code\": null,\n" +
            "    \"note\": \"这是pull request comment\",\n" +
            "    \"noteable_id\": 404,\n" +
            "    \"noteable_type\": \"MergeRequest\",\n" +
            "    \"original_position\": null,\n" +
            "    \"position\": null,\n" +
            "    \"project_id\": 16,\n" +
            "    \"resolved_at\": null,\n" +
            "    \"resolved_by_id\": null,\n" +
            "    \"resolved_by_push\": null,\n" +
            "    \"st_diff\": null,\n" +
            "    \"system\": false,\n" +
            "    \"type\": null,\n" +
            "    \"updated_at\": \"2023-01-08 11:20:20 UTC\",\n" +
            "    \"updated_by_id\": null,\n" +
            "    \"description\": \"这是pull request comment\",\n" +
            "    \"url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject/-/merge_requests/1#note_3115\"\n" +
            "  },\n" +
            "  \"repository\": {\n" +
            "    \"name\": \"测试项目\",\n" +
            "    \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "    \"description\": \"\",\n" +
            "    \"homepage\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\"\n" +
            "  },\n" +
            "  \"merge_request\": {\n" +
            "    \"assignee_id\": null,\n" +
            "    \"author_id\": 6,\n" +
            "    \"created_at\": \"2023-01-08 08:58:59 UTC\",\n" +
            "    \"description\": \"这是内容\uD83D\uDE0A\uD83D\uDE0A\",\n" +
            "    \"head_pipeline_id\": null,\n" +
            "    \"id\": 404,\n" +
            "    \"iid\": 1,\n" +
            "    \"last_edited_at\": null,\n" +
            "    \"last_edited_by_id\": null,\n" +
            "    \"merge_commit_sha\": null,\n" +
            "    \"merge_error\": null,\n" +
            "    \"merge_params\": {\n" +
            "      \"force_remove_source_branch\": \"1\"\n" +
            "    },\n" +
            "    \"merge_status\": \"can_be_merged\",\n" +
            "    \"merge_user_id\": null,\n" +
            "    \"merge_when_pipeline_succeeds\": false,\n" +
            "    \"milestone_id\": null,\n" +
            "    \"source_branch\": \"testBranch\",\n" +
            "    \"source_project_id\": 16,\n" +
            "    \"state_id\": 1,\n" +
            "    \"target_branch\": \"main\",\n" +
            "    \"target_project_id\": 16,\n" +
            "    \"time_estimate\": 0,\n" +
            "    \"title\": \"这是merge request\",\n" +
            "    \"updated_at\": \"2023-01-08 11:20:20 UTC\",\n" +
            "    \"updated_by_id\": null,\n" +
            "    \"url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject/-/merge_requests/1\",\n" +
            "    \"source\": {\n" +
            "      \"id\": 16,\n" +
            "      \"name\": \"测试项目\",\n" +
            "      \"description\": \"\",\n" +
            "      \"web_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "      \"avatar_url\": null,\n" +
            "      \"git_ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"git_http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\",\n" +
            "      \"namespace\": \"wei weiyi\",\n" +
            "      \"visibility_level\": 20,\n" +
            "      \"path_with_namespace\": \"weiweiyi189/myTestProject\",\n" +
            "      \"default_branch\": \"main\",\n" +
            "      \"ci_config_path\": null,\n" +
            "      \"homepage\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "      \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\"\n" +
            "    },\n" +
            "    \"target\": {\n" +
            "      \"id\": 16,\n" +
            "      \"name\": \"测试项目\",\n" +
            "      \"description\": \"\",\n" +
            "      \"web_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "      \"avatar_url\": null,\n" +
            "      \"git_ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"git_http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\",\n" +
            "      \"namespace\": \"wei weiyi\",\n" +
            "      \"visibility_level\": 20,\n" +
            "      \"path_with_namespace\": \"weiweiyi189/myTestProject\",\n" +
            "      \"default_branch\": \"main\",\n" +
            "      \"ci_config_path\": null,\n" +
            "      \"homepage\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject\",\n" +
            "      \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/weiweiyi189/myTestProject.git\",\n" +
            "      \"http_url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject.git\"\n" +
            "    },\n" +
            "    \"last_commit\": {\n" +
            "      \"id\": \"b7de1c5e11c314645e38455031ec39aa84032f91\",\n" +
            "      \"message\": \"Add new file\",\n" +
            "      \"title\": \"Add new file\",\n" +
            "      \"timestamp\": \"2023-01-08T09:57:06+00:00\",\n" +
            "      \"url\": \"http://gitlab.mengyunzhi.com:2448/weiweiyi189/myTestProject/-/commit/b7de1c5e11c314645e38455031ec39aa84032f91\",\n" +
            "      \"author\": {\n" +
            "        \"name\": \"wei weiyi\",\n" +
            "        \"email\": \"1589654294@qq.com\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"work_in_progress\": false,\n" +
            "    \"total_time_spent\": 0,\n" +
            "    \"time_change\": 0,\n" +
            "    \"human_total_time_spent\": null,\n" +
            "    \"human_time_change\": null,\n" +
            "    \"human_time_estimate\": null,\n" +
            "    \"assignee_ids\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"state\": \"opened\",\n" +
            "    \"blocking_discussions_resolved\": true\n" +
            "  }\n" +
            "}";
}