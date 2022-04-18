package com.amade.storeapi.model

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("company")
data class Company(
    @field:NotNull @field:NotBlank @field:Length(max = 50, min = 3) var name: String,
    @field:NotNull @field:NotBlank @field:Email var email: String,
    @field:NotNull @field:NotBlank @field:Length(max = 20, min = 5) var contact: String,
    @field:NotNull @field:NotBlank @field:Length(max = 40, min = 5) var region: String,
    @field:NotNull @field:NotBlank @Column("imagelogo") var image: String? = null,
) {
    @Id
    var id: Int = 0
}
