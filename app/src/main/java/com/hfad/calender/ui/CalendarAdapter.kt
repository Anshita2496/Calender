package com.hfad.calender.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.hfad.calender.R
import kotlinx.android.synthetic.main.calendar_cell.view.*
import java.time.LocalDate
import java.util.ArrayList


class CalendarAdapter(
    private var dateList: ArrayList<String>,
    private val clickListener: DateClickListener,
    private val currentDate: String,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    val bool = BooleanArray(dateList.size)
    private val itemViewList = ArrayList<View>()
    private var selectedDate: LocalDate? = null
    private var curDate: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        itemViewList.add(view)
        return CalendarViewHolder(view, clickListener, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dateList[position]
        holder.setData(date)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class CalendarViewHolder(
        private val view: View,
        private val clickListener: DateClickListener,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(view) {

        internal fun setData(date: String) {
            view.apply {

                cellDayText.text = date

                //yyyy-MM-dd
                var today = currentDate.substring(0, currentDate.length - 2)
                today += date


                selectedDate = LocalDate.now()
                curDate = selectedDate.toString().split("-")

                val ar = currentDate.split("-")

                if (curDate!![2] == dateList[adapterPosition] && curDate!![0] == ar[0] && curDate!![1] == ar[1]) {
                    bool[adapterPosition] = true
                    view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                    view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                if (view.cellDayText.text.toString() != "") {

                    rlDate.setOnClickListener {
                        if (bool[adapterPosition]) {
                            if (date == curDate!![2]) {
                                view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                                view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                            }
//                        rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                        } else {
                            check()
                            if (date == curDate!![2]) {
                                view.rlDate.setBackgroundResource(R.drawable.current_day_bg)
                                view.cellDayText.setTextColor(Color.parseColor("#FFFFFFFF"))
                            } else {
                                rlDate.setBackgroundResource(R.drawable.selected_date_bg)
                            }
                            bool[adapterPosition] = true
                        }
                        clickListener.onDateClicked(date, adapterPosition, today)
                    }
                }
            }
        }

        private fun check() {
            for (i in 0 until dateList.size) {
                if (bool[i] && i != adapterPosition) {
                    if (itemViewList[i].cellDayText.text.toString() == curDate!![2]) {
                        itemViewList[i].cellDayText.setTextColor(Color.parseColor("#527FF3"))
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    } else {
                        itemViewList[i].rlDate.setBackgroundResource(0)
                    }
                    bool[i] = false
//                    itemViewList[i].labourLayout.visibility = View.GONE
                }
            }

        }

    }
}

interface DateClickListener {
    fun onDateClicked(date: String, position: Int, today: String)
}
