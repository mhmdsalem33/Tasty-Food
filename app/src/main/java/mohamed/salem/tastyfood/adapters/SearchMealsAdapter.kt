package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.Meal
import mohamed.salem.tastyfood.databinding.CategoryActivityRowBinding
import mohamed.salem.tastyfood.databinding.OverRowBinding

class SearchMealsAdapter():RecyclerView.Adapter<SearchMealsAdapter.ViewHolder>() {


    lateinit var onItemClick :((Meal) ->Unit)
    lateinit var onItemLongClick :((Meal) -> Unit)

    val diffUtil = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return  oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this , diffUtil)

    class ViewHolder(val binding: CategoryActivityRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryActivityRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgCategoryRow)
        holder.binding.tvCategoryName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }

        holder.itemView.setOnLongClickListener {
                onItemLongClick.invoke(meal)
            true
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}