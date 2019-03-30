package com.dhabensky.scopedvm.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.dhabensky.scopedvm.ScopedViewModelProviders
import kotlinx.android.synthetic.main.fragment.*

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
open class ScopedFragment() : Fragment() {

    @SuppressLint("ValidFragment")
    constructor(scope: String?) : this() {
        scope?.let {
            arguments = Bundle().apply{
                putString("SCOPE", it)
            }
        }
    }

    var scope: String? = null
    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = arguments?.getString("SCOPE", null)

        viewModel = viewModelFor(scope)
        viewModel.scope = scope
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text.text = "scope: $scope\n" +
                    "data: ${viewModel.data}"
        activity?.title = javaClass.simpleName
    }

    val fm: FragmentManager
        get() {
            return (activity as AppCompatActivity).supportFragmentManager
        }

    fun replaceFragment(newFragment: Fragment) {
        fm.beginTransaction()
            .replace(R.id.container, newFragment, MainActivity.TAG)
            .addToBackStack(null)
            .commit()
    }

    fun viewModelFor(scope: String?): MyViewModel {
        if (scope == null) {
            return ViewModelProviders
                .of(this)
                .get(MyViewModel::class.java)
        }
        else {
            return ScopedViewModelProviders
                .forScope(this, scope)
                .of(activity!!)
                .get(MyViewModel::class.java)
        }
    }

}
