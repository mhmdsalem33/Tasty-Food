package mohamed.salem.tastyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohamed.salem.tastyfood.Activites.MealActivity
import mohamed.salem.tastyfood.R
import mohamed.salem.tastyfood.ViewModel.SearchMealsViewModel
import mohamed.salem.tastyfood.adapters.SearchMealsAdapter
import mohamed.salem.tastyfood.databinding.FragmentSearchBinding
import mohamed.salem.tastyfood.fragments.bottomshet.MealBottomSheetFragment


class SearchFragment : Fragment() {


    private lateinit var binding : FragmentSearchBinding
    private lateinit var searchMvvm :SearchMealsViewModel
    private lateinit var searchAdapter :SearchMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        searchMvvm = ViewModelProviders.of(this)[SearchMealsViewModel::class.java]

        searchMvvm.observeSearchMealLiveData()
        searchAdapter = SearchMealsAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        search()
        prepareSearchRecView()
        observeSearchMealLiveData()
        onSearchItemClick()
        onSearchItemLongClick()

    }

    private fun onSearchItemLongClick() {
        searchAdapter.onItemLongClick = { meal->
                    val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
                        mealBottomSheetFragment.show(childFragmentManager ,"meal info")
        }
    }

    private fun onSearchItemClick() {
        searchAdapter.onItemClick = { meal ->
            val intent = Intent(context , MealActivity::class.java)
                intent.putExtra(HomeFragment.MEAL_ID    , meal.idMeal)
                intent.putExtra(HomeFragment.MEAL_NAME  , meal.strMeal)
                intent.putExtra(HomeFragment.MEAL_THUMB , meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeSearchMealLiveData() {
        searchMvvm.observeSearchMealLiveData().observe(viewLifecycleOwner , Observer { mealList->
            searchAdapter.differ.submitList(mealList)
        })
    }

    private fun prepareSearchRecView() {
        binding.searchRecView.apply {
            layoutManager =GridLayoutManager(context , 2 , RecyclerView.VERTICAL , false)
            adapter = searchAdapter
        }
    }

    private fun search() {
        var searchJob :Job? = null
        binding.edtSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                searchMvvm.getSearchMeals(searchQuery.toString())
            }
        }
    }

}