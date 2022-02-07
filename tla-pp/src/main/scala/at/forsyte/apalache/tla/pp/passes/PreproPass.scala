package at.forsyte.apalache.tla.pp.passes

import at.forsyte.apalache.infra.passes.Pass

/**
 * A pass that does all pre-processing of TLA+ code before the assignment solver and the model checker can be run.
 *
 * @author Igor Konnov
 */
trait PreproPass extends Pass
