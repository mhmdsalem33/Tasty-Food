package mohamed.salem.tastyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamed.salem.tastyfood.Activites.CategoryActivity
import mohamed.salem.tastyfood.ViewModel.CategoriesHomeViewModel
import mohamed.salem.tastyfood.adapters.CategoriesFragmentAdapter
import mohamed.salem.tastyfood.data.Category
import mohamed.salem.tastyfood.databinding.FragmentCategoriesBinding
import mohamed.salem.tastyfood.fragments.HomeFragment.Companion.CATEGORY_NAME


class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoryHomeMvvm: CategoriesHomeViewModel
    private lateinit var categoriesFragmentAdapter :CategoriesFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesFragmentAdapter = CategoriesFragmentAdapter()
        categoryHomeMvvm = ViewModelProviders.of(this)[CategoriesHomeViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater , container , false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryHomeMvvm.getCategoriesHomeLiveData()
        observerGetCategoriesHomeLiveData()
        prepareRecviewCategoriesFragement()
        onCategoriesItemClick()
    }

    private fun onCategoriesItemClick() {
        categoriesFragmentAdapter.onClickCategories ={ meal->
            val intent = Intent(context , CategoryActivity::class.java)
                intent.putExtra(CATEGORY_NAME , meal.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRecviewCategoriesFragement() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context , 3 ,RecyclerView.VERTICAL , false)
            adapter = categoriesFragmentAdapter
        }
    }

    private fun observerGetCategoriesHomeLiveData() {
        categoryHomeMvvm.observerGetCategoriesHomeLiveData().observe(viewLifecycleOwner , Observer { meal->
            categoriesFragmentAdapter.setMeals(mealList = meal as ArrayList<Category>)

            meal.forEach {
                Log.d("test" , it.idCategory)
            }
        })
    }


}