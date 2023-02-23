package edu.temple.activity4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var textSizeSelector: RecyclerView
    lateinit var textSizeDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSizeSelector = findViewById(R.id.textSizeSelectorRecyclerView)
        textSizeDisplay = findViewById(R.id.textSizeDisplayTextView)
        // Trying to create array of integers that are multiples of 5
        // Verify correctness by examining array values.
        val textSizes = Array(20){(it + 1) * 5}

        for(i in 0 until textSizes.size) {
            Log.d("Array Values", "${textSizes[i]}");
        }

        val callback = {size: Float ->
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("Size",size)
            startActivity(intent)
        };

        textSizeSelector.adapter = TextSizeAdapter(textSizes, callback);
        textSizeSelector.layoutManager = LinearLayoutManager(this)
    };
}

/* Convert to RecyclerView.Adapter */
class TextSizeAdapter(_textSizes: Array<Int>, _callback: (Float)->Unit): RecyclerView.Adapter<TextSizeAdapter.TextSizeViewHolder>() {

    private val sizes = _textSizes;
    private val callback = _callback;

    inner class TextSizeViewHolder(_textSizeView: TextView) : RecyclerView.ViewHolder(_textSizeView) {
        val textSizeView = _textSizeView;

        init {
            textSizeView.setOnClickListener(){
                callback(sizes[adapterPosition].toFloat());
            };
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextSizeViewHolder {
        return TextSizeViewHolder (
            TextView(parent.context)
            .apply{
                this.setPadding(5,20,0,20)
            }
        )
    }
    override fun getItemCount(): Int {
        return sizes.size;
    }

    override fun onBindViewHolder(holder: TextSizeViewHolder, position: Int) {
        holder.textSizeView.text = sizes[position].toString();
        holder.textSizeView.textSize = sizes[position].toFloat();
    }
}