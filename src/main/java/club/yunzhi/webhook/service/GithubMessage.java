package club.yunzhi.webhook.service;

import club.yunzhi.webhook.Exception.FailedHandleHttpResponseException;
import club.yunzhi.webhook.Exception.FailedHttpCallingException;
import club.yunzhi.webhook.Exception.UnknownException;
import club.yunzhi.webhook.entities.GithubEvent;
import club.yunzhi.webhook.request.GithubPushRequest;
import club.yunzhi.webhook.util.CommonHttpUtils;
import club.yunzhi.webhook.util.HttpClientResponse;
import club.yunzhi.webhook.util.HttpClientWrapper;
import club.yunzhi.webhook.util.JsonUtil;
import club.yunzhi.webhook.vendor.DingResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import jdk.nashorn.api.scripting.JSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
public class GithubMessage {
  private static final String SUCCESS_CODE = "0";

  private final HttpClientWrapper httpClientWrapper;
  /**
   * 从application.properties获取设置好的dingTalkUrl
   */
  @Value("${dingTalkUrlPre}")
  private String dingTalkUrlPre;

  public GithubMessage(HttpClientWrapper httpClientWrapper) {
    this.httpClientWrapper = httpClientWrapper;
  }

  void sendRequest(Object githubRequest, GithubEvent githubEvent, String access_token) {
    String jsObject = JsonUtil.serializeToJson(githubRequest, true);
    if(!access_token.equals("")) {
      DingResponse response = this.sendMessage(jsObject, githubEvent, access_token);

      if (!SUCCESS_CODE.equals(response.getErrcode())) {
        throw new UnknownException("error");
      }
    }
  }

  /**
   * 直接根据json发送信息
   * @param jsObject json对象
   * @param githubEvent githubEvent
   */
  void sendJsonMessage(String jsObject, GithubEvent githubEvent, String access_token){
    if(!access_token.equals("")){
      DingResponse response = this.sendMessage(jsObject, githubEvent, access_token);
      if (!SUCCESS_CODE.equals(response.getErrcode())) {
        throw new UnknownException("error");
      }
    }
  }

  /**
   * 向钉钉github机器人推送消息
   *
   * @param jsObject js对象
   */
  private DingResponse sendMessage(String jsObject, GithubEvent githubEvent, String access_token) {
    HttpClientResponse httpClientResponse;
    Map<String, String> map = new java.util.HashMap<>(Collections.singletonMap("Content-Type", "application/json"));
    map.put("Accept", "*/*");
    map.put("X-GitHub-Event", githubEvent.toString());
    try {
      String githubDingTalkUrl = dingTalkUrlPre + "?access_token=" + access_token;

      httpClientResponse = httpClientWrapper.postReturnHttpResponse(map, githubDingTalkUrl, jsObject);
      return CommonHttpUtils.handleHttpResponse(httpClientResponse, new TypeReference<DingResponse<Void>>() {
      });
    } catch (IOException | FailedHandleHttpResponseException | FailedHttpCallingException e) {
      throw new UnknownException("Failed to execute http request: " + e.getMessage());
    } catch (Exception e) {
      throw new UnknownException("Failed to execute http request");
    }
  }
}

