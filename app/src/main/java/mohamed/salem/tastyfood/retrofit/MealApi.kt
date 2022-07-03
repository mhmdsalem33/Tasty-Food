package mohamed.salem.tastyfood.retrofit

import mohamed.salem.tastyfood.data.CategoryList
import mohamed.salem.tastyfood.data.MealList
import mohamed.salem.tastyfood.data.MealPopularList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>
    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String ) :Call<MealList>
    @GET("filter.php")
    fun getOverPopular(@Query("c") CategoryName:String):Call<MealPopularList>
    @GET("categories.php")
    fun getAllCategories() :Call<CategoryList>
    @GET("filter.php")
    fun getCategoryActivity(@Query("c") CategoryName: String ) :Call<MealPopularList>
    @GET("search.php")
    fun getSearchMeals(@Query("s") searchQuery:String) :Call<MealList>
}