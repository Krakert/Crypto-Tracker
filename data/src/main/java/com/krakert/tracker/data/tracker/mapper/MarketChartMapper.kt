package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.MarketChart
import com.krakert.tracker.domain.tracker.model.MarketChartItem
import javax.inject.Inject

class MarketChartMapper @Inject constructor() {

    fun map(entity: MarketChartEntity): MarketChart {
        val dataChart = arrayListOf<MarketChartItem>()
        // Filter out null values in the inner lists and convert to List<List<Double>>.
        val filteredResults = entity.prices.filterNotNull().map { innerList ->
            innerList.filterNotNull()
        }

        filteredResults.forEach { datapoint ->
            dataChart.add(
                MarketChartItem(
                    price = datapoint[1].toFloat(),
                    timestamp = datapoint[0].toLong()
                )
            )

        }
        return MarketChart(
            result = dataChart
        )
    }
}
