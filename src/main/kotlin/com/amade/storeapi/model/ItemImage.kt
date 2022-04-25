package com.amade.storeapi.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("itemimage")
data class ItemImage(
    @field:NotNull @field:NotBlank @Column("itemid")var itemId: Int,
    @field:NotNull @field:NotBlank @Column("image") var image: String,
)
