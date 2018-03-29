package io.committed.invest.extensions;

/**
 * An Invest extension which offers Java services to the service side.
 *
 * <p>Typically this is used for offering functionality such as authentication, or some common
 * service layer used by multiple plugins.
 *
 * <p>You should spilt your service into an JAR file of interfaces which other can depend on, and
 * another JAR which has the extension and implementation within. In this manner developer can code
 * against your interface but are free to replace your implementation with another. It also improves
 * modularity.
 */
public interface InvestServiceExtension extends InvestExtension {}
