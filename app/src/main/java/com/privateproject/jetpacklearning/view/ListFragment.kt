package com.privateproject.jetpacklearning.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.privateproject.jetpacklearning.R
import com.privateproject.jetpacklearning.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogListAdapter = DogsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        fab_to_detail.setOnClickListener {
//            val action : ListFragmentDirections.ActionListFragmentToDetailFragment = ListFragmentDirections.actionListFragmentToDetailFragment()
//            action.dogUuid = 5
//            Navigation.findNavController(it).navigate(action)
//        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dog_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }

        refresh_layout.setOnRefreshListener {
            dog_list.visibility = View.GONE
            error.visibility = View.GONE
            loading_view.visibility = View.VISIBLE
            viewModel.refreshByPassCache()
            refresh_layout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.dogs.observe(this, Observer { dogs ->
            dogs?.let {
                dog_list.visibility = View.VISIBLE
                dogListAdapter.updateDogList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(this, Observer { isEror ->
            isEror?.let {
                error.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    error.visibility = View.GONE
                    dog_list.visibility = View.GONE
                }
            }
        })
    }


}