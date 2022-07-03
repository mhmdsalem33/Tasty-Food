package mohamed.salem.tastyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealPopular

@Dao
interface MealDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)


    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() :LiveData<List<Meal>>
}