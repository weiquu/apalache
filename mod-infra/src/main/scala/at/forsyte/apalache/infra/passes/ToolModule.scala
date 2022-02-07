package at.forsyte.apalache.infra.passes

import com.google.inject.AbstractModule

/**
 * An extension of Google Guice AbtractModule to be used by apalache modules to configurate passes order.
 *
 * @author Gabriela Mafra
 */
abstract class ToolModule extends AbstractModule {
  /**
   * The sequence of passes that need to be run for the module
   *
   * @return the sequence of passes
   */
  def passes: Seq[Class[_ <: Pass]]
}
