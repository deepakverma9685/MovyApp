package com.example.moviez.ui.viewmodels


import androidx.lifecycle.*
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {
    private val TAG = "MoviesListViewModel"
    private val _moviesList = MutableLiveData<List<MoviesItem>>()
    val moviesList: LiveData<List<MoviesItem>> = _moviesList

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String> = _errorEvent

    var totalPages = 0

    init {
       // getMoviesListf()
    }

//    fun getMoviesList() {
//        viewModelScope.launch {
//            val resp = dataRepository.getMovies()
//            when (resp){
//                resp.isLoaded->{
//
//                }
//
//            }
//            if (resp.isLoading){
//
//            }
//            when (val resp = dataRepository.getMovies()) {
//                if (resp.isLoading){
//
//                }
//
//                is Resource.success -> {
//
//                }
//                is Result.Success -> {
//
//                }
//                else -> {}
//            }
//        }
//
//    }

    fun getMoviesList(page: Long) {
        _showProgress.value = true
        viewModelScope.launch {
            delay(2000)
            when (val resp = dataRepository.getData(page)) {
                is com.example.moviez.data.remote.Result.Success -> {
                    _showProgress.value = false
                    totalPages = 0
                    _moviesList.value = resp.data as? List<MoviesItem>
                }
                is com.example.moviez.data.remote.Result.Error -> {
                    _showProgress.value = false
                    _errorEvent.value = resp.errorMessageResource
                }
                is com.example.moviez.data.remote.Result.Loading -> {
                    _showProgress.value = true
                }
                else -> {}
            }
        }
    }

}