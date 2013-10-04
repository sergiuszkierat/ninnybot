package com.sergiuszkierat.scalatron.input

import com.sergiuszkierat.scalatron.Chars._
import com.sergiuszkierat.scalatron.input.ServerOpcodeRegexps._
import com.sergiuszkierat.scalatron.input.exception.{InvalidCommandEmptyParametersException, InvalidCommandParameterKeyException, InvalidCommandParametersException, InvalidCommandException}
import scala.collection.Iterable

/**
 * Utility object for parsing opcodes from server
 * Every input string contains one and every output string zero or more parametrized commands.
 * The string has the following format: "Opcode(key=value,key=value,...)|Opcode(...)|..."
 *
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/01
 * @see <a href="http://github.com/scalatron/scalatron/raw/master/Scalatron/doc/pdf/Scalatron%20Protocol.pdf">Scalatron Protocol</a>
 */
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

    def validateParamMapKeys(keys:  Iterable[String], allowedParameters: List[String]) = {
      val filtered: Iterable[String] = keys.filterNot(allowedParameters contains _)
      if (filtered.nonEmpty)
        throw new InvalidCommandParameterKeyException(filtered.mkString(Comma.toString))
    }

    def validateParametersNotEmpty(parameters: Array[String]) = if (parameters.nonEmpty && Option(parameters(0)).getOrElse("").isEmpty)
      throw new InvalidCommandEmptyParametersException

    def parseParameters(opcode: String, parameters: String, allowedParameters: List[String]): (String, Map[String, Any]) = {
      val params = parameters.split(Comma)
      validateParametersNotEmpty(params)
      val keyValuePairs = params.map(splitParameterIntoKeyValue).toMap
      validateParamMapKeys(keyValuePairs.keys, allowedParameters)
      (opcode, keyValuePairs)
    }

    command match {
      case Welcome(opcode, paramMap) => parseParameters(opcode, paramMap, OpcodeParameters.Welcome)
      case React(opcode, paramMap) => parseParameters(opcode, paramMap, OpcodeParameters.React)
      case Goodbye(opcode, paramMap) => parseParameters(opcode, paramMap, OpcodeParameters.Goodbye)
      case _ => throw new InvalidCommandException(command)
    }
  }
}
