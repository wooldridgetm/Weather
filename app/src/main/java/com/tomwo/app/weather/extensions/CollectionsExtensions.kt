package com.tomwo.app.weather.extensions

import java.util.*

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> = map({ Pair(it.key, it.value!!) }).toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R
{
    for (element in this)
    {
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No e lement matching predicate was found.")
}