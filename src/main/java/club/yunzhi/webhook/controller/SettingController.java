package club.yunzhi.webhook.controller;

import club.yunzhi.webhook.entities.Setting;
import club.yunzhi.webhook.service.SettingService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "setting")
public class SettingController {
  private final SettingService settingService;

  public SettingController(SettingService settingService) {
    this.settingService = settingService;
  }

  @GetMapping("page")
  public Page<Setting> page(
      @SortDefault.SortDefaults(@SortDefault(sort = "id", direction = Sort.Direction.DESC)) Pageable pageable
  ) {
    // 根据divisionalWorksTemplateId 获取实体

    return this.settingService.page(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Setting save(@RequestBody Setting building) {
    return this.settingService.save(building);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    this.settingService.deleteById(id);
  }

  @GetMapping("{id}")
  public Setting getById(@PathVariable Long id) {
    return this.settingService.findById(id);
  }

  @PutMapping("{id}")
  public Setting update(@PathVariable Long id, @RequestBody Setting setting) {
    return this.settingService.update(id, setting);
  }
}
