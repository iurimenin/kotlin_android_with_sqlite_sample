package io.github.iurimenin.habittrainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.single_card.view.*


/**
 * Created by Iuri Menin on 07/12/2017.
 */
class HabitsAdapter(private val habits : List<Habit>) :
        RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    class HabitViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder?, position: Int) {

        holder?.let {
            val habit = habits[position]
            it.view.textViewTitle.text = habit.title
            it.view.textViewDescription.text = habit.description
            it.view.imageViewIcon.setImageBitmap(habit.image)
        }
    }

    override fun getItemCount(): Int = habits.size

}