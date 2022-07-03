package mohamed.salem.tastyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import mohamed.salem.tastyfood.Activites.MealActivity
import mohamed.salem.tastyfood.MainActivity
import mohamed.salem.tastyfood.ViewModel.HomeViewModel
import mohamed.salem.tastyfood.adapters.FavoriteFragmentAdapter
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.databinding.FragmentFavoriteBinding
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_ID
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_NAME
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.MEAL_THUMB


class FavoriteFragment : Fragment() {

    private lateinit var binding     :FragmentFavoriteBinding
    private lateinit var homeMvvm    :HomeViewModel
    private lateinit var favAdapter  :FavoriteFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       homeMvvm = (activity as MainActivity).homeMvvm
        favAdapter = FavoriteFragmentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container , false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       prepareRecViewFav()
       observeFavLiveData()
        onFavItemClick()

        val itemTouchHelper = object  :ItemTouchHelper.SimpleCallback(0 ,
                     ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                            or  ItemTouchHelper.UP or  ItemTouchHelper.DOWN
            ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        homeMvvm.deleteMeal(favAdapter.mealList[position]!!)
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFav)

    }

    private fun onFavItemClick() {
        favAdapter.onFavItemClick = { meal ->
            val intent = Intent(activity , MealActivity::class.java)
                intent.putExtra(MEAL_ID , meal.idMeal)
                intent.putExtra(MEAL_NAME , meal.strMeal)
                intent.putExtra(MEAL_THUMB , meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeFavLiveData() {
        homeMvvm.observerFavoriteMealLiveData().observe(requireActivity() , Observer { meal->
            favAdapter.setMeals(mealList = meal as ArrayList<Meal>)
            binding.countFav.text = meal.size.toString()
            meal.forEach {
                Log.d("test" ,it.idMeal)
            }
        })
    }





    private fun prepareRecViewFav() {
        binding.recViewFav.apply {
            layoutManager = GridLayoutManager(activity ,2 , RecyclerView.VERTICAL, false)
            adapter  = favAdapter
        }
    }



}