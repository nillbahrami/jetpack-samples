package com.privateproject.jetpacklearning.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.privateproject.jetpacklearning.R
import com.privateproject.jetpacklearning.databinding.FragmentDetailBinding
import com.privateproject.jetpacklearning.model.DogPalette
import com.privateproject.jetpacklearning.viewModel.DetailViewModel


class DetailFragment : Fragment() {

    private var dogUuid = 0

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var  binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //?.let check mikone null nabashe
        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        detailViewModel.fetch(dogUuid)

        observeViewModel()

    }

    private fun observeViewModel() {
        detailViewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {
                binding.dog = dog

                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val color = palette?.vibrantSwatch?.rgb ?: 0
                            val myPalette = DogPalette(color)
                            binding.palette = myPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
    }

}