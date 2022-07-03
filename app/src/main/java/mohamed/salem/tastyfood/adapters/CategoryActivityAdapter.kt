package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.MealPopular
import mohamed.salem.tastyfood.databinding.CategoryActivityRowBinding

class CategoryActivityAdapter():RecyclerView.Adapter<CategoryActivityAdapter.ViewHolder>() {
    lateinit var onClickItemCategoryActivity:((MealPopular) ->Unit)
    lateinit var onClickLongImemCategoryActivity :((MealPopular)-> Unit)
    private  var mealList = ArrayList<MealPopular>()
    fun setMeals(mealList :ArrayList<MealPopular>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding :CategoryActivityRowBinding) :RecyclerView.ViewHolder(binding.root){

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
            onClickItemCategoryActivity.invoke(mealList[position])
        }


        holder.itemView.setOnLongClickListener {
            onClickLongImemCategoryActivity.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}