package com.amade.storeapi.controller

import com.amade.storeapi.model.Item
import com.amade.storeapi.model.constantes.Constants.SUCCESS
import com.amade.storeapi.response.Message
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
        return withContext(Dispatchers.IO) {
            itemService.findAll()
        }
    }

    @GetMapping("/{id}")
    suspend fun findItem(@PathVariable("id") id: Int): ResponseEntity<Any> {
        return withContext(Dispatchers.IO) {
            val item = itemService.findItem(id)
            if (item != null) {
                return@withContext ResponseEntity(item, HttpStatus.OK)
            }
            ResponseEntity(Message(message = "O Item nao foi encontrado", status = HttpStatus.NOT_FOUND.name),
                HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    suspend fun insert(@Valid @RequestBody item: Item): ResponseEntity<Any> {
        return withContext(Dispatchers.IO) {
            if (item.images.isEmpty()) {
                return@withContext ResponseEntity(
                    Message(message = "Requer uma ou mais imagens!!", status = HttpStatus.BAD_REQUEST.name),
                    HttpStatus.BAD_REQUEST)
            }
            val result = itemService.insert(item)
            if (result != null) {
                return@withContext ResponseEntity(result, HttpStatus.CREATED)
            }
            ResponseEntity(Message(message = HttpStatus.BAD_REQUEST.reasonPhrase, status = HttpStatus.BAD_REQUEST.name),
                HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping
    suspend fun update(@Valid @RequestBody item: Item): ResponseEntity<SingleItem> {
        return withContext(Dispatchers.IO) {
            ResponseEntity(itemService.update(item), HttpStatus.OK)
        }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        return withContext(Dispatchers.IO) {
            val status = itemService.delete(id)
            if (status == SUCCESS) {
                return@withContext ResponseEntity(Message(message = "Item removido com sucesso", status = HttpStatus.OK.name),
                    HttpStatus.OK)
            }
            ResponseEntity(Message(message = "Item nao encontrado", status = HttpStatus.NOT_FOUND.name), HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/wishlist")
    suspend fun addToWishlist(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<Message> {
        return withContext(Dispatchers.IO) {
            if (itemService.existsWishlist(userId, itemId)) {
                return@withContext ResponseEntity(Message(message = "O Item ja existe na sua lista de desejos", status = HttpStatus
                    .BAD_REQUEST.name),
                    HttpStatus
                        .BAD_REQUEST)
            }
            val status = itemService.addToWishlist(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity(Message(message = "Item adicionado na lista de desejos",
                    status = HttpStatus.CREATED.name),
                    HttpStatus
                        .CREATED)
            }
            ResponseEntity(Message(message = "Erro ao adicionar o item na lista de desejos", status = HttpStatus.NOT_ACCEPTABLE.name),
                HttpStatus
                    .NOT_ACCEPTABLE)
        }
    }

    @DeleteMapping("/wishlist")
    suspend fun removeFromWishlist(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<Message> {
        return withContext(Dispatchers.IO) {
            val status = itemService.removeFromWishlist(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity(Message(message = "Item removido na lista de desejos"), HttpStatus.OK)
            }
            ResponseEntity(Message(message = "Erro ao adicionar ao remover o item na lista de desejos", status = HttpStatus.NOT_FOUND.name),
                HttpStatus
                    .NOT_FOUND)
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

    @PostMapping("/cart")
    suspend fun addToCart(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<Message> {
        return withContext(Dispatchers.IO) {

            if (itemService.existsCart(userId, itemId)) {
                return@withContext ResponseEntity(
                    Message(message = "O Item ja existe na sua lista de desejos", status = HttpStatus.BAD_REQUEST.name),
                    HttpStatus.BAD_REQUEST)
            }

            val status = itemService.addToCart(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity(
                    Message(message = "Item adicionado na lista de desejos", status = HttpStatus.CREATED.name),
                    HttpStatus.CREATED)
            }
            ResponseEntity(
                Message(message = "Erro ao adicionar o item na lista de desejos, o Item nao existe",
                    status = HttpStatus.NOT_ACCEPTABLE.name),
                HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @DeleteMapping("/cart")
    suspend fun removeFromCart(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<Message> {
        return withContext(Dispatchers.IO) {
            val status = itemService.removeFromCart(userId, itemId)
            if (status == SUCCESS) {
                return@withContext ResponseEntity(Message(message = "Item removido na lista de desejos"), HttpStatus.OK)
            }
            ResponseEntity(
                Message(message = "O item nao existe",
                    status = HttpStatus.NOT_FOUND.name),
                HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/cart")
    suspend fun findItemsInCart(
        @RequestParam(name = "userId", required = true) id: String,
    ): Flow<SingleItem> {
        return withContext(Dispatchers.IO) {
            itemService.findItemsInCart(userId = id)
        }
    }

    @GetMapping("/search")
    suspend fun findItemByName(
        @RequestParam(name = "q", required = true) name: String,
    ): Flow<SingleItem> {
        return withContext(Dispatchers.IO) {
            itemService.search(name = name)
        }
    }

    @PostMapping("/likes")
    suspend fun addLike(
        @RequestParam(name = "userId", required = true) userId: String,
        @RequestParam(name = "itemId", required = true) itemId: Int,
    ): ResponseEntity<Message> {
        return withContext(Dispatchers.IO) {
            val status = itemService.verificar_existencia_do_like(userId, itemId)
            if (status) {
                val removeLike = itemService.removeLike(userId, itemId)
                if (removeLike == 0) {
                    return@withContext message("Ocorreu um erro ao remover o like",
                        HttpStatus.INTERNAL_SERVER_ERROR.name,
                        HttpStatus.INTERNAL_SERVER_ERROR)
                }
                return@withContext message("Like removido com sucesso", HttpStatus.OK.name, HttpStatus.OK)
            }
            val addLike = itemService.addLike(userId, itemId)
            if (addLike == 0) {
                return@withContext message("Ocorreu um erro ao adicionar um like",
                    HttpStatus.INTERNAL_SERVER_ERROR.name,
                    HttpStatus.INTERNAL_SERVER_ERROR)
            }
            message("Like Adicionado com Sucesso", HttpStatus.OK.name, HttpStatus.OK)
        }
    }

    private fun message(msg: String, status: String, code: HttpStatus): ResponseEntity<Message> {
        return ResponseEntity(Message(message = msg, status = status), code)
    }

}