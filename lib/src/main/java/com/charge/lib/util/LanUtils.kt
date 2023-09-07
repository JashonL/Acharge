package com.charge.lib.util

import android.content.Context
import java.util.*

object LanUtils {
    /**
     * 获取是中文还是其他：0：代表中国；1：代表外国
     *
     * @param context
     * @return
     */
    fun getLanguage(context: Context): Int {
        var lan = 1
        val locale = context.resources.configuration.locale
        val language = locale.language
        if (language.lowercase(Locale.getDefault()).contains("zh")) {
            lan = 0
            if (locale.country.lowercase(Locale.getDefault()) != "cn") {
                lan = 14
            }
        }
        if (language.lowercase(Locale.getDefault()).contains("en")) {
            lan = 1
        }
        if (language.lowercase(Locale.getDefault()).contains("fr")) {
            lan = 2
        }
        if (language.lowercase(Locale.getDefault()).contains("ja")) {
            lan = 3
        }
        if (language.lowercase(Locale.getDefault()).contains("it")) {
            lan = 4
        }
        if (language.lowercase(Locale.getDefault()).contains("ho")) {
            lan = 5
        }
        if (language.lowercase(Locale.getDefault()).contains("tk")) {
            lan = 6
        }
        if (language.lowercase(Locale.getDefault()).contains("pl")) {
            lan = 7
        }
        if (language.lowercase(Locale.getDefault()).contains("gk")) {
            lan = 8
        }
        if (language.lowercase(Locale.getDefault()).contains("gm")) {
            lan = 9
        }
        if (language.lowercase(Locale.getDefault()).contains("pt")) {
            lan = 10
        }
        if (language.lowercase(Locale.getDefault()).contains("sp")) {
            lan = 11
        }
        if (language.lowercase(Locale.getDefault()).contains("vn")) {
            lan = 12
        }
        if (language.lowercase(Locale.getDefault()).contains("hu")) {
            lan = 13
        }
        return lan
    }


}