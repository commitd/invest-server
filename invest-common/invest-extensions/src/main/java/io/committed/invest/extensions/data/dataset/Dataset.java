package io.committed.invest.extensions.data.dataset;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An Invest dataset.
 *
 * An Invest dataset represents a collection of datas provider which Invest considers as a single
 * data.
 *
 * The providers might all come from the same datasource or they might be from different original
 * sources. The concept behind Invest dataset's is that Invest does not care which case this is and
 * the application (and application configurer is free to choose).
 *
 * For example, an applicaiton might have a display news. One instance of it may which to have a
 * dataset for each news source (BBC, Sky, etc) so its users can view each in isolation. In that
 * case we would have a 1-1 mapping between dataset and dataproviders.ANother application instance
 * might wish to combine these together into a single dataset, which has multiple providers (one for
 * each new source).
 *
 */
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
