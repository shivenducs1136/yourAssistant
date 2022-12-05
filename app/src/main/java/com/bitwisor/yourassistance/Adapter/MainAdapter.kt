package com.bitwisor.yourassistance.Adapter

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bitwisor.yourassistance.Data.ChatEntity
import com.bitwisor.yourassistance.R
import com.bitwisor.yourassistance.database.ChatDatabase


class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    private var detailsofQuiz:ArrayList<ChatEntity> = ArrayList<ChatEntity>()

    fun add(s: ChatEntity){
        detailsofQuiz.add(s)
        notifyDataSetChanged()
    }
    fun clearList(){
        detailsofQuiz.clear()
        notifyDataSetChanged()
    }
    fun rusetlist(s:List<ChatEntity>){
        detailsofQuiz = s as ArrayList<ChatEntity>

        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.message_sent,parent,false)
        return MainAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=detailsofQuiz[position]
        holder.robot.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("user", currentItem.robotmsg)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(context,"Copied to clip board!",Toast.LENGTH_SHORT).show()
        }
        holder.human.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("user", currentItem.robotmsg)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(context,"Copied to clip board!",Toast.LENGTH_SHORT).show()
        }
        if(currentItem.isHuman){
            holder.human.visibility =View.VISIBLE
            holder.robot.visibility = View.GONE
            holder.human.text = currentItem.humanmsg
        }
        else{
            holder.robot.visibility =View.VISIBLE
            holder.human.visibility = View.GONE
            holder.robot.text = currentItem.robotmsg
        }
    }

    override fun getItemCount(): Int {
        return detailsofQuiz.size
    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val robot: TextView =itemView.findViewById(R.id.tv_robot)
        val human: TextView = itemView.findViewById(R.id.tv_human)
    }
}