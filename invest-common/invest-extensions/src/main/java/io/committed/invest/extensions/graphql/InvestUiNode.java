package io.committed.invest.extensions.graphql;

/**
 * The GraphQL Root node for local UI queries.
 *
 * Use this in your @GraphQLContext in order to extend the ui node.
 *
 * The Invest UI has its own graphQL resolvers, which are hosted purely in the UI. However in same
 * cases (eg development or testing) we want to offer a server side implementation of thsoe
 * functions.
 *
 * This should mirror the functionality available on the UI as best it can. And impleemnt the
 * entirity of investUi as per LocalSchema.ts in invest-framework. However some things will not be
 * possible (eg navigation). These should not fail, but simple do nothing.
 *
 */
// Suppressed because this is an empty GraphQL context placeholder
@SuppressWarnings("squid:S2094")
public final class InvestUiNode {

}
