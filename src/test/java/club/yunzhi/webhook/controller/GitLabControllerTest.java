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
        gitLabController.pushHook(json, "Issue Hook", "3213");
    }

    // 测试数据 payload 复制于下面
    String json = "{\n" +
        "  \"object_kind\": \"issue\",\n" +
        "  \"event_type\": \"issue\",\n" +
        "  \"user\": {\n" +
        "    \"id\": 4,\n" +
        "    \"name\": \"mingao li\",\n" +
        "    \"username\": \"limingao\",\n" +
        "    \"avatar_url\": \"http://gitlab.mengyunzhi.com:2448/uploads/-/system/user/avatar/4/avatar.png\",\n" +
        "    \"email\": \"[REDACTED]\"\n" +
        "  },\n" +
        "  \"project\": {\n" +
        "    \"id\": 8,\n" +
        "    \"name\": \"测试项目\",\n" +
        "    \"description\": \"\",\n" +
        "    \"web_url\": \"http://gitlab.mengyunzhi.com:2448/limingao/test\",\n" +
        "    \"avatar_url\": null,\n" +
        "    \"git_ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/limingao/test.git\",\n" +
        "    \"git_http_url\": \"http://gitlab.mengyunzhi.com:2448/limingao/test.git\",\n" +
        "    \"namespace\": \"mingao li\",\n" +
        "    \"visibility_level\": 0,\n" +
        "    \"path_with_namespace\": \"limingao/test\",\n" +
        "    \"default_branch\": \"main\",\n" +
        "    \"ci_config_path\": null,\n" +
        "    \"homepage\": \"http://gitlab.mengyunzhi.com:2448/limingao/test\",\n" +
        "    \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/limingao/test.git\",\n" +
        "    \"ssh_url\": \"ssh://git@gitlab.mengyunzhi.com:2208/limingao/test.git\",\n" +
        "    \"http_url\": \"http://gitlab.mengyunzhi.com:2448/limingao/test.git\"\n" +
        "  },\n" +
        "  \"object_attributes\": {\n" +
        "    \"author_id\": 4,\n" +
        "    \"closed_at\": null,\n" +
        "    \"confidential\": false,\n" +
        "    \"created_at\": \"2023-01-16 11:13:01 UTC\",\n" +
        "    \"description\": \"123123\",\n" +
        "    \"discussion_locked\": null,\n" +
        "    \"due_date\": null,\n" +
        "    \"id\": 519,\n" +
        "    \"iid\": 5,\n" +
        "    \"last_edited_at\": null,\n" +
        "    \"last_edited_by_id\": null,\n" +
        "    \"milestone_id\": null,\n" +
        "    \"moved_to_id\": null,\n" +
        "    \"duplicated_to_id\": null,\n" +
        "    \"project_id\": 8,\n" +
        "    \"relative_position\": null,\n" +
        "    \"state_id\": 1,\n" +
        "    \"time_estimate\": 0,\n" +
        "    \"title\": \"issue\",\n" +
        "    \"updated_at\": \"2023-01-16 11:13:01 UTC\",\n" +
        "    \"updated_by_id\": null,\n" +
        "    \"weight\": null,\n" +
        "    \"url\": \"http://gitlab.mengyunzhi.com:2448/limingao/test/-/issues/5\",\n" +
        "    \"total_time_spent\": 0,\n" +
        "    \"time_change\": 0,\n" +
        "    \"human_total_time_spent\": null,\n" +
        "    \"human_time_change\": null,\n" +
        "    \"human_time_estimate\": null,\n" +
        "    \"assignee_ids\": [\n" +
        "      4\n" +
        "    ],\n" +
        "    \"assignee_id\": 4,\n" +
        "    \"labels\": [\n" +
        "      {\n" +
        "        \"id\": 28,\n" +
        "        \"title\": \"todo\",\n" +
        "        \"color\": \"#00b140\",\n" +
        "        \"project_id\": 8,\n" +
        "        \"created_at\": \"2023-01-12 07:43:39 UTC\",\n" +
        "        \"updated_at\": \"2023-01-12 07:43:39 UTC\",\n" +
        "        \"template\": false,\n" +
        "        \"description\": null,\n" +
        "        \"type\": \"ProjectLabel\",\n" +
        "        \"group_id\": null\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 30,\n" +
        "        \"title\": \"shelve\",\n" +
        "        \"color\": \"#e6e6fa\",\n" +
        "        \"project_id\": 8,\n" +
        "        \"created_at\": \"2023-01-12 08:53:04 UTC\",\n" +
        "        \"updated_at\": \"2023-01-12 08:53:04 UTC\",\n" +
        "        \"template\": false,\n" +
        "        \"description\": null,\n" +
        "        \"type\": \"ProjectLabel\",\n" +
        "        \"group_id\": null\n" +
        "      }\n" +
        "    ],\n" +
        "    \"state\": \"opened\",\n" +
        "    \"severity\": \"unknown\",\n" +
        "    \"action\": \"open\"\n" +
        "  },\n" +
        "  \"labels\": [\n" +
        "    {\n" +
        "      \"id\": 28,\n" +
        "      \"title\": \"todo\",\n" +
        "      \"color\": \"#00b140\",\n" +
        "      \"project_id\": 8,\n" +
        "      \"created_at\": \"2023-01-12 07:43:39 UTC\",\n" +
        "      \"updated_at\": \"2023-01-12 07:43:39 UTC\",\n" +
        "      \"template\": false,\n" +
        "      \"description\": null,\n" +
        "      \"type\": \"ProjectLabel\",\n" +
        "      \"group_id\": null\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 30,\n" +
        "      \"title\": \"shelve\",\n" +
        "      \"color\": \"#e6e6fa\",\n" +
        "      \"project_id\": 8,\n" +
        "      \"created_at\": \"2023-01-12 08:53:04 UTC\",\n" +
        "      \"updated_at\": \"2023-01-12 08:53:04 UTC\",\n" +
        "      \"template\": false,\n" +
        "      \"description\": null,\n" +
        "      \"type\": \"ProjectLabel\",\n" +
        "      \"group_id\": null\n" +
        "    }\n" +
        "  ],\n" +
        "  \"changes\": {\n" +
        "    \"author_id\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": 4\n" +
        "    },\n" +
        "    \"created_at\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": \"2023-01-16 11:13:01 UTC\"\n" +
        "    },\n" +
        "    \"description\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": \"123123\"\n" +
        "    },\n" +
        "    \"id\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": 519\n" +
        "    },\n" +
        "    \"iid\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": 5\n" +
        "    },\n" +
        "    \"project_id\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": 8\n" +
        "    },\n" +
        "    \"title\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": \"issue\"\n" +
        "    },\n" +
        "    \"updated_at\": {\n" +
        "      \"previous\": null,\n" +
        "      \"current\": \"2023-01-16 11:13:01 UTC\"\n" +
        "    }\n" +
        "  },\n" +
        "  \"repository\": {\n" +
        "    \"name\": \"测试项目\",\n" +
        "    \"url\": \"ssh://git@gitlab.mengyunzhi.com:2208/limingao/test.git\",\n" +
        "    \"description\": \"\",\n" +
        "    \"homepage\": \"http://gitlab.mengyunzhi.com:2448/limingao/test\"\n" +
        "  },\n" +
        "  \"assignees\": [\n" +
        "    {\n" +
        "      \"id\": 4,\n" +
        "      \"name\": \"mingao li\",\n" +
        "      \"username\": \"limingao\",\n" +
        "      \"avatar_url\": \"http://gitlab.mengyunzhi.com:2448/uploads/-/system/user/avatar/4/avatar.png\",\n" +
        "      \"email\": \"[REDACTED]\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";
}
