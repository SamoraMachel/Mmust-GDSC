package com.presentation.ui.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.members.adapters.MembersAdapter
import com.presentation.ui.members.viewmodels.MembersViewModel
import com.presentation.ui.states.MemberUIState
import com.app.mmustgdsc.databinding.FragmentLeadsListBinding


class LeadsListFragment : Fragment() {
    private var _binding : FragmentLeadsListBinding? = null
    private val binding get() = _binding!!

    private val membersViewModel : MembersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeadsListBinding.inflate(inflater, container, false)

        binding.leadsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        loadLeads()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadLeads() {
        membersViewModel.leadDataList.observe(viewLifecycleOwner) { memberState ->
            when(memberState) {
                is MemberUIState.Failure -> {
                    binding.leadsLoader.visibility = View.GONE
                    showSnackBar("Could not get leads.\n${memberState.message}")
                }
                MemberUIState.Loading -> binding.leadsLoader.visibility = View.VISIBLE
                MemberUIState.StandBy -> Unit
                is MemberUIState.Success -> {
                    binding.leadsLoader.visibility = View.GONE
                    binding.leadsRecyclerView.adapter = memberState.data?.let { MembersAdapter(it) }
                }
            }
        }
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

}