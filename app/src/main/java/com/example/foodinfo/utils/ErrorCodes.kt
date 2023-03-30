package com.example.foodinfo.utils


object ErrorCodes {
    val CLIENT_RANGE = 400..499
    val SERVER_RANGE = 500..599

    const val CLIENT_BAD_REQUEST = 400
    const val CLIENT_UNAUTHORIZED = 401
    const val CLIENT_FORBIDDEN = 403
    const val CLIENT_NOT_FOUND = 404
    const val CLIENT_TOO_MANY_REQUESTS = 429

    const val RESPONSE_NETWORK = 600
    const val RESPONSE_UNKNOWN = 700

    const val STATE_NO_DATA = 800
    const val STATE_LOCAL_UNKNOWN = 801
    const val STATE_LOCAL_MAPPING = 802
    const val STATE_REMOTE_UNKNOWN = 803
    const val STATE_REMOTE_MAPPING = 804
    const val STATE_REMOTE_SAVE = 805
}