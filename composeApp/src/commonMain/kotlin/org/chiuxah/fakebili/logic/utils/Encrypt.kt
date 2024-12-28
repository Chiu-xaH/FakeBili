package org.chiuxah.fakebili.logic.utils

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

object Encrypt {
    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            val hex = (0xff and b.toInt()).toString(16)
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    fun hmacSha256(key: String, message: String): String {
        val mac: Mac = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(key.toByteArray(UTF_8), "HmacSHA256")
        mac.init(secretKeySpec)
        val hash: ByteArray = mac.doFinal(message.toByteArray(UTF_8))
        return bytesToHex(hash)
    }
}
