package com.amade.storeapi.model

import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("usuario")
data class User(
    @field:NotBlank @field:NotNull @field:Id var id: String = "",
    @field:NotBlank @field:NotNull var name: String="",
    @field:Email @field:NotBlank @field:NotNull var email: String="",
    var image: String? = null,
)
{
    fun toJson():String{
        val json = JsonMapper().writeValueAsString(this)
        return json!!
    }
}