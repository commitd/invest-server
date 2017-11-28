package io.committed.vessel.plugins.graphql.feedback;

import java.security.Principal;
import java.time.Instant;

import io.committed.vessel.core.graphql.Context;
import io.committed.vessel.extensions.graphql.VesselGraphQlService;
import io.committed.vessel.plugins.data.feedback.data.Feedback;
import io.committed.vessel.plugins.data.feedback.data.FeedbackDataProvider;
import io.committed.vessel.server.data.services.DatasetProviders;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@VesselGraphQlService
public class FeedbackGraphQlService {

  private final DatasetProviders providers;

  public FeedbackGraphQlService(final DatasetProviders providers) {
    this.providers = providers;
  }


  @GraphQLMutation(name = "addFeedback", description = "Save feedback")
  public Mono<Feedback> addFeedback(@GraphQLRootContext final Context context,
      @GraphQLArgument(name = "pluginId") final String pluginId,
      @GraphQLArgument(name = "subject") final String subject,
      @GraphQLArgument(name = "type") final String type,
      @GraphQLArgument(name = "comment") final String comment) {

    final String user =
        context.getAuthentication().map(Principal::getName).defaultIfEmpty("guest").block();

    final Feedback f = Feedback.builder()
        .comment(comment)
        .subject(subject)
        .type(type)
        .user(user)
        .timestamp(Instant.now())
        .build();

    // Save into every feeback provider...
    return providers.findAll(FeedbackDataProvider.class)
        .flatMap(d -> d.save(f))
        .last();
  }

  @GraphQLMutation(name = "deleteFeedback", description = "Save feedback")
  public void deleteFeedback(@GraphQLRootContext final Context context,
      @GraphQLArgument(name = "id") final String feedbackId) {

    // TODO: Check if we are admin or the original feedback author

    providers.findAll(FeedbackDataProvider.class)
        .subscribe(d -> d.delete(feedbackId));
  }

  @GraphQLMutation(name = "deleteFeedback", description = "Save feedback")

  public Flux<Feedback> listFeedback(
      @GraphQLRootContext final Context context,
      @GraphQLArgument(name = "offset", description = "Start offset",
          defaultValue = "0") final int offset,
      @GraphQLArgument(name = "size", description = "Maximum values to return",
          defaultValue = "10") final int limit) {

    // TODO: Admin can list everything, user can only list there own. if not logged in then nothing

    return providers.findAll(FeedbackDataProvider.class)
        .flatMap(d -> d.findAll(offset, limit));
  }
}
