package com.amade.storeapi.exception

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
data class ErrorMessage(
    val message: String? = null,
    val requestStatus: Int? = null,
    var erro: ErrorAtribute,

    ) {

    data class ErrorAtribute(
        val field: String? = null,
        val message: String? = null,
    )
}