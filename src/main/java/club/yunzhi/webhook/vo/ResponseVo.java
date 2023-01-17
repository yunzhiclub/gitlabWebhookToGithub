package club.yunzhi.webhook.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体
 */
@Data
@NoArgsConstructor
public class ResponseVo {

  private Integer code;
  private String msg;

  public ResponseVo(Integer code,String msg){
    this.code = code;
    this.msg = msg;
  }
}
