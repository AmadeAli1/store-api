package com.amade.storeapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("category")
data class Category(
    @NotNull @NotBlank val name: String,
) {
    @Id
    var id: Int = 0
}
