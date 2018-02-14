package io.committed.invest.extensions.data.dataset;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dataset {

  private String id;

  private String name;


  @Builder.Default
  private String description = "";

  @Builder.Default
  private List<DataProviderSpecification> providers = Collections.emptyList();
}
