package club.yunzhi.webhook.service.specs;

import club.yunzhi.webhook.entities.Setting;
import org.springframework.data.jpa.domain.Specification;

public class SettingSpecs {
  public static Specification<Setting> equalSecret(String secret) {
    if (secret != null) {
      return (Specification<Setting>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("secret").as(String.class),secret);
    } else {
      return Specification.where(null);
    }
  }

}
