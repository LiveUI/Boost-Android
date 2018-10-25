package io.liveui.boost.util.gson

import android.net.Uri
import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type
import java.text.ParseException

class UriDeserializer : JsonDeserializer<Uri>, JsonSerializer<Uri> {

    override fun serialize(uri: Uri, arg1: Type, arg2: JsonSerializationContext): JsonElement {
        return JsonPrimitive(uri.toString())
    }

    override fun deserialize(j: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Uri? {
        val fieldString: String? = j.asString
            try {
                return Uri.parse(fieldString)
            } catch (pe: ParseException) {
                Timber.w("Failed to parse date with format $fieldString")
            }
        return null
    }
}