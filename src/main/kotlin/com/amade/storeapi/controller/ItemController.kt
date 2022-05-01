package com.amade.storeapi.controller

import com.amade.storeapi.model.Constants.SUCCESS
import com.amade.storeapi.model.Item
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
    suspend fun findAll(): Flow<SingleItem> {
        return withContext(Dispatchers.Default) {
            itemService.findAll()
        }
    }

    @GetMapping("/{id}")
    suspend fun findItem(@PathVariable("id") id: Int): ResponseEntity<SingleItem> {
        return withContext(Dispatchers.IO) {
            val item = itemService.findItem(id)
            if (item != null) {
                return@withContext ResponseEntity(item, HttpStatus.OK)
            }
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    suspend fun insert(@Valid @RequestBody item: Item): ResponseEntity<Any> {
        return withContext(Dispatchers.IO) {
            if (item.images.isEmpty()) return@withContext ResponseEntity("Requer uma ou mais imagens!!",HttpStatus.BAD_REQUEST)
            val result = itemService.insert(item)
            if (result != null) return@withContext ResponseEntity(result, HttpStatus.CREATED)
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping
    suspend fun update(@Valid @RequestBody item: Item): ResponseEntity<SingleItem> {
        return withContext(Dispatchers.IO) {
            ResponseEntity(itemService.update(item), HttpStatus.OK)
        }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: Int): ResponseEntity<String> {
        return withContext(Dispatchers.IO) {
            val status = itemService.delete(id)
            if (status == SUCCESS) {
                return@withContext ResponseEntity("Item removido com sucesso", HttpStatus.OK)
            }
            ResponseEntity("Erro ao remover o Item", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/wishlist")
    suspend fun addToWishlist(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<String> {
        return withContext(Dispatchers.IO) {
            val status = itemService.addToWishlist(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity("Item adicionado na lista de desejos", HttpStatus.CREATED)
            }
            ResponseEntity("Erro ao adicionar o item na lista de desejos", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @DeleteMapping("/wishlist")
    suspend fun removeFromWishlist(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<String> {
        return withContext(Dispatchers.IO) {
            val status = itemService.removeFromWishlist(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity("Item removido na lista de desejos", HttpStatus.OK)
            }
            ResponseEntity("Erro ao adicionar ao remover o item na lista de desejos", HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/wishlist")
    suspend fun findItemsInWishlist(
        @RequestParam(name = "userId", required = true) id: String,
    ): Flow<SingleItem> {
        return withContext(Dispatchers.IO) {
            itemService.findItemsInWishlist(userId = id)
        }
    }


}