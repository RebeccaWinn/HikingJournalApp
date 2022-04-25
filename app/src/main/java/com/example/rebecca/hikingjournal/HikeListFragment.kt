package com.example.rebecca.hikingjournal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*

private const val TAG= "HikeListFragment"

class HikeListFragment : Fragment() {

    interface Callbacks {
        fun onHikeSelected(hikeId: UUID)
    }

    private var callbacks: Callbacks? = null


    private lateinit var  hikeRecyclerView: RecyclerView
    private var adapter: HikeAdapter? = HikeAdapter(emptyList())

    private val hikeListViewModel: HikeListViewModel by lazy{
        ViewModelProviders.of(this).get(HikeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater:LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view= inflater.inflate(R.layout.fragment_hike_list,container,false)

        hikeRecyclerView=
            view.findViewById(R.id.hike_recycler_view) as RecyclerView
        hikeRecyclerView.layoutManager= LinearLayoutManager(context)

        hikeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hikeListViewModel.hikeListLiveData.observe(
            viewLifecycleOwner,
            Observer { hikes ->
                hikes?.let {
                    Log.i(TAG, "Got hikes ${hikes.size}")
                    updateUI(hikes)
                }
            })
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_hike_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_hike -> {
                val hike = Hike()
                hikeListViewModel.addHike(hike)
                callbacks?.onHikeSelected(hike.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun updateUI(hikes: List<Hike>) {
        adapter = HikeAdapter(hikes)
        hikeRecyclerView.adapter= adapter
    }
    companion object{
        fun newInstance():HikeListFragment{
            return HikeListFragment()
        }
    }
    private inner class HikeHolder(view:View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var hike: Hike

        private val titleEditView: TextView= itemView.findViewById(R.id.hike_name)
        private val hikedImageView: ImageView = itemView.findViewById(R.id.hike_visited)
        private val locationEditView: TextView = itemView.findViewById(R.id.hike_location)
        private val difficultyEditView: TextView = itemView.findViewById(R.id.hike_difficulty)
        init{
            itemView.setOnClickListener(this)
        }
        fun bind(hike:Hike){
            this.hike= hike
            titleEditView.text= this.hike.name
            locationEditView.text=this.hike.location
            difficultyEditView.text=this.hike.difficulty
            hikedImageView.visibility = if (hike.isHiked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        override fun onClick(v:View){
            callbacks?.onHikeSelected(hike.id)

        }
    }

    private inner class HikeAdapter(var hikes: List<Hike>)
        :RecyclerView.Adapter<HikeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : HikeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_hike,parent,false)
            return HikeHolder(view)
        }

        override fun getItemCount()= hikes.size

        override fun onBindViewHolder(holder: HikeHolder, position: Int) {
            val hike =hikes[position]
            holder.bind(hike)
        }

    }

}