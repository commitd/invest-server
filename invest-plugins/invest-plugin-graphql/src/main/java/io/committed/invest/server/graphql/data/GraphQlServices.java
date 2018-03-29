package io.committed.invest.server.graphql.data;

import java.util.Collection;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import io.committed.invest.extensions.annotations.GraphQLService;

/**
 * A 'named' holder for GraphQL services.
 *
 * <p>That is Spring beans which have bene annotated with {@link GraphQLService}.
 */
@Data
@RequiredArgsConstructor
public class GraphQlServices {
  private final Collection<Object> services;
}
