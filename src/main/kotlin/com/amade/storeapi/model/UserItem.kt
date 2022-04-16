package com.amade.storeapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("usuarioitem")
data class UserItem(
    @NotNull @NotBlank @Id val id: Int,
    @NotNull @NotBlank val itemId: Int,
    @NotNull @NotBlank val userId: Int,
    @NotNull @NotBlank var status: String,
)
