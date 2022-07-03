package mohamed.salem.tastyfood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealList
import mohamed.salem.tastyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMealsViewModel():ViewModel() {
    private var searchMealLiveData = MutableLiveData<List<Meal>>()
    fun getSearchMeals(searchQuery :String){
        RetrofitInstance.api.getSearchMeals(searchQuery).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()!!.meals?.let {  mealList->
                    searchMealLiveData.postValue(mealList)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("search" , t.message!!)
            }

        })

    }

    fun observeSearchMealLiveData() :LiveData<List<Meal>> = searchMealLiveData
}