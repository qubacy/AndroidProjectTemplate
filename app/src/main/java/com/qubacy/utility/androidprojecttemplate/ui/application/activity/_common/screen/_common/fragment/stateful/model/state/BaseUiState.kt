package com.qubacy.utility.androidprojecttemplate.ui.application.activity._common.screen._common.fragment.stateful.model.state

import com.qubacy.utility.androidprojecttemplate._common.error.Error
import java.io.Serializable

abstract class BaseUiState(
    var error: Error? = null
) : Serializable {
    abstract fun copy(): BaseUiState
}