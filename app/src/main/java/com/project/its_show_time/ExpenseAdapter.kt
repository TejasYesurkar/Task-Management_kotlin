package com.project.its_show_time

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.project.roomdbkotlin.db.Note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.roomdbkotlin.db.Expense
import kotlin.coroutines.coroutineContext

class ExpenseAdapter(
    private val onItemClickListener: (text: String, note: Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.expense_item, parent,
            false
        )
        return ExpenseHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        with(getItem(position)) {
            holder.tvTitle.text = reason
            holder.tvDescription.text = date
            holder.tvAmout.text = "₹ "+amount.toString()

            holder.tvTitle.setOnClickListener {
                try {
//                (holder.itemView.context as HomeActivity).clickAdapater(holder.textViewLink.text as String)
                (holder.itemView.context  as  SideNavigationActivity).clickAdapater(holder.tvTitle.text as String)
                    Log.d("TAGG","Try"+holder.tvTitle.text)
                } catch (e: Exception) {
                    Log.d(">>",e.toString())
                }
            }
        }

    }

    fun getNoteAt(position: Int) = getItem(position)

    inner class ExpenseHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val tvTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val tvDescription: TextView = itemView.findViewById(R.id.text_view_description)
        val tvPriority: TextView = itemView.findViewById(R.id.text_view_priority)
        val tvAmout: TextView = itemView.findViewById(R.id.textViewSubtext)
        val imUpdate: ImageButton = itemView.findViewById(R.id.ib_edit)

        init {

            itemView.setOnClickListener {
//                    (itemView.context  as  SideNavigationActivity).clickAdapater(getItem(adapterPosition).docLink)

            }

            imUpdate.setOnClickListener {
                if(adapterPosition != NO_POSITION)
                    onItemClickListener("edit",getItem(adapterPosition))
            }
//            imDelete.setOnClickListener {
//                if (position != NO_POSITION)
//                    onItemClickListener("delete", getItem(position))
//            }
        }

    }

}


private val diffCallback = object : DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense) =
        oldItem.type == newItem.type
}