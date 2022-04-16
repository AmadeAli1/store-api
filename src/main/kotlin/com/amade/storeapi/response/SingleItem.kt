package com.amade.storeapi.response

import com.amade.storeapi.model.Category
import com.amade.storeapi.model.Company
import com.amade.storeapi.model.Item
import kotlinx.coroutines.flow.Flow

data class SingleItem(
    val category: Category,
    val company: Company,
    val item: Item,
) {

}
