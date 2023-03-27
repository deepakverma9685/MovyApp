package com.example.moviez.data.models

import com.google.gson.annotations.SerializedName

data class ErrorsModel(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("errors")
	val errors: List<String?>? = null
)
