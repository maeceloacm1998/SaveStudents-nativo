package br.oficial.savestudents.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.oficial.savestudents.adapter.CarouselRVAdapter
import br.oficial.savestudents.databinding.ActivityOnboardBinding
import br.oficial.savestudents.utils.OnboardMock
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

        binding.viewPager.adapter = CarouselRVAdapter(OnboardMock.getOnboardItems(), contract)
    }

    val contract = object : CarouselRVContract {
        override fun clickNextButtonListener(position: Int) {
            binding.viewPager.setCurrentItem(position + 1, true)
        }

        override fun clickFinishButtonListener() {
            renderHomeActivity()
        }
    }

    private fun renderHomeActivity() {
        val intent = HomeActivity.newInstance(applicationContext)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}