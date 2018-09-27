import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import it.chutien.skeletonscreen.R
import it.chutien.skeletonscreen.Recipe

class RecipeListAdapter(private val context: Context, private val cartList: List<Recipe>) : RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        return cartList.size
    }
    // recipe



    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var description: TextView
        var price: TextView
        var chef: TextView
        var timestamp: TextView
        var thumbnail: SimpleDraweeView

        init {
            name = view.findViewById(R.id.name)
            chef = view.findViewById(R.id.chef)
            description = view.findViewById(R.id.description)
            price = view.findViewById(R.id.price)
            thumbnail = view.findViewById(R.id.thumbnail)
            timestamp = view.findViewById(R.id.timestamp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, name, description, price1, thumbnail, chef, timestamp) = cartList[position]
        holder.name.text = name
        holder.chef.text = "By $chef"
        holder.description.text = description
        holder.price.text = "Price: ₹$price1"
        holder.timestamp.text = timestamp

        holder.thumbnail.setImageURI(thumbnail)
    }
}