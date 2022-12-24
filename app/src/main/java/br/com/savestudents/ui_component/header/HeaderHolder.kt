package br.com.savestudents.ui_component.header

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import br.com.savestudents.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass
abstract class HeaderHolder : EpoxyModelWithHolder<HeaderHolder.SectionHolder>() {
    private var clickLogo: Int = 0

    @EpoxyAttribute
    var adminModeOn: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var adminModeOnActiveListener: () -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var joinAdminModeListener: () -> Unit

    override fun bind(holder: SectionHolder) {
        super.bind(holder)

        holder.apply {
            shouldShowAdminMode()
            handleClickJoinAdminMode()
            handleClickAdminModeButton()
        }
    }

    private fun SectionHolder.handleClickAdminModeButton() {
        mAdminModeButton.setOnClickListener {
            adminModeOnActiveListener()
        }
    }

    private fun SectionHolder.handleClickJoinAdminMode() {
        mLogo.setOnClickListener {
            if (!adminModeOn) {
                if (clickLogo < 10) {
                    clickLogo += 1
                    return@setOnClickListener
                }

                if (clickLogo in 10..14) {
                    clickLogo += 1
                    Toast.makeText(
                        it.context,
                        "Faltam ${15 - clickLogo} cliques.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                joinAdminModeListener()
            }
        }
    }

    private fun SectionHolder.shouldShowAdminMode() {
        if (adminModeOn) {
            handleAdminModeActive()
        } else {
            handleAdminModeDisabled()
        }
    }

    private fun SectionHolder.handleAdminModeActive() {
        mAdminModeButton.visibility = View.VISIBLE
        mAdminModeFlag.visibility = View.VISIBLE
    }

    private fun SectionHolder.handleAdminModeDisabled() {
        mAdminModeButton.visibility = View.GONE
        mAdminModeFlag.visibility = View.GONE
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mLogo: ImageView
        lateinit var mAdminModeButton: ImageView
        lateinit var mAdminModeFlag: TextView

        override fun bindView(itemView: View) {
            mLogo = itemView.findViewById(R.id.logo)
            mAdminModeButton = itemView.findViewById(R.id.admin_mode_access)
            mAdminModeFlag = itemView.findViewById(R.id.admin_mode_flag)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.header_home_holder
    }
}