package mohamed.salem.tastyfood.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohamed.salem.tastyfood.data.Category
import mohamed.salem.tastyfood.data.CategoryList
import mohamed.salem.tastyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesHomeViewModel() :ViewModel() {
    private var categoriesHomeLiveData = MutableLiveData<List<Category>>()
    fun getCategoriesHomeLiveData(){
        RetrofitInstance.api.getAllCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    categoriesHomeLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("error" , t.message.toString())
            }

        })
    }

    fun observerGetCategoriesHomeLiveData () :LiveData<List<Category>>{
        return categoriesHomeLiveData
    }
}