package software.design.lab9.actors.web.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data  class SearchRsDto @JsonCreator constructor(

    @field:JsonProperty("responses")
    @get:JsonProperty("responses")
    @param:JsonProperty("responses")
    val responses: Map<SearchSystemDto, SystemRsDto>,
) : Message
