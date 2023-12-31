package com.krakert.tracker

import com.krakert.tracker.ui.tracker.formatter.CurrentPriceFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertAll

class PriceFormatterTest {

    private val formatter = CurrentPriceFormatter()

    @Test
    fun currentPriceFormatter_Price_Below_1() {
        assertEquals("0.90447", formatter.map(0.9044717858868634))
    }

    @Test
    fun currentPriceFormatter_Price_Above_1() {
        assertEquals("1.7458", formatter.map(1.7457817858868634))
    }

    @Test
    fun currentPriceFormatter_Price_Above_10() {
        assertEquals("12.431", formatter.map(12.431243776886863))
    }

    @Test
    fun currentPriceFormatter_Price_Above_100() {
        assertAll("price_above_100",
            { assertEquals("38.568,19", formatter.map(38568.18920568336)) },
            { assertEquals("149,99", formatter.map(149.9893872184712)) })
    }
}