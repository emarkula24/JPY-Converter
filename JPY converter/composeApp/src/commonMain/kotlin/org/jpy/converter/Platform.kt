package org.jpy.converter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform