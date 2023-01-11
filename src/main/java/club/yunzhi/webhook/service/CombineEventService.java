package club.yunzhi.webhook.service;

import java.io.IOException;

/**
 * 合并事件
 */
public interface CombineEventService {

    /**
     * 评论&关闭issue 事件合并
     * @return json
     */
    String commentAndIssueClose(String access_token,String eventName, String json) throws IOException;

    /**
     * 对json进行总事件处理
     * @param access_token access_token
     * @param eventName eventName
     * @param json json
     * @return json
     */
    String handleEvent(String json,String eventName, String access_token) throws IOException;
}
