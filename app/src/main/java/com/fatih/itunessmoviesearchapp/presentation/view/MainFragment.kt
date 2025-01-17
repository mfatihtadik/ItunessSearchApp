package com.fatih.itunessmoviesearchapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.fatih.itunessmoviesearchapp.databinding.FragmentMainBinding
import com.fatih.itunessmoviesearchapp.presentation.adapter.SearchAdapter
import com.fatih.itunessmoviesearchapp.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy { SearchAdapter() }
    var word = "batman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && searchAdapter.itemCount ==0
            searchAdapter.showLoading(loadState.append is LoadState.Loading || loadState.refresh is LoadState.Loading )
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context,2)
        binding.recyclerView.adapter = searchAdapter

        lifecycleScope.launch {
            searchViewModel.loadMovies(word).collectLatest {
                searchAdapter.submitData(it)
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    searchViewModel.loadMovies(searchTerm = query!!).collectLatest {
                        searchAdapter.submitData(it)
                        binding.recyclerView.layoutManager = GridLayoutManager(context,2)
                        binding.recyclerView.adapter = searchAdapter
                        word = query
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        binding.gitFav.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToFavoriteFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}