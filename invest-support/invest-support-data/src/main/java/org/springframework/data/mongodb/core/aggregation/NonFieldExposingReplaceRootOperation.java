package org.springframework.data.mongodb.core.aggregation;

import org.bson.Document;

/**
 * The Spring Mongo ReplaceRootOperation has the exposes fields interface but then it proceeds to
 * expose no fields. This means taht when you it builds the following aggregation it doesn't use any
 * to bson doc mappers, etc.
 *
 * That meant that say GeoCommand isn't mapped see the if statement in
 * AggregationOperationRenderer.toDocument.
 *
 * Seems like a bug in Spring Data Mongo? Not sure where those - should ReplceRoot try to figure out
 * its fields, should the AggregationoperationRenderer keep bits of the context?
 *
 */
public class NonFieldExposingReplaceRootOperation implements AggregationOperation {



  private final String field;

  public NonFieldExposingReplaceRootOperation(final String field) {
    this.field = field;
  }

  @Override
  public Document toDocument(final AggregationOperationContext context) {
    return new Document("$replaceRoot", new Document("newRoot", '$' + field));
  }


}
