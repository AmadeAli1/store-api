package com.amade.storeapi.service

import com.amade.storeapi.model.Item
import com.amade.storeapi.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
) {

    suspend fun insert(item: Item): Item {
        return itemRepository.save(item)
    }

    suspend fun update(item: Item): Item {
        return itemRepository.save(item)
    }

    suspend fun findItem(id: Int): Item? {
        return itemRepository.findById(id)
    }

    suspend fun delete(id: Int): Int {
        return itemRepository.delete(id)
    }

    suspend fun addToWishlist(userId: String, itemId: Int): Int {
        return itemRepository.selectItem(itemId = itemId, userId = userId)
    }

    suspend fun removeFromWishlist(userId: String, itemId: Int): Int {
        return itemRepository.removeSelectItem(itemId = itemId, userId = userId)
    }

    suspend fun findItemsInWishlist(userId: String): Flow<Item> {
        return itemRepository.findWhitlistItems(userId = userId)
    }


}