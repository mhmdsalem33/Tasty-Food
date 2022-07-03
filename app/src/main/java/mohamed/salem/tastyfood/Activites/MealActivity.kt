package mohamed.salem.tastyfood.Activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import mohamed.salem.tastyfood.R
import mohamed.salem.tastyfood.ViewModel.MealViewModel
import mohamed.salem.tastyfood.ViewModel.MealViewModelFactory
//import mmohamed.salem.tastyfood.ViewModel.MealViewModelFactory
import mohamed.salem.tastyfood.data.Meal


import mohamed.salem.tastyfood.databinding.ActivityMealBinding
import mohamed.salem.tastyfood.db.MealDatabase
import mohamed.salem.tastyfood.fragments.HomeFragment


class MealActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel
    private lateinit var mealId  :String
    private lateinit var mealName :String
    private lateinit var mealThumb :String
    private lateinit var youtube :String
    lateinit var mAdView : AdView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mealDatabase = MealDatabase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm= ViewModelProvider(this , mealViewModelFactory)[MealViewModel::class.java]
       // mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        getInformationFromIntent()
        setInformationInViews()

        mealMvvm.getMealDetail(mealId)
        observeGetMealDetail()
        btnAddToFavrite()

        loadingCase()
        onYoutubeImgCLick()

    }

    private fun onYoutubeImgCLick() {
        binding.tvYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtube))
            startActivity(intent)
        }
    }


    private fun btnAddToFavrite() {
        binding.btnFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private var mealToSave : Meal? = null
    private fun observeGetMealDetail() {
        mealMvvm.observerGetMealLiveData().observe(this , Observer { meal->
            onResponeCase()
            binding.tvArea.text     = "Area :  ${ meal.strArea}"
            binding.tvDetials.text  = meal.strInstructions
            binding.tcCategory.text = "Category : ${meal.strCategory}"
            youtube = meal.strYoutube.toString()

            this.mealToSave = meal


        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMeal)
        binding.collapsing.title = mealName
    }

    private fun getInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }


    private fun onResponeCase() {
        binding.progressLinear.visibility = View.INVISIBLE

        binding.btnFav.visibility         = View.VISIBLE
        binding.tvArea.visibility         = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tcCategory.visibility     = View.VISIBLE
        binding.tvDetials.visibility      = View.VISIBLE
    }

    private fun loadingCase() {
        binding.progressLinear.visibility = View.VISIBLE
        binding.btnFav.visibility         = View.INVISIBLE
        binding.tvArea.visibility         = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tcCategory.visibility     = View.INVISIBLE
        binding.tvDetials.visibility      = View.INVISIBLE
    }



}