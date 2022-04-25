package com.example.rebecca.hikingjournal



import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val ARG_HIKE_ID = "hike_id"
private const val TAG = "HikeFragment"


class HikeFragment: Fragment() {
    private lateinit var hike: Hike
    private lateinit var journalField: EditText
    private lateinit var location : EditText
    private lateinit var name :EditText
    private lateinit var hikedCheckBox: CheckBox
    private lateinit var difficulty: EditText
    private lateinit var distance: EditText

    private val hikeDetailViewModel: HikeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(HikeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hike = Hike()
        val hikeId: UUID = arguments?.getSerializable(ARG_HIKE_ID) as UUID
        hikeDetailViewModel.loadHike(hikeId)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hike, container, false)

        journalField = view.findViewById(R.id.journal_entry_edit) as EditText
        location = view.findViewById(R.id.hike_location) as EditText
        hikedCheckBox = view.findViewById(R.id.hike_visited) as CheckBox
        name = view.findViewById(R.id.hike_name) as EditText
        difficulty= view.findViewById(R.id.hike_difficulty) as EditText
        distance = view.findViewById(R.id.hike_distance) as EditText

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hikeDetailViewModel.hikeLiveData.observe(
                viewLifecycleOwner,
                Observer { hike ->
                    hike?.let {
                        this.hike= hike
                        updateUI()
                    }
                }
        )
    }



    override fun onStart(){
        super.onStart()
//        val titleWatcher= object: TextWatcher{
//            override fun beforeTextChanged(
//                    sequence: CharSequence,
//                    start: Int,
//                    count:Int,
//                    after:Int
//            ){
//
//            }
//            override fun onTextChanged(
//                    sequence: CharSequence,
//                    start: Int,
//                    before: Int,
//                    count:Int
//            ){
//                hike.name= sequence.toString()
//            }
//
//            override fun afterTextChanged(sequence: Editable?) {
//
//            }
//        }
//        journalField.addTextChangedListener(titleWatcher)
        hikedCheckBox.apply{
            setOnCheckedChangeListener { _, isChecked ->
                hike.isHiked= isChecked
            }
        }
    }
    override fun onStop() {
        super.onStop()
        hikeDetailViewModel.saveHike(hike)
    }

    override fun onPause() {
        super.onPause()
        hike.name= name.text.toString()
        hike.journal=journalField.text.toString()
        hike.location=location.text.toString()
        hike.difficulty= difficulty.text.toString()
        hike.distance = distance.text.toString()

    }

    private fun updateUI() {
        name.setText(hike.name)
        journalField.setText(hike.journal)
        location.setText(hike.location)
        difficulty.setText(hike.difficulty)
        distance.setText(hike.distance)
        hikedCheckBox.apply {
            isChecked = hike.isHiked
            jumpDrawablesToCurrentState()
        }
    }
    companion object {
        fun newInstance(hikeId: UUID): HikeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_HIKE_ID, hikeId)
            }
            return HikeFragment().apply {
                arguments = args
            }
        }
    }
}
