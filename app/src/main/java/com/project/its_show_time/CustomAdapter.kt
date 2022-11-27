import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.its_show_time.*
import kotlinx.coroutines.currentCoroutineContext


class CustomAdapter(private val mList: List<ItemsViewModel>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(), OnClikInterface {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text
        holder.textViewLink.text = ItemsViewModel.textLink
        holder.itemView.setOnClickListener {
            try {
//                (holder.itemView.context as HomeActivity).clickAdapater(holder.textViewLink.text as String)
                (holder.itemView.context  as  SideNavigationActivity).clickAdapater(holder.textViewLink.text as String)
            } catch (e: Exception) {
                Log.d(">>",e.toString())
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val imageviewLink: ImageView = itemView.findViewById(R.id.imageviewLink)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textViewLink: TextView = itemView.findViewById(R.id.textViewDesc)

    }
}
