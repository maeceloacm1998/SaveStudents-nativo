package com.savestudents.features.addMatter.domain

import com.savestudents.features.addMatter.data.AddMatterRepository
import com.savestudents.features.addMatter.models.Matter

class CreateMatterOptionsUseCase(
    private val addMaterRepository: AddMatterRepository
) {
    suspend operator fun invoke(
        period: String,
        matterName: String
    ): Result<Boolean> {
        val matter = Matter(
            id = generateId(),
            period = period,
            matterName = matterName,
        )
        addMaterRepository.handleSaveMatter(matter)
            .onSuccess {
                return Result.success(true)
            }

        return Result.failure(Exception("Error creating matter options"))
    }

    private fun generateId(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }
}