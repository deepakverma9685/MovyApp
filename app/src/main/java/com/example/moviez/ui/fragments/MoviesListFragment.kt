package com.example.moviez.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviez.R
import com.example.moviez.base.BaseFragment
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.databinding.FragmentMovilesListBinding
import com.example.moviez.ui.adapters.MoviesListAdapter
import com.example.moviez.ui.viewmodels.MoviesListViewModel
import com.example.moviez.utils.EqualSpacing
import com.example.moviez.utils.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMovilesListBinding>() {
    private val TAG = "MoviesListFragment"
    private val viewModel: MoviesListViewModel by viewModels()
    private lateinit var moviesListAdapter: MoviesListAdapter
    private val moviesItem = ArrayList<MoviesItem>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_moviles_list
    }

    override fun initViews() {
        setupRecyclerView()
        viewModel.getMoviesList(1)
    }


    override fun observeViewModel() {
        observeProgressBar()
        observeMoviesData()
        observeErrors()
    }

    private fun observeProgressBar() {
         viewModel.showProgress.observe(this){
             if (it){
                 showProgressDialog("")
             }else{
                 hideProgressDialog()
             }
         }
    }

    private fun observeMoviesData() {
        viewModel.moviesList.observe(this){
            moviesItem.addAll(it)
            moviesListAdapter.setSearchResultsItemList(moviesItem)
        }
    }

    private fun observeErrors() {
        viewModel.errorEvent.observe(this){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView(){
        val llayoutManager = GridLayoutManager(context,2)
        binding?.recyMovies?.recyclerview?.layoutManager = llayoutManager
        binding?.recyMovies?.recyclerview?.addItemDecoration(EqualSpacing(16,context))
        moviesListAdapter = MoviesListAdapter(moviesItem)
        binding?.recyMovies?.recyclerview?.adapter = moviesListAdapter
        moviesListAdapter.setOnItemClickListener(object :MoviesListAdapter.OnItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                val movie = moviesListAdapter.searchResultsItem[position]
                val data = Bundle()
                data.putSerializable("item",movie)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, data)
            }
        })

        binding?.recyMovies?.recyclerview?.addOnScrollListener(object : RecyclerViewPaginator(
            binding?.recyMovies?.recyclerview!!
        ) {
            override val isLastPage: Boolean
                get() = false

            override fun loadMore(page: Long) {
                Log.e(TAG, "loadMore() called with: page = $page")
                viewModel.getMoviesList(page)
            }

            override fun loadFirstData(page: Long) {
                Log.e(TAG, "loadFirstData() called with: page = $page")

            }
        })
    }




}