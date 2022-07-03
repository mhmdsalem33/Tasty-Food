package mohamed.salem.tastyfood.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mohamed.salem.tastyfood.db.MealDatabase


class MealViewModelFactory(val mealDatabase: MealDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  MealViewModel(mealDatabase) as T
    }
}

