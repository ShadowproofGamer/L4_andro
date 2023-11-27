package com.example.l4_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.core.os.bundleOf
import com.example.l4_andro.databinding.FragmentAddBinding
import org.w3c.dom.Text

class AddFragment : Fragment() {
    private lateinit var _binding: FragmentAddBinding
    lateinit var addName: EditText
    lateinit var addSpec: EditText
    lateinit var addStrength: RatingBar
    lateinit var addType: RadioGroup
    lateinit var addDanger: CheckBox
    lateinit var saveButton: Button
    lateinit var cancelButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        addName = _binding.addName
        addSpec = _binding.addSpec
        addStrength = _binding.addStrengthBar
        addType = _binding.addRadio
        addDanger = _binding.addDanger
        saveButton = _binding.addSaveButton
        cancelButton=_binding.addCancelButton

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelButton.setOnClickListener { requireActivity().onBackPressed() }
        /*
        saveButton.setOnClickListener {
         parentFragmentManager.setFragmentResult("msgtochild", bundleOf(
            "name" to addName.text,
            "spec" to addSpec.text,
            "strength" to addStrength.rating,
            "danger" to currData.dangerous,
            "type" to currData.item_type,
            "humanoids" to currData.humanoids
        )
        )
         }


         */
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}