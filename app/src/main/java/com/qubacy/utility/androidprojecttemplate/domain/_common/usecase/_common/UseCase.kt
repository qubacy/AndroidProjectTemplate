package com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common

import com.qubacy.utility.androidprojecttemplate._common.coroutine.CoroutineUser
import com.qubacy.utility.androidprojecttemplate.data.error.repository.ErrorDataRepository
import com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.result._common.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class UseCase(
    protected val mErrorDataRepository: ErrorDataRepository,
    mCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mCoroutineScope: CoroutineScope = CoroutineScope(mCoroutineDispatcher)
) : CoroutineUser(mCoroutineDispatcher, mCoroutineScope) {
    protected val mResultFlow: MutableSharedFlow<DomainResult> = MutableSharedFlow()
    val resultFlow: SharedFlow<DomainResult> get() = mResultFlow
}