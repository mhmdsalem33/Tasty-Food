package mohamed.salem.tastyfood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealPopular
import mohamed.salem.tastyfood.data.MealPopularList
import mohamed.salem.tastyfood.db.MealDatabase
import mohamed.salem.tastyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivityViewModel() :ViewModel() {

    private var categoryActivityLiveData = MutableLiveData<List<MealPopular>>()
    fun getCategoryActivity(CategoryName:String){
        RetrofitInstance.api.getCategoryActivity(CategoryName).enqueue(object :Callback<MealPopularList>{
            override fun onResponse(
                call: Call<MealPopularList>,
                response: Response<MealPopularList>
            ) {
                if (response.body() != null){
                    categoryActivityLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealPopularList>, t: Throwable) {
                Log.d("error" , t.message.toString())
            }

        })

    }

    fun observerGetCategoryActivity() :LiveData<List<MealPopular>>{
        return categoryActivityLiveData
    }

    /*
    fun insertMeal(mealPopular:List<MealPopular>){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(mealPopular)
        }
    }

     */
}