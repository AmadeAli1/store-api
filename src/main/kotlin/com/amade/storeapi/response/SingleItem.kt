package com.amade.storeapi.response

import com.amade.storeapi.model.Category
import com.amade.storeapi.model.Item

data class SingleItem(
    val category: Category?,
    val item: Item?
)
