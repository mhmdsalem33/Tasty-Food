package mohamed.salem.tastyfood.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.category_activity_row.view.*
import mohamed.salem.tastyfood.R
import mohamed.salem.tastyfood.ViewModel.CategoryActivityViewModel
//import mohamed.salem.tastyfood.ViewModel.CategoryActivityViewModelFactory
import mohamed.salem.tastyfood.adapters.CategoryActivityAdapter
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealPopular
import mohamed.salem.tastyfood.databinding.ActivityCategoryBinding
import mohamed.salem.tastyfood.databinding.FragmentMealBottomSheetBinding
import mohamed.salem.tastyfood.db.MealDatabase
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.CATEGORY_NAME
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_ID
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_NAME
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_THUMB
import mohamed.salem.tastyfood.fragments.bottomshet.MealBottomSheetFragment

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding :ActivityCategoryBinding
    private lateinit var categoryActivityMvvm:CategoryActivityViewModel
    private lateinit var categoryActivityAdapter: CategoryActivityAdapter

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryActivityAdapter = CategoryActivityAdapter()

        /*
        val mealDatabase = MealDatabase.getInstance(this)
        val categoryActivityViewModelFactory = CategoryActivityViewModelFactory(mealDatabase)
        categoryActivityMvvm = ViewModelProvider(this , categoryActivityViewModelFactory)[CategoryActivityViewModel::class.java]


         */
       categoryActivityMvvm = ViewModelProviders.of(this)[CategoryActivityViewModel::class.java]
        categoryActivityMvvm.getCategoryActivity(intent.getStringExtra(CATEGORY_NAME)!!)
        observeCategoryActivity()
        prepareRecViewCategoryActivity()
        onCategoryItemClick()

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        onCategoryItemLongClick()
    }

    private fun onCategoryItemLongClick() {
        categoryActivityAdapter.onClickLongImemCategoryActivity = { meal ->

        }
    }


    private fun onCategoryItemClick() {
                categoryActivityAdapter.onClickItemCategoryActivity = { meal->
                    val intent = Intent(this , MealActivity::class.java)
                        intent.putExtra(MEAL_ID     , meal.idMeal)
                        intent.putExtra(MEAL_NAME   , meal.strMeal)
                        intent.putExtra(MEAL_THUMB  , meal.strMealThumb)

                    startActivity(intent)
                }


    }

    private fun prepareRecViewCategoryActivity() {
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(context , 2 ,RecyclerView.VERTICAL,false)
            adapter = categoryActivityAdapter
        }
    }

    private var mealToSave : List<MealPopular>? = null
    private fun observeCategoryActivity() {
        categoryActivityMvvm.observerGetCategoryActivity().observe(this , Observer { mealList->


            categoryActivityAdapter.setMeals(mealList = mealList as ArrayList<MealPopular>)
                binding.tvCountOfMeals.text = mealList.size.toString()

            mealToSave = mealList
            mealToSave!!.forEach {
                Log.d("test" , it.idMeal)
            }
        })
    }
}