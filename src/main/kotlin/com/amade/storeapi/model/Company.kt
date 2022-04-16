package com.amade.storeapi.model

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("company")
data class Company(
    @NotNull @NotBlank @Id val id: Int,
    @NotNull @NotBlank @Length(max = 50, min = 3) var name: String,
    @NotNull @NotBlank @Email var email: String,
    @NotNull @NotBlank @Length(max = 20, min = 5) var contact: String,
    @NotNull @NotBlank @Length(max = 40, min = 5) var region: String,
    @NotNull @NotBlank var image: String? = null,
)
