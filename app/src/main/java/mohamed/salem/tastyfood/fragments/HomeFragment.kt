package mohamed.salem.tastyfood.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.Activites.CategoryActivity
import mohamed.salem.tastyfood.Activites.MealActivity
import mohamed.salem.tastyfood.MainActivity
import mohamed.salem.tastyfood.R
import mohamed.salem.tastyfood.ViewModel.CategoriesHomeViewModel
import mohamed.salem.tastyfood.ViewModel.HomeViewModel
import mohamed.salem.tastyfood.ViewModel.OverPopularViewModel
import mohamed.salem.tastyfood.adapters.CategoriesHomeAdapter
import mohamed.salem.tastyfood.adapters.OverPopularAdapter
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.data.MealPopular

import mohamed.salem.tastyfood.databinding.FragmentHomeBinding
import mohamed.salem.tastyfood.fragments.bottomshet.MealBottomSheetFragment
import java.lang.reflect.Array.newInstance


class HomeFragment : Fragment() {


    private lateinit var binding    :FragmentHomeBinding
    private lateinit var homeMvvm   :HomeViewModel
    private lateinit var overMvvm   :OverPopularViewModel
    private lateinit var categoriesMvvm:CategoriesHomeViewModel
    private lateinit var overPopularAdapter :OverPopularAdapter
    private lateinit var categoriesHomeAdapter :CategoriesHomeAdapter
    private lateinit var randomMeal :Meal


    companion object{
        const val  MEAL_ID     = "mmohamed.salem.tastyfood.fragments.mealId"
        const val  MEAL_NAME   = "mmohamed.salem.tastyfood.fragments.mealName"
        const val  MEAL_THUMB  = "mmohamed.salem.tastyfood.fragments.mealThumb"
        const val  CATEGORY_NAME = "mmohamed.salem.tastyfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm = (activity as MainActivity).homeMvvm
     // homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        overMvvm = ViewModelProviders.of(this)[OverPopularViewModel::class.java]
        categoriesMvvm = ViewModelProviders.of(this)[CategoriesHomeViewModel::class.java]


            overPopularAdapter = OverPopularAdapter()
            categoriesHomeAdapter = CategoriesHomeAdapter()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                homeMvvm.getRandomMeal()
                observeGetRandomMeal()
                onRandomMealCardClick()
                onRandomMealCardLongClick()

                overMvvm.getOverPopular()
                observerGetOverPopular()
                prepareRecViewOverPopular()
                onOverPopularClick()
                onOverPopularLongClick()

                categoriesMvvm.getCategoriesHomeLiveData()
                observeCategoriesHomeLiveData()
                prepareRecViewCategoiesHome()
                onCategoriesHomeClick()



                onSearchItemClick()
    }

    private fun onSearchItemClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }


    private fun onOverPopularLongClick() {
        overPopularAdapter.onLongItemClick = { meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
                mealBottomSheetFragment.show(childFragmentManager , "Meal Info")
        }
    }

    private fun onCategoriesHomeClick() {
            categoriesHomeAdapter.onCategoriesHomeClick = {  meal->
                    val intent = Intent(activity , CategoryActivity::class.java)
                        intent.putExtra(CATEGORY_NAME , meal.strCategory)
                    startActivity(intent)

            }
    }

    private fun prepareRecViewCategoiesHome() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context ,3 , RecyclerView.VERTICAL , false)
            adapter = categoriesHomeAdapter
        }
    }

    private fun observeCategoriesHomeLiveData() {
        categoriesMvvm.observerGetCategoriesHomeLiveData().observe(viewLifecycleOwner , Observer { meal->

            categoriesHomeAdapter.differ.submitList(meal)

        })
    }

    private fun onOverPopularClick() {
        overPopularAdapter.onOverItemClick = { meal->
            val intent = Intent(activity   , MealActivity::class.java)
                intent.putExtra(MEAL_ID    , meal.idMeal)
                intent.putExtra(MEAL_NAME  , meal.strMeal)
                intent.putExtra(MEAL_THUMB , meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun prepareRecViewOverPopular() {
                binding.tvRecViewOverPopular.apply {
                    layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
                    adapter =  overPopularAdapter
                }
    }

    private fun observerGetOverPopular() {
        overMvvm.observerGetOverPopular().observe(viewLifecycleOwner , Observer { meal->


            overPopularAdapter.setMeals(mealList = meal as ArrayList<MealPopular>)

        })
    }

    private fun onRandomMealCardClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity   , MealActivity::class.java)
                intent.putExtra(MEAL_ID    , randomMeal.idMeal)
                intent.putExtra(MEAL_NAME  , randomMeal.strMeal)
                intent.putExtra(MEAL_THUMB , randomMeal.strMealThumb)


            startActivity(intent)

        }
    }

    private fun onRandomMealCardLongClick() {
        binding.randomMealCard.setOnLongClickListener {
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(randomMeal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager , "Meal Info")

            true
        }
    }
    private fun observeGetRandomMeal() {
        homeMvvm.observerGetRandomMeal().observe(viewLifecycleOwner , Observer { meal->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.tvImgRandomMeal)
            this.randomMeal = meal
        })
    }


}