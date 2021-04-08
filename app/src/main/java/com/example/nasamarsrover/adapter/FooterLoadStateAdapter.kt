package com.example.nasamarsrover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamarsrover.R
import kotlinx.android.synthetic.main.layout_footer_load_state.view.*

class FooterLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_footer_load_state, parent, false), retry)


    class LoadStateViewHolder(private val view: View, private val retry: () -> Unit) : RecyclerView.ViewHolder(view) {
        init {
            view.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState){
           with(view) {
                loadStateProgresBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMessage.isVisible = loadState is LoadState.Error
                errorMessage.text = (loadState as? LoadState.Error)?.error?.localizedMessage
            }
        }
    }
}