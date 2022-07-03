package mohamed.salem.tastyfood.fragments.bottomshet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mohamed.salem.tastyfood.Activites.MealActivity
import mohamed.salem.tastyfood.MainActivity
import mohamed.salem.tastyfood.R
import mohamed.salem.tastyfood.ViewModel.HomeViewModel
import mohamed.salem.tastyfood.databinding.FragmentMealBottomSheetBinding
import mohamed.salem.tastyfood.fragments.HomeFragment



private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {


    private  var mealId    : String? = null
    private  var mealName  : String? = null
    private  var mealThump : String? = null
    private lateinit var binding : FragmentMealBottomSheetBinding
    private lateinit var homeMvvm: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealId = it.getString(MEAL_ID)


        }



        homeMvvm = (activity as MainActivity).homeMvvm
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // mealId?.let { homeMvvm.getMealById(it) }


        // mealId?.let { homeMvvm.getMealById(it) }
        mealId?.let {
            homeMvvm.getMealById(it)
        }
        observeBottomSheet()

        onBottomSheetDialogClick()




    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {

            if(mealName != null && mealThump != null){
                val intent = Intent(activity , MealActivity::class.java)
                     intent.putExtra(HomeFragment.MEAL_ID , mealId)
                     intent.putExtra(HomeFragment.MEAL_NAME , mealName)
                     intent.putExtra(HomeFragment.MEAL_THUMB , mealThump)
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheet() {
            homeMvvm.observerBottomSheetLiveData().observe(viewLifecycleOwner , Observer { meal->
                Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.imgBottomSheet)
                binding.tvAreaBottomSheet.text     = meal.strArea
                binding.tvCategoryBottomSheet.text = meal.strCategory
                binding.tvMealNameBottomSheet.text = meal.strMeal

                mealName  = meal.strMeal
                mealThump = meal.strMealThumb



            })
    }


    companion object {
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }


}