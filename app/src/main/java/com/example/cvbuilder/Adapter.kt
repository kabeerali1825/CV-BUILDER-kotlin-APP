package com.example.cvbuilder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CVAdapter(private val context: Context, private var cvList: List<CVModel>) :
    RecyclerView.Adapter<CVAdapter.CVViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.cv_item_layout, parent, false)
        return CVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CVViewHolder, position: Int) {
        val cv = cvList[position]
        holder.bind(cv)
    }

    // Function to update the list of CVs
    fun updateCVs(newCVList: List<CVModel>) {
        cvList = newCVList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cvList.size
    }

    inner class CVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rollNumberTextView: TextView = itemView.findViewById(R.id.rollNumberTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val cgpaTextView: TextView = itemView.findViewById(R.id.cgpaTextView)
        private val degreeTextView: TextView = itemView.findViewById(R.id.degreeTextView)
        private val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        private val interestsTextView: TextView = itemView.findViewById(R.id.interestsTextView)

        fun bind(cv: CVModel) {
            rollNumberTextView.text = "Roll Number: ${cv.rollNumber}"
            nameTextView.text = "Name: ${cv.name}"
            cgpaTextView.text = "CGPA: ${cv.cgpa}"
            degreeTextView.text = "Degree: ${cv.degree}"
            genderTextView.text = "Gender: ${cv.gender}"
            interestsTextView.text = "Interests: ${cv.interests}"
        }
    }
}
