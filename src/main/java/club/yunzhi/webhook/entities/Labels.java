package club.yunzhi.webhook.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Labels {
  List<Label> previous;
  List<Label> current;
}
