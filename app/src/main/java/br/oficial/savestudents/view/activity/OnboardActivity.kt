package br.oficial.savestudents.view.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.oficial.savestudents.adapter.CarouselRVAdapter
import br.oficial.savestudents.databinding.ActivityOnboardBinding
import com.example.data_transfer.model.contract.CarouselRVContract


class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }

        val demoData = arrayListOf(
            "Curabitur sit amet rutrum enim, sit amet commodo urna. Nullam nec nisl eget purus vulputate ultrices nec sit amet est. Sed sodales maximus risus sit amet placerat.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet lectus a mi lobortis iaculis. Mauris odio tortor, accumsan vel gravida sit amet, malesuada a tortor.",
            "Praesent efficitur eleifend eros quis elementum. Vivamus eget nunc ante. Sed sed sodales libero. Nam ipsum lorem, consequat at ipsum sit amet, tempor vulputate nibh.",
            "Aliquam sodales imperdiet augue at consectetur. Suspendisse dui mauris, tincidunt non auctor quis, facilisis et tellus.",
            "Ut non tincidunt neque, et sodales ligula. Quisque interdum in dui sit amet sagittis. Curabitur erat magna, maximus quis libero quis, dapibus eleifend orci."
        )

        binding.viewPager.setLayoutParams(
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        binding.viewPager.adapter = CarouselRVAdapter(demoData, contract)
    }

    val contract = object : CarouselRVContract {
        override fun clickNextButtonListener(position: Int) {
            binding.viewPager.setCurrentItem(position + 1, true)
        }

        override fun clickFinishButtonListener() {
            TODO("Not yet implemented")
        }
    }
}