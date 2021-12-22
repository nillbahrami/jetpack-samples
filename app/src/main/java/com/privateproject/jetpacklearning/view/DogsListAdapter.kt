package com.privateproject.jetpacklearning.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.privateproject.jetpacklearning.R
import com.privateproject.jetpacklearning.databinding.ItemDogBinding
import com.privateproject.jetpacklearning.model.DogBread
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBread>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(),
    DogClickListener {

    fun updateDogList(newDogList: List<DogBread>) {
        dogsList.clear()
        dogsList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    override fun getItemCount() = dogsList.size

    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root) {}

    override fun onDogClicked(v: View) {
        val action: ListFragmentDirections.ActionListFragmentToDetailFragment = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogUuid = v.dog_id.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }


}