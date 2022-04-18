package com.amade.storeapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("usuario")
data class User(
    @field:NotBlank @field:NotNull @Id val id: String,
    @field:NotBlank @field:NotNull var name: String,
    @field:Email @field:NotBlank @field:NotNull var email: String,
    var image: String? = null,
)
