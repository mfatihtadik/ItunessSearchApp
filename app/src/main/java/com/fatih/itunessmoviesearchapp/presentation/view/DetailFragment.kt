package com.fatih.itunessmoviesearchapp.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fatih.itunessmoviesearchapp.data.local.FavDao
import com.fatih.itunessmoviesearchapp.data.local.FavFilmsDatabase
import com.fatih.itunessmoviesearchapp.databinding.FragmentDetailBinding
import com.fatih.itunessmoviesearchapp.presentation.Util.downloadFromUrl
import com.fatih.itunessmoviesearchapp.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private var simgUrl = ""
    private var filmAdi = ""
    private var desc = ""
    private var link = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            simgUrl = DetailFragmentArgs.fromBundle(it).imgUrl
            filmAdi = DetailFragmentArgs.fromBundle(it).filmAdi
            desc = DetailFragmentArgs.fromBundle(it).filmDescription
            link = DetailFragmentArgs.fromBundle(it).filmUrl
        }
        binding.filmDetailName.text = filmAdi
        binding.filmDescription.text = desc
        binding.imageViewDetail.downloadFromUrl(simgUrl)
        binding.kaydetFilm.setOnClickListener {
            lifecycleScope.launch {
                searchViewModel.storeInSql(filmAdi,simgUrl,desc)
            }
            Toast.makeText(context,"Film kaydedildi", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFragman.setOnClickListener {
            openUrl(link)
        }
    }
    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}