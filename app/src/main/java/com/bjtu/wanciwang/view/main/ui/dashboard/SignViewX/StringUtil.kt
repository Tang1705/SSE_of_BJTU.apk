package com.bjtu.wanciwang.view.main.ui.dashboard.SignViewX

object StringUtil {
    /**
     * Check whether the input string is empty
     *
     * @param input String
     * @return boolean
     */
    fun isEmpty(input: String?): Boolean {
        if (input == null || "" == input) return true
        for (i in 0 until input.length) {
            val c = input[i]
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun getStringLength(number: Int): Int {
        return number.toString().length
    }
}