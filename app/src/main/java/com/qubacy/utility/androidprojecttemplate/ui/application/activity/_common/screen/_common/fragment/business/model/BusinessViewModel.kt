package com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.business.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.qubacy.utility.androidprojecttemplate.data.error.repository.ErrorDataRepository
import com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.UseCase
import com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.result._common.DomainResult
import com.qubacy.utility.androidprojecttemplate.domain._common.usecase._common.result.error.ErrorDomainResult
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.StatefulViewModel
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.operation._common.UiOperation
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.operation.error.ErrorUiOperation
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.operation.loading.SetLoadingStateUiOperation
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.business.model.state.BusinessUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

abstract class BusinessViewModel<UiStateType: BusinessUiState>(
    mSavedStateHandle: SavedStateHandle,
    mErrorDataRepository: ErrorDataRepository,
    protected val mUseCase: UseCase
) : StatefulViewModel<UiStateType>(mSavedStateHandle, mErrorDataRepository) {
    companion object {
        const val TAG = "BusinessViewModel"
    }

    override val uiOperationFlow = merge(
        mUiOperationFlow,
        mUseCase.resultFlow.map { mapDomainResultFlow(it) }
    )
    private lateinit var mBusinessScope: CoroutineScope

    init {
        resetBusinessScope()
    }

    private fun resetBusinessScope() {
        mBusinessScope = CoroutineScope(viewModelScope.coroutineContext)

        mUseCase.setCoroutineScope(mBusinessScope)
    }

    override fun onCleared() {
        mBusinessScope.cancel()

        super.onCleared()
    }

    fun interrupt() {
        mBusinessScope.cancel()

        resetBusinessScope()
    }

    private fun mapDomainResultFlow(domainResult: DomainResult): UiOperation {
        return processDomainResultFlow(domainResult) ?: throw IllegalStateException()
    }

    protected open fun processDomainResultFlow(domainResult: DomainResult): UiOperation? {
        return when (domainResult::class) {
            ErrorDomainResult::class -> processErrorDomainResult(domainResult as ErrorDomainResult)
            else -> null
        }
    }

    private fun processErrorDomainResult(errorResult: ErrorDomainResult): ErrorUiOperation {
        changeLoadingState(false)

        mUiState.error = errorResult.error

        return ErrorUiOperation(errorResult.error)
    }

    protected fun changeLoadingState(isLoading: Boolean) {
        mUiState.isLoading = isLoading

        viewModelScope.launch {
            mUiOperationFlow.emit(SetLoadingStateUiOperation(isLoading))
        }
    }
}