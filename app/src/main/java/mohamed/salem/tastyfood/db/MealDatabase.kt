package mohamed.salem.tastyfood.db

import android.content.Context
import androidx.room.*
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealPopular

@Database(entities = [Meal::class] , version = 1)
@TypeConverters(MealTypeConverts::class)
abstract class MealDatabase : RoomDatabase() {
        abstract fun mealDao() :MealDao

    companion object{
     @Volatile
     var INSTANCE :MealDatabase? = null
        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meals.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }


    }



}