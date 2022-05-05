package com.amade.storeapi.model.constantes

enum class ItemCategory {
    ANY,
    TSHIRT,
    SHOES;

    companion object {
        fun get(name: String): String? {
            return ItemGenre.values().find { i -> i.name.equals(name, ignoreCase = true) }?.name
        }
    }

}