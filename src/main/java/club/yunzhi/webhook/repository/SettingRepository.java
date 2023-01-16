package club.yunzhi.webhook.repository;

import club.yunzhi.webhook.entities.Setting;
import club.yunzhi.webhook.service.specs.SettingSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SettingRepository extends PagingAndSortingRepository<Setting, Long>, JpaSpecificationExecutor<Setting> {
  public default List<Setting> getSettingBySecret(String secret) {
    Specification<Setting> specification = SettingSpecs.equalSecret(secret);
    return this.findAll(specification);
  }
}
