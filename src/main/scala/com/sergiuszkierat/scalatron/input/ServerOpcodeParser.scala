package com.sergiuszkierat.scalatron.input

import com.sergiuszkierat.scalatron.Chars._
import com.sergiuszkierat.scalatron.input.ServerOpcodeRegexps._
import com.sergiuszkierat.scalatron.input.exception.{InvalidCommandParametersException, InvalidCommandException}

/**
 * Utility object for parsing opcodes from server
 * Every input string contains one and every output string zero or more parametrized commands.
 * The string has the following format: "Opcode(key=value,key=value,...)|Opcode(...)|..."
 *
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/01
 * @see <a href="http://github.com/scalatron/scalatron/raw/master/Scalatron/doc/pdf/Scalatron%20Protocol.pdf">Scalatron Protocol</a>
 */
//TODO [skierat on 01/10/13]: check if parameters are valid (only defined keys)
object ServerOpcodeParser {

  //TODO [skierat on 01/10/13]: make return List[(String, Map[String, String])]
  def apply(command: String): (String, Map[String, Any]) = {
    val intRegexp = """(\d+)""".r

    def parseInt(integerString: String) = integerString match {
      case intRegexp(_) => integerString.toInt
      case _ => integerString
    }

    def splitParameterIntoKeyValue(param: String): (String, Any) = {
      val segments = param.split(EqualsSign)
      if (segments.length != 2)
        throw new InvalidCommandParametersException(param)
      (segments(0), parseInt(segments(1)))
    }

    def welcome(opcode: String, parameters: String): (String, Map[String, Any]) = {
      //      println(String.format("OP: %s, keyValuePairs: %s", opcode, parameters))
      val params = parameters.split(Comma)
      val keyValuePairs = params.map(splitParameterIntoKeyValue).toMap
      (opcode, keyValuePairs)
    }

    command match {
      case Welcome(opcode, paramMap) => welcome(opcode, paramMap)
      case React() => ???
      case Goodbye() => ???
      case _ => throw new InvalidCommandException(command)
    }
  }
}
