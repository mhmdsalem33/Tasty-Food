package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.Category
import mohamed.salem.tastyfood.databinding.CategoriesRowBinding

class CategoriesFragmentAdapter():RecyclerView.Adapter<CategoriesFragmentAdapter.ViewHolder>() {

    lateinit var onClickCategories:((Category) ->Unit)
    private var mealList = ArrayList<Category>()
    fun setMeals(mealList : ArrayList<Category>){
        this.mealList = mealList
        notifyDataSetChanged()
    }
    class ViewHolder(val binding:CategoriesRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealList[position].strCategoryThumb)
           .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = mealList[position].strCategory
        holder.itemView.setOnClickListener {
            onClickCategories.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}