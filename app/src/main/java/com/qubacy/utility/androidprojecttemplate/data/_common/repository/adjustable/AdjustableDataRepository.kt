package com.qubacy.utility.androidprojecttemplate.data._common.repository.adjustable

import com.qubacy.utility.androidprojecttemplate._common.coroutine.CoroutineUser
import com.qubacy.utility.androidprojecttemplate.data._common.repository._common.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

abstract class AdjustableDataRepository(
    coroutineScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher
) : CoroutineUser(coroutineDispatcher, coroutineScope), DataRepository {

}