package com.qubacy.utility.androidprojecttemplate.data._common.repository

import com.qubacy.utility.androidprojecttemplate.data._common.repository._common.DataRepository

abstract class DataRepositoryTest<DataRepositoryType : DataRepository> {
    protected lateinit var mDataRepository: DataRepositoryType


}