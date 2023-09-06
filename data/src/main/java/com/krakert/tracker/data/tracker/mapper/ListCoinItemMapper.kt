package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.data.tracker.entity.TEST
import com.krakert.tracker.data.tracker.entity.TESTItem
import com.krakert.tracker.data.tracker.entity.database.DBListCoinItemEntity
import com.krakert.tracker.domain.tracker.model.ListCoinsItem
import javax.inject.Inject

class ListCoinItemMapper @Inject constructor(

) {
    fun mapApiToDomain(entity: ListCoinsItemEntity?): ListCoinsItem {
        return ListCoinsItem(
            id = entity?.id.requireNotNull(),
            imageUrl = entity?.image.requireNotNull(),
            name = entity?.name.requireNotNull(),
        )
    }

    fun mapDatabaseToDomain(entity: DBListCoinItemEntity): ListCoinsItem {
        return ListCoinsItem(
            id = entity.id.requireNotNull(),
            imageUrl = entity.imageUrl.requireNotNull(),
            name = entity.name.requireNotNull(),
        )
    }
}
