import com.sergiuszkierat.scalatron.ControlFunction

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/09/30
 */
class ControlFunctionFactory {
  def create = new ControlFunction().respond _
}
