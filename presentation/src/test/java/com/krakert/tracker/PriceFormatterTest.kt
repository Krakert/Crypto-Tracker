package com.krakert.tracker

import com.krakert.tracker.ui.tracker.formatter.CurrentPriceFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
class PriceFormatterTest {

    private val formatter = CurrentPriceFormatter()
    @Test
    fun currentPriceFormatter_Price_Above_1() {
       assertEquals("37.960,63", formatter.map(37960.63))
    }

    @Test
    fun currentPriceFormatter_Price_Below_1() {
        assertEquals("0.90416", formatter.map(0.9041632))
    }
}