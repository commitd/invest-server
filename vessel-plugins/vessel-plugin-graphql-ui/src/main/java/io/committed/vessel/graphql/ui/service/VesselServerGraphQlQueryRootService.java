package io.committed.vessel.graphql.ui.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.leangen.graphql.annotations.GraphQLQuery;

@VesselGraphQlService
public class VesselServerGraphQlQueryRootService {

  private final VesselServerGraphQLService service;

  @Autowired
  public VesselServerGraphQlQueryRootService(final VesselServerGraphQLService service) {
    this.service = service;
  }

  @GraphQLQuery
  public VesselServerGraphQLService vesselServer() {
    return service;
  }

}
