package com.amade.storeapi.service

import com.amade.storeapi.model.Item
import com.amade.storeapi.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
) {

    suspend fun insert(item: Item): Item {
        return itemRepository.save(item)
    }

    suspend fun update(item: Item): Int {
        val status = itemRepository.update(id = item.id, name = item.name, image = item.image)
        if (status==1){

        }
        return status
    }

    suspend fun findItem(id: Int): Item? {
        return itemRepository.findById(id)
    }

}