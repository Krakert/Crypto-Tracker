package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.ImagesEntity
import com.krakert.tracker.data.tracker.entity.database.DBImagesEntity
import com.krakert.tracker.domain.tracker.model.CoinDetails
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

    fun mapDatabaseToDomain(dbImagesEntity: DBImagesEntity): Images {
        return Images(
            small = dbImagesEntity.small.requireNotNull(),
            large = dbImagesEntity.large.requireNotNull(),
            thumb = dbImagesEntity.thumb.requireNotNull(),
        )
    }

    fun mapDomainToDatabase(images: Images): DBImagesEntity {
        return DBImagesEntity(
            small = images.small,
            large = images.large,
            thumb = images.thumb,
        )
    }


}