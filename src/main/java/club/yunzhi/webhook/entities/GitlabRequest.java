package club.yunzhi.webhook.entities;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 请求数据
 */
@Data
public class GitlabRequest {
    // 请求id 唯一
    private String requestId;
    /**
     * 请求json数据
     */
    private String json;

    private String eventName;

    private String access_token;
    /**
     * 接受请求时当前时间
     */
    private Timestamp receivedTime;
}
