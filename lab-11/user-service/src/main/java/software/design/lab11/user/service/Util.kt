package software.design.lab11.user.service

import feign.RequestTemplate
import feign.Response
import feign.codec.Decoder
import feign.codec.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.lang.reflect.Type

object Util {

    object KotlinxEncoder : Encoder {
        override fun encode(any: Any, bodyType: Type, template: RequestTemplate) {
            template.body(
                Json.encodeToString(
                    serializer(bodyType),
                    any,
                ),
            )
        }
    }

    object KotlinxDecoder : Decoder {
        override fun decode(response: Response, type: Type): Any {
            return Json.decodeFromString(
                serializer(type),
                response.body().asInputStream()
                    .readAllBytes()
                    .toString(response.charset()),
            )
        }
    }
}
