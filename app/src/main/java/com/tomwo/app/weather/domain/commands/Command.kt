package com.tomwo.app.weather.domain.commands

interface Command<out T> {
    fun execute(): T
}