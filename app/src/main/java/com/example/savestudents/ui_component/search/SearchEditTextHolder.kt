package com.example.savestudents.ui_component.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class SearchEditTextHolder : EpoxyModelWithHolder<SearchEditTextHolder.SectionHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickFilterButton: () -> Unit

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
        holder.apply {
            handleClickFilterButton()
            clickButtonCancel()
            handleOnFocusEditText()
        }
    }

    private fun SectionHolder.handleClickFilterButton() {
        mButtonFilterContainer.setOnClickListener {
            clickFilterButton()
        }
    }

    private fun SectionHolder.clickButtonCancel() {
        mButtonCancel.setOnClickListener { view ->
            clearEditText()
            setDefaultBackgroundEditText(view.context)
        }
    }

    private fun SectionHolder.handleOnFocusEditText() {
        mEditText.setOnFocusChangeListener { view, _ ->
            setBackgroundClickEditText(view.context)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun SectionHolder.setDefaultBackgroundEditText(context: Context) {
        mSearchBarContainer.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_blue_light))
        visibleButtonFilter()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun SectionHolder.setBackgroundClickEditText(context: Context) {
        visibleClearEditText()
        mSearchBarContainer.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_blue_light_with_border_primary))
    }

    private fun SectionHolder.clearEditText(){
        mEditText.apply {
            clearFocus()
            text.clear()
            hideKeyboard(context, this)
        }
    }

    private fun SectionHolder.visibleButtonFilter() {
        mButtonCancel.visibility = View.GONE
        mButtonFilterContainer.visibility = View.VISIBLE
        mButtonFilterIcon.visibility = View.VISIBLE
        mButtonFilterText.visibility = View.VISIBLE
    }

    private fun SectionHolder.visibleClearEditText() {
        mButtonCancel.visibility = View.VISIBLE
        mButtonFilterContainer.visibility = View.GONE
        mButtonFilterIcon.visibility = View.GONE
        mButtonFilterText.visibility = View.GONE
    }

    private fun hideKeyboard(context: Context, view: EditText) {
        val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view.windowToken, 0)
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mSearchBarContainer: View
        lateinit var mEditText: EditText
        lateinit var mButtonFilterContainer: View
        lateinit var mButtonFilterIcon: ImageView
        lateinit var mButtonFilterText: TextView
        lateinit var mButtonCancel: TextView

        override fun bindView(itemView: View) {
            mSearchBarContainer = itemView.findViewById(R.id.search_bar_container)
            mEditText = itemView.findViewById(R.id.search_bar)
            mButtonFilterContainer = itemView.findViewById(R.id.filter_button_container)
            mButtonFilterIcon = itemView.findViewById(R.id.filter_button_icon)
            mButtonFilterText = itemView.findViewById(R.id.filter_button_title)
            mButtonCancel = itemView.findViewById(R.id.button_cancel)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.search_bar_holder
    }
}