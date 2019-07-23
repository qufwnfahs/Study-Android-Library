package com.example.android_navigation_example


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class FirstFragment : Fragment(), View.OnClickListener {

    internal lateinit var view : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first, container, false)

        view.button_firstFragment_call_action.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when(v) {
            view.button_firstFragment_call_action -> findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

}
