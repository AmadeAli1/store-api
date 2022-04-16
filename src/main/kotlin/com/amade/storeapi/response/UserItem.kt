package com.amade.storeapi.response

import com.amade.storeapi.model.Item
import com.amade.storeapi.model.User

data class UserItem(
    val user: User,
    val items: List<Item>,
)
