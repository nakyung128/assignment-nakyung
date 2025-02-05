package com.nakyung.assignment_nakyung.ui.util

fun Int.toKFormat(): String =
    when {
        this >= 1_000_000 -> String.format("%.1fm", this / 1_000_000.0)
        this >= 1_000 -> String.format("%.1fk", this / 1_000.0)
        else -> this.toString()
    }