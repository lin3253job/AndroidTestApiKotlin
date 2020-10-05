package com.example.androidtestapikotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtestapikotlin.model.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.coroutines.*
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment() {

    private var colorData: String? = null
    var jsonArray: JSONArray? = null
    var colorList = mutableListOf<Color>()
    private val scope = MainScope()
    private lateinit var colorAdapter: ColorAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //開執行緒 抓data
        scope.launch {
            colorData =
                CommonTask().getRemotoData("https://jsonplaceholder.typicode.com/photos")

            val gson = Gson()
            val itemType = object : TypeToken<List<Color>>() {}.type
            colorList =
                (gson.fromJson<List<Color>>(colorData, itemType) as List<Color>).toMutableList()
            Log.e("colorData=", "" + colorData)
            rvColors.adapter = ColorAdapter(colorList, scope)
        }

        rvColors.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)


    }

}


//自定義Adapter
private class ColorAdapter(
    private val mData: MutableList<Color>,
    private val scope: CoroutineScope
) :
    RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)



        return MyViewHolder(v, scope)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val color = mData[position]

        holder.itemView.tvId.text = color.id.toString()
        holder.itemView.tvTitle.text = color.title
        holder.ivColor.setImageResource(R.drawable.ic_launcher_foreground)
        holder.loadImage(color.thumbnailUrl)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("color", color)
            Navigation.findNavController(it)
                .navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }

    }

    override fun getItemCount(): Int {
        Log.e("數量=", "" + mData.size)
        return mData.size
    }


    class MyViewHolder(v: View, private val scope: CoroutineScope) : RecyclerView.ViewHolder(v) {

        var job: Job? = null

        val tvId: TextView = itemView.tvId
        val tvTitle: TextView = itemView.tvTitle
        val ivColor: ImageView = itemView.ivColor


        fun loadImage(url: String) {
            job?.cancel()
            job = scope.launch {
                val bitmap = CommonTask().getRemoteImage(url)
                ivColor.setImageBitmap(bitmap)
            }
        }
    }

}



