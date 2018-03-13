package io.committed.invest.extensions.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;

/**
 * Annotates a class as providing GraphQL functions.
 *
 * Use this annotation in preference to @Service when creating services which have GraphQL (SQR)
 * functions, ie functions annotated themselves with @GraphQLQuery or @GraphQLMutation.
 *
 * When the GraphQL endpoint is set up only instances with this annotation will be merged to form
 * the final GraphQL schema.
 *
 */
@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GraphQLService {

}
