package org.chiuxah.fakebili

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform