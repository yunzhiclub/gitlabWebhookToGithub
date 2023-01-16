package club.yunzhi.webhook.entities;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 请求数据
 */
@Data
public class GitlabRequest {
    /**
     * 请求json数据
     */
    private String json;

    private String eventName;

    private String secret;
    /**
     * 接受请求时当前时间
     */
    private Timestamp receivedTime;
}
