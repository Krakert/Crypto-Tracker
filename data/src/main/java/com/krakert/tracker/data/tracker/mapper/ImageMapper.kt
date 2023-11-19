package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.ImagesEntity
import com.krakert.tracker.domain.tracker.model.Images
import javax.inject.Inject

class ImageMapper @Inject constructor(

) {
    fun mapApiToDomain(imagesEntity: ImagesEntity): Images {
        return Images(
            small = imagesEntity.small.requireNotNull(),
            large = imagesEntity.large.requireNotNull(),
            thumb = imagesEntity.thumb.requireNotNull(),
        )
    }
}
