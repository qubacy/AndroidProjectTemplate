package com.qubacy.utility.androidprojecttemplate._common.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class CoroutineUser(
    protected var mCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    protected var mCoroutineScope: CoroutineScope = CoroutineScope(mCoroutineDispatcher)
) {
    fun setCoroutineScope(coroutineScope: CoroutineScope) {
        mCoroutineScope = coroutineScope

        onCoroutineScopeSet()
    }

    protected open fun onCoroutineScopeSet() {}

    fun setCoroutineDispatcher(coroutineDispatcher: CoroutineDispatcher) {
        mCoroutineDispatcher = coroutineDispatcher
    }
}