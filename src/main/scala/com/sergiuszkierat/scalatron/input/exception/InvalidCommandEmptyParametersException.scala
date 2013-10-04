package com.sergiuszkierat.scalatron.input.exception

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/04
 */
class InvalidCommandEmptyParametersException(message: String = "Empty parameters", cause: Throwable = null) extends RuntimeException(message, cause)
