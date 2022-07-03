package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.databinding.CategoryActivityRowBinding

class FavoriteFragmentAdapter() :RecyclerView.Adapter<FavoriteFragmentAdapter.ViewHolder>() {

    lateinit var onFavItemClick :((Meal)  ->  Unit)
     var mealList = ArrayList<Meal>()
    fun setMeals(mealList :ArrayList<Meal>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : CategoryActivityRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(CategoryActivityRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgCategoryRow)
        holder.binding.tvCategoryName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onFavItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
       return mealList.size
    }
}