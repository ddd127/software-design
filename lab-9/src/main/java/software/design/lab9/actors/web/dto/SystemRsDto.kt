package software.design.lab9.actors.web.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SystemRsDto @JsonCreator constructor(

    @field:JsonProperty("system")
    @get:JsonProperty("system")
    @param:JsonProperty("system")
    val system: SearchSystemDto,

    @field:JsonProperty("results")
    @get:JsonProperty("results")
    @param:JsonProperty("results")
    val results: List<String>,
) : Message
