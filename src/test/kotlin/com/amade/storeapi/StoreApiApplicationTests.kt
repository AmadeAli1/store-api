package com.amade.storeapi

import com.amade.storeapi.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@SpringBootTest
class StoreApiApplicationTests {

    @Test
    suspend fun contextLoads() {
        hello()
    }

    suspend fun hello(){
        WebClient.create("http:localhost:8080/items").get().retrieve().awaitBody<Flow<Item>>().collect {
            println(it)
        }
    }
}
