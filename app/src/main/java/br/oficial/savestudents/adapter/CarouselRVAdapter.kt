package br.oficial.savestudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.oficial.savestudents.R
import com.example.data_transfer.model.OnboardModel
import com.example.data_transfer.model.contract.CarouselRVContract

class CarouselRVAdapter(
    private val carouselDataList: List<OnboardModel>,
    private val contract: CarouselRVContract
) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.carousel_holder, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val item = carouselDataList[position]
        holder.apply {
            clickNextStep(position)
            clickJumpStep()
            setText(item.title, item.description)
            setImage(item.image)
            handleButtonLayout(position)
        }
    }

    private fun CarouselItemViewHolder.handleButtonLayout(position: Int) {
        val button = itemView.findViewById<View>(R.id.button)
        val buttonTitle = itemView.findViewById<TextView>(R.id.button_title)

        if (isLastItem(position)) {
            button.setBackgroundDrawable(
                ContextCompat.getDrawable(button.context, R.drawable.bg_rounded_primary)
            )

            buttonTitle.apply {
                text = context.getString(R.string.finish_step)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        } else {
            button.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    button.context,
                    R.drawable.bg_rounded_blue_light_with_border_primary
                )
            )

            buttonTitle.apply {
                text = context.getString(R.string.next_step)
                setTextColor(ContextCompat.getColor(context, R.color.primary))
            }
        }
    }

    private fun CarouselItemViewHolder.setImage(image: Int) {
        val imageView = itemView.findViewById<ImageView>(R.id.image)
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, image))
    }

    private fun CarouselItemViewHolder.clickJumpStep() {
        val jumpButton = itemView.findViewById<View>(R.id.jump_step)

        jumpButton.setOnClickListener {
            contract.clickFinishButtonListener()
        }
    }

    private fun CarouselItemViewHolder.setText(title: String, description: String) {
        itemView.findViewById<TextView>(R.id.title).text = title
        itemView.findViewById<TextView>(R.id.description).text = description
    }

    private fun CarouselItemViewHolder.clickNextStep(position: Int) {
        val nextStepButton = itemView.findViewById<View>(R.id.button)

        nextStepButton.setOnClickListener {
            if (isLastItem(position)) {
                contract.clickFinishButtonListener()
            }
            contract.clickNextButtonListener(position)
        }
    }

    private fun isLastItem(position: Int): Boolean {
        return position == carouselDataList.size - 1
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}