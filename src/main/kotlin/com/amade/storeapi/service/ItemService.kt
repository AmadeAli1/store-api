package com.amade.storeapi.service

import com.amade.storeapi.model.Item
import com.amade.storeapi.repository.ItemRepository
import com.amade.storeapi.response.SingleItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
class ItemService(
    private val itemRepository: ItemRepository,
    private val categoryService: CategoryService,
) {
    suspend fun insert(item: Item): SingleItem? {
        println("in")
        val newItem: Deferred<Item> = runBlocking {
            async {
                itemRepository.save(item)
            }
        }

        item.images.forEach {
            itemRepository.inserir_imagem(itemId = newItem.await().id, image = it)
        }
        println("Out")
        return mapper(item = newItem.await())
    }

    suspend fun update(item: Item): SingleItem {
        return mapper(item = itemRepository.save(item))
    }

    suspend fun findItem(id: Int): SingleItem? {
        return itemRepository.findById(id).let { item ->
            if (item == null) {
                throw RuntimeException("Item nao encontrado: ")
            }
            mapper(item = item)
        }
    }

    suspend fun delete(id: Int): Int {
        return itemRepository.remover_item(id)
    }

    suspend fun addToWishlist(userId: String, itemId: Int): Int {
        return try {
            itemRepository.adicionar_na_lista_de_desejos(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun removeFromWishlist(userId: String, itemId: Int): Int {
        return try {
            itemRepository.remover_da_lista_de_desejos(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun findItemsInWishlist(userId: String): Flow<SingleItem> {
        return try {
            val items = itemRepository.obter_todos_items_da_lista_de_desejos_do_usuario(userId = userId)
            items.map { mapper(item = it) }
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun findAll(): Flow<SingleItem> {
        return withContext(Dispatchers.Default) {
            itemRepository.findAll().map { mapper(item = it) }
        }
    }

    private suspend fun mapper(item: Item): SingleItem {
        val category = categoryService.findCategory(item.category)
        val itemImages = itemRepository.obter_todas_imagens(item.id)
        val images = itemImages.map { it.image }.toList()
        item.images = images
        return SingleItem(category!!, item = item)
    }

    suspend fun addToCart(userId: String, itemId: Int): Int {
        return try {
            itemRepository.adicionar_no_carrinho_de_compras(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun removeFromCart(userId: String, itemId: Int): Int {
        return try {
            itemRepository.remover_do_carrinho_de_compras(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun findItemsInCart(userId: String): Flow<SingleItem> {
        return try {
            val items = itemRepository.obter_todos_items_do_usuario(userId = userId)
            items.map { mapper(item = it) }
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun search(name: String): Flow<SingleItem> {
        return withContext(Dispatchers.Default) {
            itemRepository.pesquisar(query = name).map { mapper(item = it) }
        }
    }

    suspend fun existsWishlist(userId: String, itemId: Int): Boolean {
        return itemRepository.verificar_existencia_do_item_na_lista_de_desejos(userId, itemId)
    }

    suspend fun existsCart(userId: String, itemId: Int): Boolean {
        return itemRepository.verificar_existencia_do_item_no_carrinho_de_compras(userId, itemId)
    }

    suspend fun addLike(userId: String, itemId: Int): Int {
        return itemRepository.adicionar_like_ao_item(itemId).let {
            if (it == 0) {
                return@let 0
            }
            itemRepository.adicionar_like_do_usuario(userId, itemId)
        }
    }

    suspend fun removeLike(userId: String, itemId: Int): Int {
        return itemRepository.remover_like_do_item(itemId).let {
            if (it == 0) {
                return@let 0
            }
            itemRepository.remover_like_do_usuario(userId, itemId)
        }
    }

    suspend fun verificar_existencia_do_like(
        userId: String, itemId: Int,
    ): Boolean {
        return itemRepository.verificar_existencia_de_like_do_usuario(userId, itemId)
    }


}