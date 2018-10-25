package io.liveui.boost.util.gson

import android.annotation.SuppressLint
import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Calendar>, JsonSerializer<Calendar> {

    companion object {
        @SuppressLint("ConstantLocale")
        val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        @SuppressLint("ConstantLocale")
        private val SIMPLE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val DATE_FORMATS = arrayOf<DateFormat>(DATE_FORMAT, SIMPLE_DATE_FORMAT)
    }

    override fun serialize(cal: Calendar, arg1: Type, arg2: JsonSerializationContext): JsonElement {
        return JsonPrimitive(DATE_FORMAT.format(cal.time))
    }

    override fun deserialize(j: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Calendar? {
        val fieldString = j.asString
        for (dateFormat in DATE_FORMATS) {
            try {
                return Calendar.getInstance().apply {
                    time = dateFormat.parse(fieldString)
                }
            } catch (pe: ParseException) {
                Timber.w("Failed to parse date with format $dateFormat")
            }
        }
        return null
    }
}