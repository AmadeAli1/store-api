package com.amade.storeapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("category")
data class Category(
    @field:NotNull @field:NotBlank val name: String,
    @field:NotNull @field:NotBlank @Column("image") var image: String,
) {
    @Id
    var id: Int = 0
}
