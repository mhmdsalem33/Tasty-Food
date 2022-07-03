package mohamed.salem.tastyfood.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealList
import mohamed.salem.tastyfood.db.MealDatabase
import mohamed.salem.tastyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealViewModel(
           val mealDatabase: MealDatabase
) :ViewModel(){

    private var mealDetailLiveData = MutableLiveData<Meal>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }else
                    return

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("error" , t.message.toString())
            }

        })
    }

    fun observerGetMealLiveData() :LiveData<Meal>{
        return mealDetailLiveData
    }


    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observerFavoriteMealLiveData() : LiveData<List<Meal>>{
        return favoriteMealLiveData
    }







}