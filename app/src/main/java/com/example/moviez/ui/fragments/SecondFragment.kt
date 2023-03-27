package com.example.moviez.ui.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviez.R
import com.example.moviez.base.BaseFragment
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_second
    }

    override fun initViews() {
        val uri = arguments?.getSerializable("item") as MoviesItem
        Log.e("TAG", "initViews: ${uri.title}" )

        binding?.buttonSecond?.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun observeViewModel() {
    }

}