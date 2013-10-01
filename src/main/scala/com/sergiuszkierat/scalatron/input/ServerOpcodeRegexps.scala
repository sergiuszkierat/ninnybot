package com.sergiuszkierat.scalatron.input

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/01
 */
object ServerOpcodeRegexps {
  val Welcome = """(Welcome)\((.*)\)""".r

  val React = """(React)\((.*)\)""".r

  val Goodbye = """(Goodbye)\((.*)\)""".r
}
