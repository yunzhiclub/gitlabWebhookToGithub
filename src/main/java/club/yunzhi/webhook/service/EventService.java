package club.yunzhi.webhook.service;

import club.yunzhi.webhook.request.GitlabIssueRequest;
import club.yunzhi.webhook.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 事件接口
 */
public interface EventService {


  String getEventKey();

  /**
   * 处理事件
   *
   * @param json data
   * @throws IOException exception
   */
  void handleEvent(String json, String secret) throws IOException;

  /**
   * 反序列化——把字节恢复为Java对象
   */
  static <T> T covertJson(String json, Class<T> clazz) throws IOException {
    return JsonUtil.deserializeFromJson(json, clazz);
  }


  static String getAccessToken(String secret, SettingService settingService) {
    if (settingService.getSettingBySecret(secret) == null) {
      return "";
    } else {
      return settingService.getSettingBySecret(secret).getToken();
    }
  }
}
