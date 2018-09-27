import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import it.chutien.skeletonscreen.ItemAnimation
import it.chutien.skeletonscreen.R
import it.chutien.skeletonscreen.Recipe

class RecipeListAdapter(private val context: Context, private val cartList: List<Recipe>, private val animation_type: Int) : RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>() {


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
        setAnimation(holder.itemView, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                on_attach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    private var lastPosition = -1
    private var on_attach = true


    private fun setAnimation(itemView: View, position: Int) {
        if (position > lastPosition) {
            ItemAnimation.animate(itemView, if (on_attach) position else -1, animation_type)
            lastPosition = position
        }
    }


}