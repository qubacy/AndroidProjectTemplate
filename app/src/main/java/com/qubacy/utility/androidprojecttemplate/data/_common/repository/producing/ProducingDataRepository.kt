package com.qubacy.utility.androidprojecttemplate.data._common.repository.producing

import com.qubacy.utility.androidprojecttemplate.data._common.repository.adjustable.AdjustableDataRepository
import com.qubacy.utility.androidprojecttemplate.data._common.repository._common.result.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class ProducingDataRepository(
    coroutineScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher
) : AdjustableDataRepository(coroutineScope, coroutineDispatcher) {
    protected val mResultFlow: MutableSharedFlow<DataResult> = MutableSharedFlow()
    val resultFlow: SharedFlow<DataResult> get() = mResultFlow

}