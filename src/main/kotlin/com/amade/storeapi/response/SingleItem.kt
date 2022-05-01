package com.amade.storeapi.response

import com.amade.storeapi.model.Category
import com.amade.storeapi.model.Item
import com.amade.storeapi.model.ItemImage
import kotlinx.coroutines.flow.Flow

data class SingleItem(
    val category: Category?,
    val item: Item?,
    val images: Flow<ItemImage>
)
