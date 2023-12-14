package com.tipiz.berita.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.berita.R
import com.tipiz.berita.data.Result
import com.tipiz.berita.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {

    private var tabName: String? = null
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding


    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "tab_news"
        const val TAB_BOOKMARK = "tab_bookmark"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabName = arguments?.getString(ARG_TAB)

        /**
         *      jika di activity seperti ini
         *      val factory = ViewModelFactory.getInstance(this)
         *      val viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
         *
         */
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteBookmark(news)
            } else {
                viewModel.saveBookmark(news)
            }
        }

        if (tabName == TAB_NEWS) {
            viewModel.getHeadLineNews().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val newsData = result.data
                        newsAdapter.submitList(newsData)
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi Kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        } else if (tabName == TAB_BOOKMARK) {
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkNews ->
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(bookmarkNews)
            }
        }

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}