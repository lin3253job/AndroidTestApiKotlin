package com.example.androidtestapikotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtestapikotlin.model.Color
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class ThirdFragment : Fragment() {
    var colorThrid: Color? = null
    val scope=  MainScope()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorThrid = arguments?.getParcelable("color")
        tvId.text = colorThrid!!.id.toString()
        tvTitle.text = colorThrid!!.title
        scope.launch {
            val bitmap=  CommonTask().getRemoteImage(colorThrid?.url ?: return@launch)
            ivColor.setImageBitmap(bitmap)
        }



    }



}
