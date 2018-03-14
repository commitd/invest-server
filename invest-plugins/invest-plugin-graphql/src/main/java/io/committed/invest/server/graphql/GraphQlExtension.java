package io.committed.invest.server.graphql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.committed.invest.extensions.InvestApiExtension;

/**
 * An extension which adds the GraphQL Endpoint API.
 *
 * The endpoint is available via on /graphql.
 *
 * The implementation is backed by GraphQL SPQR (see https://github.com/leangen/graphql-spqr).
 *
 * In order to add GraphQL queries and mutations to the GraphQL API should annotate Spring bean
 * components with @GraphQLService. These will automatically be picked up by this extension and
 * merged into the schema.
 *
 * Typically for GraphQL you will want to 'nest' query functionality. We suggest making heavy use
 * of @GraphQlContext from SPQR to achieve this.
 *
 */
@Configuration
@Import(GraphQlConfig.class)
public class GraphQlExtension implements InvestApiExtension {

}
