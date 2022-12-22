package com.example.kotlin_stady.util

class ImageLoadUtil private constructor() {

    val instance: ImageLoadUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        ImageLoadUtil()
    }
}