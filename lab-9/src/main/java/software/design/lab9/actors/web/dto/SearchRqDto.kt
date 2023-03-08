package software.design.lab9.actors.web.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchRqDto @JsonCreator constructor(

    @field:JsonProperty("text")
    @get:JsonProperty("text")
    @param:JsonProperty("text")
    val text: String,

    @field:JsonProperty("systems")
    @get:JsonProperty("systems")
    @param:JsonProperty("systems")
    val systems: Set<SearchSystemDto> = setOf(),
) : Message
