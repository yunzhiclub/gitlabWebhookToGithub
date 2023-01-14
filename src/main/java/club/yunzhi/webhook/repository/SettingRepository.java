package club.yunzhi.webhook.repository;

import club.yunzhi.webhook.entities.Setting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SettingRepository extends PagingAndSortingRepository<Setting, Long>, JpaSpecificationExecutor<Setting> {
}
