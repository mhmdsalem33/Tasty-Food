package mohamed.salem.tastyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverts {
    @TypeConverter
    fun fromAnyToSting(attribute : Any?):String{
        if (attribute == null)
            return ""
        return attribute as String
    }
    @TypeConverter
    fun fromStringToAny(attribute: String?):Any{
        if (attribute == null)
            return ""
        return attribute
    }
}