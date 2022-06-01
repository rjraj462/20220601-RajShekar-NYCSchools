package com.nycschool.usecase.errors

import com.nycschool.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
