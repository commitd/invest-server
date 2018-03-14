package io.committed.invest.server.graphql.data;

import java.util.Collection;
import io.committed.invest.extensions.annotations.GraphQLService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * A 'named' holder for GraphQL services.
 *
 * That is Spring beans which have bene annotated with {@link GraphQLService}.
 *
 */
@Data
@RequiredArgsConstructor
public class GraphQlServices {
  private final Collection<Object> services;
}
