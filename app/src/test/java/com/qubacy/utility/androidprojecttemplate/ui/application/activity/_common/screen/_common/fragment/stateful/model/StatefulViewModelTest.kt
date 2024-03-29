package com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.qubacy.utility.androidprojecttemplate._common._test._common.rule.dispatcher.MainDispatcherRule
import com.qubacy.utility.androidprojecttemplate._common.error.Error
import com.qubacy.utility.androidprojecttemplate._common.error.type.TestErrorType
import com.qubacy.utility.androidprojecttemplate.data.error.repository.ErrorDataRepository
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.operation.error.ErrorUiOperation
import com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.state.BaseUiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

abstract class StatefulViewModelTest<
    UiStateType : BaseUiState, ViewModelType : StatefulViewModel<UiStateType>
> {
    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    protected lateinit var mModel: ViewModelType

    protected open fun setUiState(uiState: UiStateType) {
        StatefulViewModel::class.java.getDeclaredField("mUiState")
            .apply { isAccessible = true }
            .set(mModel, uiState)
    }

    @Before
    fun setup() {
        preInit()
        init()
    }

    protected open fun preInit() { }

    private fun init() {
        resetResults()
        initViewModel()
    }

    protected open fun resetResults() { }

    protected open fun initViewModel() {
        val savedStateHandleMock = Mockito.mock(SavedStateHandle::class.java)
        val errorDataRepositoryMock = Mockito.mock(ErrorDataRepository::class.java)

        Mockito.`when`(errorDataRepositoryMock.getError(Mockito.anyLong()))
            .thenAnswer {
                val errorTypeId = it.arguments[0] as Long

                return@thenAnswer Error(errorTypeId, "", false)
            }

        mModel = createViewModel(savedStateHandleMock, errorDataRepositoryMock)
    }

    protected abstract fun createViewModel(
        savedStateHandle: SavedStateHandle,
        errorDataRepository: ErrorDataRepository
    ): ViewModelType

    @Test
    fun retrieveErrorTest() = runTest {
        val expectedErrorType = TestErrorType.TEST

        mModel.uiOperationFlow.test {
            mModel.retrieveError(expectedErrorType)

            val errorOperation = awaitItem()

            Assert.assertEquals(ErrorUiOperation::class, errorOperation::class)

            val error = (errorOperation as ErrorUiOperation).error

            Assert.assertEquals(expectedErrorType.id, error.id)
        }
    }
}