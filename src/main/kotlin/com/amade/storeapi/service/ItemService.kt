package com.amade.storeapi.service

import com.amade.storeapi.model.Item
import com.amade.storeapi.repository.ItemRepository
import com.amade.storeapi.response.SingleItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
class ItemService(
    private val itemRepository: ItemRepository,
    private val categoryService: CategoryService
) {

    suspend fun insert(item: Item): SingleItem? {
        val category = categoryService.findCategory(item.category)
        val newItem = itemRepository.save(item)

        item.images.forEach {
            itemRepository.insertImages(itemId = newItem.id, image = it)
        }

        val itemImages = itemRepository.findImages(newItem.id)

        return SingleItem(category, newItem, itemImages)
    }

    suspend fun update(item: Item): SingleItem {
        return itemRepository.save(item).let {
            val category = categoryService.findCategory(it.category)
            val itemImages = itemRepository.findImages(it.id)
            SingleItem(category!!, item = it, itemImages)
        }
    }

    suspend fun findItem(id: Int): SingleItem? {
        return itemRepository.findById(id).let {
            if(it==null) {
                throw RuntimeException("Item nao encontrado: ")
            }
            val category = categoryService.findCategory(it.category)
            val itemImages = itemRepository.findImages(it.id)
            SingleItem(category!!, item = it, images = itemImages)
        }
    }

    suspend fun delete(id: Int): Int {
        return itemRepository.delete(id)
    }

    suspend fun addToWishlist(userId: String, itemId: Int): Int {
        return try {
            itemRepository.selectItem(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun removeFromWishlist(userId: String, itemId: Int): Int {
        return try {
            itemRepository.removeSelectItem(itemId = itemId, userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun findItemsInWishlist(userId: String): Flow<SingleItem> {
        return try {
            val items = itemRepository.findWhitlistItems(userId = userId)
            items.map {

                val category = categoryService.findCategory(it.category)
                val itemImages = itemRepository.findImages(it.id)
                SingleItem(category!!, it, images = itemImages)
            }
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    suspend fun findAll(): Flow<SingleItem> {
        return itemRepository.findAll().map {
            val category = categoryService.findCategory(it.category)
            val itemImages = itemRepository.findImages(it.id)
            SingleItem(category!!, item = it, images = itemImages)
        }
    }

}