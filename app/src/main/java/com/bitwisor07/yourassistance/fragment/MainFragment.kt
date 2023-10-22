package com.bitwisor07.yourassistance.fragment

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitwisor.yourassistance.databinding.FragmentMainBinding
import com.bitwisor07.yourassistance.Adapter.MainAdapter
import com.bitwisor07.yourassistance.Data.ChatEntity
import com.bitwisor07.yourassistance.Data.Query.HumanQuery
import com.bitwisor07.yourassistance.MainActivity
import com.bitwisor07.yourassistance.ViewModels.MainViewModel
import com.bitwisor07.yourassistance.ViewModels.Resource
import com.bitwisor07.yourassistance.database.ChatDatabase
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var chatAdapter:MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        if(isOnline(requireContext())){
            binding.statuss.text ="Online"
        }
        else{
            binding.statuss.text = "Offline"
        }
        viewModel.botchat.observe(viewLifecycleOwner, Observer {response->
            when(response) {
                is Resource.Success -> {
                    binding.statuss.text ="Online"
                    response.data?.let { chatResponse ->
                        chatAdapter.add(chatResponse)
                        binding.mainRecview.smoothScrollToPosition(chatAdapter.itemCount-1)

                    }
                }
                is Resource.Error -> {
                    binding.statuss.text ="Offline"
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                    Snackbar.make(requireView(),"Sorry I couldn't understand",Snackbar.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {
                    binding.statuss.text ="Typing..."
//                    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
                }
                else -> {
                    val a = ChatEntity(0,"Currently unable to process your request due to heavy load on my brain(server) or Bad Internet, please try again later!!",
                        "",false)
                    chatAdapter.add(a)
                    val dao = ChatDatabase.getDatabase(requireContext()).chatDao()
                    viewModel.insertIntoDb(a)
                    binding.statuss.text = "Online"
                    binding.mainRecview.smoothScrollToPosition(chatAdapter.itemCount-1)
                }
            }
        })
        binding.appnamee.setOnClickListener {
            Snackbar.make(requireView(),"Made with ❤️ by Shivendu Mishra",Snackbar.LENGTH_SHORT).show()
        }
        binding.deletebtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Delete Chats")
            builder.setTitle("Do you really want to delete all chats?")
            builder.setCancelable(false)
            builder.setNegativeButton("No!",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.deleteAllChats()
                    setupRecyclerView()
                })

            val alert: AlertDialog = builder.create()
            alert.show()
        }
        binding.sendbtn.setOnClickListener {
            val str = binding.mainEditTxt.text.toString()
            binding.mainEditTxt.text.clear()
            if(!str.isNullOrEmpty()){

                if(isOnline(requireContext())){
                    binding.statuss.text ="Online"
                    val stop = ArrayList<String>()
                    stop.add("Human")
                    val a =ChatEntity(0,"",refine(str),true)
                    chatAdapter.add(a)
                    viewModel.insertIntoDb(a)
                    binding.mainRecview.smoothScrollToPosition(chatAdapter.itemCount-1)
                    viewModel.getResp(HumanQuery(0,1000,"text-davinci-003",0.6,"Human: ${str}",stop,0,1))

                }
                else{
                    Snackbar.make(requireView(),"Can't send messages bot is offline",Snackbar.LENGTH_SHORT).show()
                    binding.statuss.text = "Offline"
                }
            }
            else{
                Snackbar.make(requireView(),"Message box is empty",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun refine(str: String): String {
        return str.trim()
    }

    private fun setupRecyclerView() {
        chatAdapter = MainAdapter(requireContext())
        viewModel.detailsofChat.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                chatAdapter.rusetlist(it)
                if(it.size>0){
                    binding.mainRecview.smoothScrollToPosition(it.size-1)
                }
            }
        })
        binding.mainRecview.apply {

            adapter = chatAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}