package com.bacchoterra.financetrackerv2.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

        const val DEFAULT_PATTERN = "dd/MM/yyyy"

        fun formatDate(timestamp: Long, pattern: String): String {

            val sdf = SimpleDateFormat(pattern, Locale.getDefault())

            return sdf.format(timestamp)
        }


    }


}