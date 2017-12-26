package io.committed.invest.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.committed.invest.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;

@VesselGraphQlService
public class VesselServerGraphQlQueryRootService {

  private final VesselServerGraphQLService service;

  @Autowired
  public VesselServerGraphQlQueryRootService(final VesselServerGraphQLService service) {
    this.service = service;
  }

  @GraphQLQuery(name = "vesselServer",
      description = "Root for all Vessel Server queries and endpoints")
  public VesselServerGraphQLService vesselServer() {
    return service;
  }

}
