package mohamed.salem.tastyfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.category_activity_row.view.*
import mohamed.salem.tastyfood.ViewModel.HomeViewModel
import mohamed.salem.tastyfood.ViewModel.HomeViewModelFactory
import mohamed.salem.tastyfood.databinding.ActivityMainBinding
import mohamed.salem.tastyfood.db.MealDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    lateinit var mAdView : AdView

    val homeMvvm : HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this , homeViewModelFactory)[HomeViewModel::class.java]
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btn_navigation)
        val navController    = Navigation.findNavController(this , R.id.hostFragment)
        NavigationUI.setupWithNavController(bottomNavigation , navController)



        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


    }
}