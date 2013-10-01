import org.scalatest._

/**
 * @author Sergiusz Kierat <sergiusz.kierat@javart.eu>
 * @since 2013/10/01
 */
class ControlFunctionFactorySpec extends FlatSpec {

  "A ControlFunctionFactory" should "respond \"Status(text=Hello World)\"" in {
    val entryPoint = new ControlFunctionFactory
    val respond: (String) => String = entryPoint.create
    val inputFromServer: String = "insignificant"

    assert(respond(inputFromServer) === "Status(text=Hello World)")
  }
}
