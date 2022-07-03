package mohamed.salem.tastyfood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohamed.salem.tastyfood.data.MealPopular
import mohamed.salem.tastyfood.data.MealPopularList
import mohamed.salem.tastyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverPopularViewModel () :ViewModel() {
    private var overPopularLiveData = MutableLiveData<List<MealPopular>>()
    fun getOverPopular(){
        RetrofitInstance.api.getOverPopular("Beef").enqueue(object  :Callback<MealPopularList>{
            override fun onResponse(
                call: Call<MealPopularList>,
                response: Response<MealPopularList>
            ) {
                if (response.body() != null){
                    overPopularLiveData.value = response.body()!!.meals
                }else
                    return
            }

            override fun onFailure(call: Call<MealPopularList>, t: Throwable) {
               Log.d("error" , t.message.toString())
            }

        })
    }
    fun observerGetOverPopular() :LiveData<List<MealPopular>>{
        return overPopularLiveData
    }
}