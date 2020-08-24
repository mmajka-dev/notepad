package com.mmajka.notatnik.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.notatnik.R
import com.mmajka.notatnik.database.note
import kotlinx.android.synthetic.main.single_note_card.view.*

class HomeAdapter(private var allNotes: List<note>, val itemClickListener: OnClickListener): RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val homeCard = inflater.inflate(R.layout.single_note_card, parent, false)
        return HomeViewHolder(homeCard)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val noteBind =allNotes.get(position)
        holder.bind(noteBind, itemClickListener)

    }

}

class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
    val context = itemView.context
    val date = itemView.noteDate
    val title = itemView.noteTitle
    val content = itemView.noteContent

    fun bind(note: note, onClickListener: OnClickListener){
        title.text = note.title
        date.text = note.date
        content.text = note.content

        itemView.setOnClickListener {
            onClickListener.OnItemClick(note, adapterPosition, itemView)
        }

        itemView.setOnLongClickListener {
            onClickListener.OnItemLongClick(note, adapterPosition, itemView)
            return@setOnLongClickListener true
        }

    }
}

interface OnClickListener{
    fun OnItemClick(note: note, position: Int, view: View)
    fun OnItemLongClick(note: note, position: Int, view: View)
}