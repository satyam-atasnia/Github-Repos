package com.satyam.githubrepos.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.satyam.githubrepos.R
import com.satyam.githubrepos.data.database.model.Repo
import com.satyam.githubrepos.databinding.RowMainItemBinding
import com.satyam.githubrepos.utils.OnViewClickListener
import javax.inject.Inject

class ReposAdapter @Inject constructor() : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private var list = listOf<Repo?>()
    private lateinit var itemClickListener: OnViewClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowMainItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_main_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: RowMainItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setView(list[position], holder.binding)
    }

    private fun setView(
        repo: Repo?, binding: RowMainItemBinding
    ) {

        binding.tvTitle.text = repo?.name

        binding.tvSubTitle.text = repo?.description

        binding.root.setOnClickListener { itemClickListener.onItemClick(it, repo) }
    }

    /**
     * Set Data
     * */
    fun setData(list: List<Repo?>) {
        this.list = list
        notifyDataSetChanged()
    }

    /**
     * Set Item Click Listener
     * */
    fun setItemClick(itemClickListener: OnViewClickListener) {
        this.itemClickListener = itemClickListener
    }
}