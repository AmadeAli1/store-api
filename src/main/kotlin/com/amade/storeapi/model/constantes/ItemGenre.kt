package com.amade.storeapi.model.constantes

enum class ItemGenre {
    Man,
    Woman;

    companion object {
        fun get(name: String): String? {
            return values().find { i -> i.name.equals(name, ignoreCase = true) }?.name
        }
    }
}