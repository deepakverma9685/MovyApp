package com.example.moviez.data.remote

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviez.data.models.ErrorsModel
import com.example.moviez.data.remote.networkadapter.NetworkResponse
import kotlinx.coroutines.*


/**
 * ResultType - data type used locally.
 * RequestType - data type returned from the API
 */
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val TAG = "NetworkBoundResource"
    private val result = MutableLiveData<Resource<ResultType>>()
    private val resultTwo = MediatorLiveData<Resource<ResultType>>()

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)


    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
             Resource.loading(null)
        }

        coroutineScope.launch {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                fetchFromNetwork(dbResult)
            } else {
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        setValue(Resource.loading(dbResult))
        val apiResponse = createCallAsync()
    //    val result = processResponse(apiResponse)
       // saveCallResults(result)
        val newData = loadFromDb()
        setValue(Resource.success(newData))
    }

//    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
//        val apiResponse = createCallAsync()
//        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//
//        when(apiResponse){
//            is NetworkResponse.Success ->{
//
//            }
//            is NetworkResponse.ServerError ->{
//
//            }
//            else -> {}
//        }
//
//        resultTwo.addSource(dbSource) { newData ->
//            setValue(Resource.loading(newData))
//        }
//        resultTwo.addSource(apiResponse) { response ->
//            resultTwo.removeSource(response)
//            resultTwo.removeSource(dbSource)
//            when (response) {
//                is NetworkResponse.Success -> {
//                    appExecutors.diskIO().execute {
//                        saveCallResult(processResponse(response))
//                        appExecutors.mainThread().execute {
//                            // we specially request a new live data,
//                            // otherwise we will get immediately last cached value,
//                            // which may not be updated with latest results received from network.
//                            resultTwo.addSource(loadFromDb()) { newData ->
//                                setValue(Resource.success(newData))
//                            }
//                        }
//                    }
//                }
//                is NetworkResponse.ServerError -> {
//                    appExecutors.mainThread().execute {
//                        // reload from disk whatever we had
//                        result.addSource(loadFromDb()) { newData ->
//                            setValue(Resource.success(newData))
//                        }
//                    }
//                }
//                is ApiErrorResponse -> {
//                    onFetchFailed()
//                    result.addSource(dbSource) { newData ->
//                        setValue(Resource.error(response.errorMessage, newData))
//                    }
//                }
//                else -> {}
//            }
//        }
//    }



    fun asLiveData(): LiveData<Resource<ResultType>> = result

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue)
            result.postValue(newValue)
    }

    //Decide whether to fetch potentially updated data from the network
    @MainThread
    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean


    //Get the cached data from the database.
    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType


    //Create the API call
    @MainThread
    protected abstract suspend fun createCallAsync(): NetworkResponse<RequestType,ErrorsModel>



    //Maps response into Database objects
    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType


    // Save the result of the API response into the database
    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)


    //Called when the fetch fails.
    protected abstract fun onFetchFailed(e: Exception)

}