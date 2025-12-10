package com.example.mvvm_android_template.application.event

sealed interface BrochuresEvent : Event {
    data class BrochureClicked(val id: Int) : BrochuresEvent
}
