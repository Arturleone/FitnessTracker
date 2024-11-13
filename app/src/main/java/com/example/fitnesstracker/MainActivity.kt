package com.example.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(){

    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                textStringId = R.string.label_imc,
                DrawableId = R.drawable.baseline_health_and_safety_24,
                color = Color.RED
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                textStringId = R.string.label_TMB,
                DrawableId = R.drawable.baseline_battery_4_bar_24,
                    color = Color.CYAN
            )
        )

        val adapter = MainAdapter(mainItems, object: OnItemClickListener {
            override fun onClick(id: Int) {
                when(id) {
                    1 -> {
                        val intent = Intent(this@MainActivity, IMCActivity::class.java)
                        startActivity(intent)
                    }

                    2-> {
                        // Abrir outra activity
                    }
                }
            }
        })

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }
    fun Trabalhar(view: View) {
        startActivity(Intent(this, IMCActivity::class.java))

    }


    private inner class MainAdapter (
        private val mainItems: List<MainItem>,
        private val onItemClickListener: OnItemClickListener
    ): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)

        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind (item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.DrawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.onClick(item.id)
                }
            }
        }

    }
}