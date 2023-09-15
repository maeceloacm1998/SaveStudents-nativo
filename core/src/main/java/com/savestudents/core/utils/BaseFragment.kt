package com.savestudents.core.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<T : ViewBinding, R>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    var parentActivity: R? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = activity as R
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

inline fun <reified T> Fragment.arguments(
    key: String,
): Lazy<T> = lazy {
    val value = arguments?.get(key)
    if (value is T) {
        value
    } else {
        throw IllegalArgumentException("Couldn`t find argument for key $key of type ${T::class.java.canonicalName}")
    }
}