package club.yunzhi.webhook.util;

import club.yunzhi.webhook.service.GitLabNotifyServiceImpl;
import club.yunzhi.webhook.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ResponseUtil {

  private static final Logger logger = LoggerFactory.getLogger(GitLabNotifyServiceImpl.class);

  private static final Integer SUCCESS_CODE = 0;
  private static final String SUCCESS_MSG = "success";

  public static ResponseVo ok() {
    return new ResponseVo(SUCCESS_CODE, SUCCESS_MSG);
  }

  public static ResponseVo error(HttpStatus code, String msg) {
    logger.error(msg);
    return new ResponseVo(code.value(), msg);
  }
}
