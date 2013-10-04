package com.sergiuszkierat.scalatron.input

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/01
 */
object OpcodeParameters {

  val Welcome = List(
    "name",
    "path",
    "apocalypse",
    "round"
  )

  val React = List(
    "generation",
    "name",
    "time",
    "view",
    "energy",
    "master"
  )

  val Goodbye = List(
    "energy"
  )
}
