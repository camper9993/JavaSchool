package com.handtruth.javaschool.ui.simpletest

import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.handtruth.javaschool.R
import com.handtruth.javaschool.baseURL
import com.handtruth.javaschool.data.model.Test
import com.squareup.picasso.Picasso

class SimpleTestAdapter(val activity: FragmentActivity, val viewModel: SimpleTestViewModel) : RecyclerView.Adapter<SimpleTestAdapter.SimpleTestHolder>() {

    private var testsList = mutableListOf<Test>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleTestHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_test_list_item, parent, false)
        return SimpleTestHolder(itemView)
    }


    override fun getItemCount(): Int {
        return testsList.size
    }


    override fun onBindViewHolder(holder: SimpleTestHolder, position: Int) {
        val test = testsList[position]

        holder.check.setOnClickListener {
            holder.setOnClick(test)
        }
        holder.button1.text = test.answer1
        holder.button2.text = test.answer2
        holder.button3.text = test.answer3
        holder.button4.text = test.answer4
        holder.state.setBackgroundColor(test.state)

        if (test.question == "") {
            holder.name.visibility = View.GONE
        } else {
            holder.name.text = test.question
        }

        if (test.img_q != "") {
            holder.img
        } else {
            Picasso.with(activity.baseContext)
                .load(baseURL + "file/image/${test.img_q}")
                .placeholder(R.drawable.download_logo)
                .error(R.drawable.loader_progress)
                .into(holder.img)
        }
    }

    fun setTest(tests: MutableList<Test>) {
        this.testsList = tests
        notifyDataSetChanged()
    }


    inner class SimpleTestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.question_text)
        val img: ImageView = itemView.findViewById(R.id.question_image)
        val check = itemView.findViewById<FloatingActionButton>(R.id.check_answers_button)
        val state = itemView.findViewById<Button>(R.id.state)
        val group: RadioGroup = itemView.findViewById(R.id.radio_group)
        val button1: RadioButton = itemView.findViewById(R.id.first_radio)
        val button2: RadioButton = itemView.findViewById(R.id.second_radio)
        val button3: RadioButton = itemView.findViewById(R.id.third_radio)
        val button4: RadioButton = itemView.findViewById(R.id.fourth_radio)
        val map = mutableMapOf<Int, Int>()


        init {
            map[R.id.first_radio] = 1
            map[R.id.second_radio] = 2
            map[R.id.third_radio] = 3
            map[R.id.fourth_radio] = 4
        }

        fun setOnClick(test: Test) {
            Log.d("SIMPLE_TEST", group.checkedRadioButtonId.toString())
            val index = map[group.checkedRadioButtonId]
            if (test.trueAnsw == index) {
                test.state = Color.rgb(36, 125, 14)
                viewModel.update(test)
            } else {
                test.state = Color.rgb(166, 17, 17)
                viewModel.update(test)
            }
        }
    }
}