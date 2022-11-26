package com.project.its_show_time

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

class NoteAdapter(
    private val onItemClickListener: (text: String, note: Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item, parent,
            false
        )
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        with(getItem(position)) {
            holder.tvTitle.text = title
            holder.tvDescription.text = docLink
            holder.tvPriority.text = docLink
        }
    }

    fun getNoteAt(position: Int) = getItem(position)

    inner class NoteHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val tvTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val tvDescription: TextView = itemView.findViewById(R.id.text_view_description)
        val tvPriority: TextView = itemView.findViewById(R.id.text_view_priority)
        val imDelete: ImageButton = itemView.findViewById(R.id.ib_delete)
        val imUpdate: ImageButton = itemView.findViewById(R.id.ib_edit)

        init {
            imUpdate.setOnClickListener {
                if(adapterPosition != NO_POSITION)
                    onItemClickListener("edit",getItem(adapterPosition))
            }
            imDelete.setOnClickListener {
                if (position != NO_POSITION)
                    onItemClickListener("delete", getItem(position))
            }
        }

    }

}


private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) =
        oldItem.title == newItem.title
                && oldItem.description == newItem.description
                && oldItem.priority == newItem.priority
}