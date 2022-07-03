package mohamed.salem.tastyfood.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MealPopular(

    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)