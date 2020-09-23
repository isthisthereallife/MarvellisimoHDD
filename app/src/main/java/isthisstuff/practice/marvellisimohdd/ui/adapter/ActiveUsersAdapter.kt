package isthisstuff.practice.marvellisimohdd.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import isthisstuff.practice.marvellisimohdd.MyActiveUsersViewHolder
import isthisstuff.practice.marvellisimohdd.R
import isthisstuff.practice.marvellisimohdd.entities.MarvelObject
import java.time.LocalDateTime

class ActiveUsersAdapter(_sender: String, _marvelObject: MarvelObject) :
    RecyclerView.Adapter<MyActiveUsersViewHolder>() {
    private var database = FirebaseDatabase.getInstance()
    var sender = _sender
    var marvelObject = _marvelObject

    var data = listOf<Pair<String, String>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyActiveUsersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_active_user, parent, false) as LinearLayout
        return MyActiveUsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyActiveUsersViewHolder, position: Int) {
        val item = data[position]
        holder.view.findViewById<TextView>(R.id.user_email_text_view).text =
            item.second.replace(",", ".")
        holder.view.findViewById<LinearLayout>(R.id.active_user_item).setOnClickListener {
            sendMessage(
                sender = sender,
                receiver = item.second,
                payload = marvelObject.id.toString()
            )
            val updatedText = "Skickat till ${item.second}"
            holder.view.findViewById<TextView>(R.id.user_email_text_view).text = updatedText
        }

    }

    private fun sendMessage(sender: String, receiver: String, payload: String) {
        val customDatabaseMessageReference =
            database.getReference("<TO:${receiver.replace(".", ",")}>")
        val timeString = LocalDateTime.now().toString().replace(".", ":")

        val message = "<SENDER>$sender</SENDER><PAYLOAD>$payload</PAYLOAD><TIMESTAMP>$timeString</TIMESTAMP>"

        customDatabaseMessageReference.push().setValue(message)


    }

}