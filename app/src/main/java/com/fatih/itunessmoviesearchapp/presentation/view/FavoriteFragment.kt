package com.fatih.itunessmoviesearchapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatih.itunessmoviesearchapp.R
import com.fatih.itunessmoviesearchapp.databinding.FragmentFavoriteBinding
import com.fatih.itunessmoviesearchapp.presentation.adapter.FavFilmAdapter
import com.fatih.itunessmoviesearchapp.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val favFilmAdapter by lazy { FavFilmAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeToDelete()
        searchViewModel.favFilms.observe(viewLifecycleOwner, Observer {
            favFilmAdapter.differ.submitList(it)
        })
        binding.recyclerView2.adapter = favFilmAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        searchViewModel.fetchAllFavFilms()
    }
    private fun swipeToDelete(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.absoluteAdapterPosition
                    val item = favFilmAdapter.differ.currentList[position]
                    searchViewModel.deleteFilm(item)
                    binding.recyclerView2.adapter = favFilmAdapter
                    binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
            }
        }).attachToRecyclerView(binding.recyclerView2)
    }
}