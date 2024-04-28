package com.savestudents.features.addMatter.domain

import com.google.firebase.firestore.toObject
import com.savestudents.features.addMatter.data.AddMatterRepository
import com.savestudents.features.addMatter.models.Matter

class GetMatterOptionsUseCase(
    private val addMatterRepository: AddMatterRepository
) {
    suspend operator fun invoke(): Result<List<Matter>> {
        addMatterRepository.handleMatters()
            .onSuccess {
                val matters = it.map { documentSnapshot ->
                    checkNotNull(documentSnapshot.toObject<Matter>())
                }

                return Result.success(matters)
            }

        return Result.failure(Exception("Error fetching matters"))
    }
}
