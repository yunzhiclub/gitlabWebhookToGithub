package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.Sender;
import club.yunzhi.webhook.entities.Setting;
import club.yunzhi.webhook.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {
  private final SettingRepository settingRepository;
  private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);

  @Autowired
  public SettingServiceImpl(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  @Override
  public Setting save(Setting setting) {
    Assert.notNull(setting.getGitlabUrl(), "GitlabUrl不能为空");
    Assert.notNull(setting.getSecret(), "Secret不能为空");
    Assert.notNull(setting.getToken(), "Token不能为空");
    Assert.notNull(setting.getName(), "Name不能为空");
    Setting newSetting = new Setting();

    newSetting.setGitlabUrl(setting.getGitlabUrl());
    newSetting.setSecret(setting.getSecret());
    newSetting.setToken(setting.getToken());
    newSetting.setName(setting.getName());

    return this.settingRepository.save(newSetting);
  }

  @Override
  public Setting findById(@NotNull Long id) {
    return this.settingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("找不到相关实体"));
  }

  @Override
  public Page<Setting> page(@NotNull Pageable pageable) {
    return this.settingRepository.findAll(pageable);
  }

  @Override
  public Setting update(Long id, Setting setting) {
    Setting oldSetting = this.findById(id);
    Assert.notNull(setting.getGitlabUrl(), "GitlabUrl不能为空");
    Assert.notNull(setting.getSecret(), "Secret不能为空");
    Assert.notNull(setting.getToken(), "Token不能为空");
    Assert.notNull(setting.getName(), "Name不能为空");

    oldSetting.setGitlabUrl(setting.getGitlabUrl());
    oldSetting.setSecret(setting.getSecret());
    oldSetting.setToken(setting.getToken());
    oldSetting.setName(setting.getName());
    return this.settingRepository.save(oldSetting);
  }

  @Override
  public void deleteById(Long id) {
    this.settingRepository.deleteById(id);
  }

  @Override
  public Setting getSettingBySecret(String secret) {
    if (this.settingRepository.findBySecret(secret).isPresent()) {
      return this.settingRepository.findBySecret(secret).get();
    } else {
      logger.debug("未找到Secret:" + secret + "对应Setting");
      return null;
    }
  }

  @Override
  public boolean existBySetting(String secret) {
    return this.settingRepository.findBySecret(secret).isPresent();
  }


}
