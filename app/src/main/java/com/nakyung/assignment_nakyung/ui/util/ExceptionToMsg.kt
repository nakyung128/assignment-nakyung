package com.nakyung.assignment_nakyung.ui.util

import coil.network.HttpException
import java.io.IOException

fun Exception.toErrorMessage(): String =
    when (this) {
        is HttpException ->
            when (response.code) {
                403 -> "접근 권한이 없습니다."
                404 -> "존재하지 않는 페이지입니다."
                else -> "알 수 없는 오류 발생 ${response.code}"
            }

        is IOException -> "네트워크 오류입니다."
        else -> "알 수 없는 오류 발생"
    }
