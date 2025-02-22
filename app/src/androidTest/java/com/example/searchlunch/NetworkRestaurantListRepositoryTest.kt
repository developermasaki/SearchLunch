package com.example.searchlunch

import com.example.searchlunch.data.remote.NetworkRestaurantListRepository
import com.example.searchlunch.fake.FakeDataSource
import com.example.searchlunch.fake.FakeRestaurantListApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkRestaurantListRepositoryTest {
    @Test
    fun networkRestaurantListRepository_getRestaurantList_verifyRestaurantList() = runTest {
        val repository = NetworkRestaurantListRepository(
            restaurantListApiService = FakeRestaurantListApiService()
        )
        assertEquals(FakeDataSource.searchRestaurantList, repository.getRestaurantList())
    }
}