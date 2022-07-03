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

class HomeViewModel(
    val mealDatabase: MealDatabase
) :ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetLiveData = MutableLiveData<Meal>()



    private var saveStateMeal :Meal? = null

    fun getRandomMeal(){


        saveStateMeal?.let { randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }


        RetrofitInstance.api.getRandomMeal().enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    randomMealLiveData.value = response.body()!!.meals[0]
                    saveStateMeal = response.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("error" , t.message.toString() )
            }

        })
    }

    fun observerGetRandomMeal() : LiveData<Meal>{
        return  randomMealLiveData
    }


    fun observerFavoriteMealLiveData() : LiveData<List<Meal>>{
        return favoriteMealLiveData
    }


    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                val meal = response.body()!!.meals.first()
                meal.let { meal->
                    bottomSheetLiveData.postValue(meal)
                   // meal.idMeal
                }



            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel" , t.message.toString())
            }

        })
    }

    fun observerBottomSheetLiveData() : LiveData<Meal> = bottomSheetLiveData

    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

}