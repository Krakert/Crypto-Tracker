package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.CoinOverviewItem
import javax.inject.Inject

class OverviewMapper @Inject constructor(
    private val marketChartMapper: MarketChartMapper,
    private val overviewItemMapper: OverviewItemMapper,
) {
    fun map(
        tileCoin: String,
        currency: String,
        pricesCoinsEntity: Map<String, Map<String, Double>>,
        favoriteCoins: List<FavoriteCoinEntity>,
        listMarketChartEntity: List<MarketChartEntity>,
    ): CoinOverview {
        val results = favoriteCoins.map { coin ->
            CoinOverviewItem(
                name = coin.name.toString().requireNotNull(),
                id = coin.id.requireNotNull(),
                currentPrice = pricesCoinsEntity[coin.id]?.get(currency).requireNotNull(),
                marketChart = marketChartMapper.map(listMarketChartEntity[favoriteCoins.indexOf(coin)])
                    .requireNotNull()
            )
        }

        return CoinOverview(
            result = results,
            tileCoin = tileCoin
        )
    }
}
