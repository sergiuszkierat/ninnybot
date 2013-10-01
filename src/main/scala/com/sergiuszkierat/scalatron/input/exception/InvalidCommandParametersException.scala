package com.sergiuszkierat.scalatron.input.exception

/**
 * @author Sergiusz Kierat <sergiusz.kierat@gmail.com>
 * @since 2013/10/01
 */
class InvalidCommandParametersException(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)