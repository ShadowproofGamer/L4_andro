package com.example.l4_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l4_andro.databinding.FragmentListBinding
import com.example.l4_andro.databinding.ListItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _binding: FragmentListBinding
    val dataRepo = DataRepo.getInstance()
    //val adapter = MyAdapter(dataRepo.getData())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val recView = _binding.recView
        recView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = DataRepo.getInstance().getData()?.let { MyAdapter(it) }
        recView.adapter = adapter

        return _binding.root
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
                    "name" to currData.text_name,
                    "spec" to currData.text_spec,
                    "strength" to currData.item_strength,
                    "danger" to currData.dangerous,
                    "type" to currData.item_type,
                    "humanoids" to currData.humanoids
         */
        parentFragmentManager.setFragmentResultListener("addNewItem", viewLifecycleOwner){ string, bundle ->
            run {
                if (bundle.getBoolean("toAdd")){
                    val itemName = bundle.getString("name", "New person")
                    val itemSpec = bundle.getString("spec", "Some spec")
                    val itemStrength = bundle.getFloat("strength", 1.0F)
                    val itemDanger = bundle.getBoolean("danger", false)
                    val itemType = bundle.getString("type", "Human")
                    val newItem = DataItem(itemName, itemSpec, itemStrength, itemType, itemDanger)
                    dataRepo.addItem(newItem)
                }
            }
        }

        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_item_add -> {
                //Toast.makeText(requireActivity(), "new item clicked!", Toast.LENGTH_SHORT).show()
                //go to adder fragment
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class MyAdapter(var data: MutableList<DataItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding : ListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val txt1: TextView = viewBinding.itemTitle
            val txt2: TextView = viewBinding.itemSpec1
            val img: ImageView = viewBinding.itemImg
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var currData = data[position]
            holder.txt1.text = currData.text_name
            holder.txt2.text = if (currData.text_spec=="Default specification"){
                (currData.item_type + " " + currData.text_spec +" "+ currData.item_strength)
                }else{currData.text_spec}
            holder.itemView.setOnClickListener {
                parentFragmentManager.setFragmentResult("msgtochild", bundleOf(
                    "name" to currData.text_name,
                    "spec" to currData.text_spec,
                    "strength" to currData.item_strength,
                    "danger" to currData.dangerous,
                    "type" to currData.item_type,
                    "humanoids" to currData.humanoids
                    )
                )



                findNavController().navigate(R.id.action_listFragment_to_showFragment)

                /*
                //temp for clicking an object
                Toast.makeText(requireContext(),
                    "You clicked: " + (position + 1),

                    Toast.LENGTH_SHORT).show()
                 */
            }
            holder.itemView.setOnLongClickListener {
                if (dataRepo.deleteItem(position))
                    notifyDataSetChanged()
                true
            }

            when (currData.item_type) {
                "Human" -> holder.img.setImageResource(R.drawable.human)
                "NPC" -> holder.img.setImageResource(R.drawable.npc)
                "Orc" -> holder.img.setImageResource(R.drawable.enemy)
            }
        }
    }




    companion object {
        //val publicRepo = this.dataRepo

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}