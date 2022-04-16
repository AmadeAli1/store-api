package com.amade.storeapi.controller

import com.amade.storeapi.model.Item
import com.amade.storeapi.response.ResponseItem
import com.amade.storeapi.response.SingleItem
import com.amade.storeapi.service.ItemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/items")
@RestController
class ItemController(
    private val itemService: ItemService,
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    suspend fun findAll(): Flow<ResponseItem> {
        return withContext(Dispatchers.Default) {
            itemService.findAll()
        }
    }

    @GetMapping("/{id}")
    suspend fun findItem(@PathVariable("id") id: Int): ResponseEntity<SingleItem> {
        return withContext(Dispatchers.IO) {
            val item = itemService.findItem(id)
            if (item != null) {
                return@withContext ResponseEntity(item, HttpStatus.FOUND)
            }
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    suspend fun insert( @RequestBody item: Item): ResponseEntity<SingleItem> {
        return withContext(Dispatchers.IO) {
            ResponseEntity(itemService.insert(item), HttpStatus.CREATED)
        }
    }

}