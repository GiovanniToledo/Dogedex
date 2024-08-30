package com.example.dogedex.doglist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogedex.R
import com.example.dogedex.databinding.DogListItemBinding
import com.example.dogedex.domain.Dog
import com.example.dogedex.loadUrlImage
import com.example.dogedex.loadUrlImageWithBlur
import jp.wasabeef.glide.transformations.BlurTransformation

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener: ((Dog) -> Unit)? = null

    fun setOnLongItemClickListener(onLongItemClickListener: (Dog) -> Unit) {
        this.onLongItemClickListener = onLongItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)
    }

    inner class DogViewHolder(val binding: DogListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dog: Dog) {
            with(binding) {
                dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }
                dogListItemLayout.setOnLongClickListener {
                    onLongItemClickListener?.invoke(dog)
                    true
                }
                if (!dog.inCollection) {
                    tvDogIndex.isVisible = true
                    tvDogIndex.text = dog.index.toString()
                    dogListItemLayout.background = ContextCompat.getDrawable(
                        dogImage.context,
                        R.drawable.dog_list_item_null_background
                    )
                    dogImage.loadUrlImageWithBlur(dog.imageUrl)
                } else {
                    dogImage.loadUrlImage(dog.imageUrl)
                }
            }
        }
    }
}