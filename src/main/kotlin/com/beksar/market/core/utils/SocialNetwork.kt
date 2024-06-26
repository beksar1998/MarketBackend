package com.beksar.market.core.utils

import java.net.URLEncoder


object SocialNetwork {

    fun whatsApp(
        name: String,
        price: Int
    ): String {
//        val text = "$name цена: *${price} ₸ *"
//        val encode = URLEncoder.encode(text, "UTF-8")
        return "https://wa.me/7079777093?lang=ru"//?text=${encode}"
    }

    fun telegram(
        name: String,
        price: Int
    ): String {
//        val text = "$name цена: *${price} ₸ *"
//        val encode = URLEncoder.encode(text, "UTF-8")
//        return "tg://msg?text=${encode}&to=+77079777093"
        return "https://t.me/+77079777093"
    }
}