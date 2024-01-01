package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.CoinOverview
import javax.inject.Inject

class OverviewMapper @Inject constructor(
    private val overviewItemMapper: OverviewItemMapper,
) {
    fun map(
        tileCoin: String,
        currency: String,
        pricesCoinsEntity: Map<String, Map<String, Double>>,
        favoriteCoins: List<FavoriteCoinEntity>,
        listMarketChartEntity: List<MarketChartEntity>,
    ): CoinOverview {
        return CoinOverview(
            result = favoriteCoins.map { coin ->
                overviewItemMapper.map(
                    name = coin.name,
                    id = coin.id,
                    currentPrice = pricesCoinsEntity[coin.id]?.get(currency),
                    marketChart = listMarketChartEntity[favoriteCoins.indexOf(coin)],
                    currency = currency
                )
            },
            tileCoin = tileCoin
        )
    }
}
