package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SettingService {

  /**
   * 新增setting
   * @param setting setting
   * @return setting
   */
  Setting save(Setting setting);

  /**
   * 查找setting
   * @param id settingID
   * @return setting
   */
  Setting findById(@NotNull Long id);

  /**
   * 查询分页信息
   * @param pageable 分页条件
   * @return 分页数据
   */
  Page<Setting> page(@NotNull Pageable pageable);

  /**
   * 更新setting
   * @param id ID
   * @param setting setting
   * @return 用户
   */
  Setting update(Long id, Setting setting);

  /**
   * 删除setting
   * @param id ID
   */
  void deleteById(Long id);

  Setting getSettingBySecret(String secret);
}
