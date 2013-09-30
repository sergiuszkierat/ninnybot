package scala

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/09/30
 */
class ControlFunctionFactory {
  def create = new ControlFunction().respond _
}

class ControlFunction {
  def respond(input: String) = "Status(text=Hello World)"
}
