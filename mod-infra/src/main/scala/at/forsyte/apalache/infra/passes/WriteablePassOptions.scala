package at.forsyte.apalache.infra.passes

import at.forsyte.apalache.infra.PassOptionException
import com.google.inject.Singleton

import scala.collection.mutable

/**
  * <p>The central store for various options given to the passes.
  * An option is a key-value pair. By convention, a key is a string
  * of the shape pass.option, where pass is the pass name and option is the option name.
  * A pass name does not have to match exactly the name of the pass that is accessing
  * the option, but of a class of passes. For instance, the option parser.filename can be
  * used by all parsing passes, not just the pass called 'parser'.</p>
  *
  * <p>This class is used only internally. When you implement your own pass,
  * use the trait PassOptions.</p>
  *
  * @author Igor Konnov
  */
@Singleton
class WriteablePassOptions extends PassOptions {
  private val store: mutable.Map[String, Any] = mutable.HashMap[String, Any]()

  /**
    * Set a pass option
    * @param name an option name, where the pass name is separated from the option name with a dot (.)
    * @param value an option value
    */
  def setOption(name: String, value: Any): Unit = {
    if (!name.exists(_ == '.')) {
      throw new PassOptionException("Expected an option of format <pass>.<option, found: " + name)
    }
    store += (name -> value)
  }

  /**
    * Get a pass option
    * @param passName a pass name, or any other convenient name
    * @param optionName an option name
    * @return the option value, normally, an Integer or String
    */
  def getOption(passName: String, optionName: String): Option[Any] = {
    store.get(passName + "." + optionName)
  }

  /**
    * Get a pass option, or fallback to the default value
    *
    * @param passName   a pass name, or any other convenient name
    * @param optionName an option name
    * @param default    a default value
    * @return the option value, normally, an Integer or String
    */
  def getOption(passName: String, optionName: String, default: Any): Any = {
    val value = getOption(passName, optionName)
    if (value.isDefined) {
      value.get
    } else {
      default
    }
  }

  /**
    * Get a pass option. If there is no such option, throw an OptionException.
    *
    * @param passName   a pass name, or any other convenient name
    * @param optionName an option name
    * @return the option value, normally, an Integer or String
    */
  def getOptionOrError(passName: String, optionName: String): Any = {
    val value = getOption(passName, optionName)
    if (value.isDefined) {
      value.get
    } else {
      throw new PassOptionException("The mandatory option %s.%s not found"
        .format(passName, optionName))
    }
  }

}
