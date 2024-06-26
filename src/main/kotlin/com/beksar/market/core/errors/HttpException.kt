package com.beksar.market.core.errors

class HttpException(override val message: String, val errors: Map<String, List<String>>) : RuntimeException()


abstract class HttpError(open val message: String, val errors: Map<String, List<String>>, exception: Exception? = null)

object NotNullError : HttpError(message = "Значение не межет быть пустым", errors = emptyMap())
object CommonError : HttpError(message = "Неизвестная ошибка", errors = emptyMap())
object PageSizeError : HttpError(message = "Ошибка размера страницы", errors = emptyMap())
class SaveDBError(exception: Exception?) :
    HttpError(message = "Ошибка при сохранении в базу", errors = emptyMap(), exception = exception)

class MessageError(override val message: String) : HttpError(message = message, errors = emptyMap())
