package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.Category
import mohamed.salem.tastyfood.databinding.CategoriesRowBinding

class CategoriesHomeAdapter():RecyclerView.Adapter<CategoriesHomeAdapter.ViewHolder>()  {

    lateinit var onCategoriesHomeClick: ((Category) -> Unit)

    private var diffUtil = object :DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                    return  oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this , diffUtil)

    class ViewHolder(val binding : CategoriesRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal  = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text  = meal.strCategory

        holder.itemView.setOnClickListener {
            onCategoriesHomeClick.invoke(differ.currentList[position])
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}