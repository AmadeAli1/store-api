package com.amade.storeapi.service

import com.amade.storeapi.model.Item
import com.amade.storeapi.repository.ItemRepository
import com.amade.storeapi.response.ResponseItem
import com.amade.storeapi.response.SingleItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val categoryService: CategoryService,
    private val companyService: CompanyService,
) {

    suspend fun insert(item: Item): SingleItem? {
        val category = categoryService.findCategory(id = item.categoryId)
        val company = companyService.findCompany(id = item.companyId)

        if (category != null && company != null) {
            val newItem = itemRepository.save(item)
            return SingleItem(category, company, newItem)
        }

        return null
    }

    suspend fun update(item: Item): SingleItem {
        return itemRepository.save(item).let {
            val company = companyService.findCompany(it.companyId)
            val category = categoryService.findCategory(it.categoryId)
            SingleItem(category!!, company!!, item = it)
        }
    }

    suspend fun findItem(id: Int): SingleItem? {
        return itemRepository.findById(id).let {
            val company = companyService.findCompany(it!!.companyId)
            val category = categoryService.findCategory(it.categoryId)
            SingleItem(category!!, company!!, item = it)
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

    suspend fun findItemsInWishlist(userId: String): Flow<Item> {
        return try {
            itemRepository.findWhitlistItems(userId = userId)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    @OptIn(FlowPreview::class)
    suspend fun findAll(): Flow<ResponseItem> {
        return itemRepository.findAll().flatMapConcat {
            val company = companyService.findCompany(it.companyId)
            val category = categoryService.findCategory(it.categoryId)
            flowOf(ResponseItem(category!!, company!!, flowOf(it)))
        }
    }

}