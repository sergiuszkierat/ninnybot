package com.sergiuszkierat.scalatron

import com.sergiuszkierat.scalatron.input.ServerOpcodeParser

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/09/30
 */
class ControlFunction {
  def respond(input: String) = {
    //TODO [skierat on 01/10/13]: to remove
    if (input == "insignificant") "Status(text=Hello World)"
    else ServerOpcodeParser(input)._1
  }
}
