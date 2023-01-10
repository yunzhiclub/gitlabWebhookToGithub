package club.yunzhi.webhook.service;

import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.util.JsonUtil;

import java.io.IOException;

/**
 * 事件接口
 */
public interface EventService {


  String getEventKey();

  /**
   * 处理事件
   * @param json data
   * @throws IOException exception
   */
  void handleEvent(String json) throws IOException;

  /**
   * 反序列化——把字节恢复为Java对象
   */
  static <T> T covertJson(String json, Class<T> clazz) throws IOException {
    return JsonUtil.deserializeFromJson(json, clazz);
  }
}
