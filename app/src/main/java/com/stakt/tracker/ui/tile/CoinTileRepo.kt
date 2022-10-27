package com.stakt.tracker.ui.tile

data class DataTileCoin(
    val name: String,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val priceCurrent: Double
)

object CoinRepoTile {

//    private val coinGecko: CoinGeckoClient = CoinGeckoClient.create()
//
//    suspend fun getDetails(coinId: String, context: Context): DataTileCoin {
//        val sharedPreference = SharedPreference.sharedPreference(context = context)
//        val currencyString = sharedPreference.Currency?.let { Currency.valueOf(it) }.toString().lowercase(Locale.getDefault())
//        return try {
//            withTimeout(10_000) {
//                val data = coinGecko.getCoinById(id = coinId, marketData = true)
//                DataTileCoin(
//                    name = data.name,
//                    priceCurrent = data.marketData?.currentPrice?.get(currencyString) ?: 0.0,
//                    priceChangePercentage24h = data.marketData?.priceChangePercentage24h ?: 0.0,
//                    priceChangePercentage7d = data.marketData?.priceChangePercentage7d ?: 0.0
//                )
//            }
//
//        } catch (e: Exception) {
//            throw CoinGeckoRepository.CoinGeckoExceptionError("Retrieval of details of the coin was unsuccessful")
//        }
//    }
}
