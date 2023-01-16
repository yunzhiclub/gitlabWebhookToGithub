package club.yunzhi.webhook.repository;

import club.yunzhi.webhook.entities.Setting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SettingRepository extends PagingAndSortingRepository<Setting, Long>, JpaSpecificationExecutor<Setting> {

  Optional<Setting> findBySecret(String secret);

}
