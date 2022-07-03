package mohamed.salem.tastyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohamed.salem.tastyfood.data.MealPopular
import mohamed.salem.tastyfood.databinding.OverRowBinding

class OverPopularAdapter():RecyclerView.Adapter<OverPopularAdapter.ViewHolder>() {

    lateinit var onOverItemClick :((MealPopular) -> Unit)
      var onLongItemClick : ((MealPopular) -> Unit)? = null
    private var mealList  = ArrayList<MealPopular>()
    fun setMeals(mealList :ArrayList<MealPopular>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(OverRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgOver)

       holder.itemView.setOnClickListener {
                onOverItemClick.invoke(mealList[position])
       }
        holder.itemView.setOnLongClickListener {

            onLongItemClick!!.invoke(mealList[position])

            true
        }

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class ViewHolder(val binding :OverRowBinding):RecyclerView.ViewHolder(binding.root) {

    }
}