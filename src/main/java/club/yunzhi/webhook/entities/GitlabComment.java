package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weiweiyi
 * 作为gitlab comment事件的object_attributes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitlabComment {
    // comment内容 目前只用到该字段
    String note;

    String description;

    String url;
}
