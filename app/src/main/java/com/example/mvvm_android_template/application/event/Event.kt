package com.example.mvvm_android_template.application.event

interface Event

interface EventHandler<E : Event> {
    fun onEvent(event: E)
}