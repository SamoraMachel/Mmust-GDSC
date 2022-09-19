package com.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.presentation.models.LevelPresentation
import com.presentation.models.ProfilePresentation
import com.presentation.models.TrackPresentation
import com.presentation.ui.home.adapters.ResourceLevelAdapter
import com.presentation.ui.home.viewmodels.ResourceViewModel
import com.presentation.ui.states.SingleProfileUIState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentResourceLevelBinding


class ResourceLevelFragment : Fragment() {
    private var _binding : FragmentResourceLevelBinding? = null
    private val binding get() = _binding!!

    private val args : ResourceLevelFragmentArgs by navArgs()
    private val resourceViewModel : ResourceViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResourceLevelBinding.inflate(inflater, container, false)

        resourceViewModel.getLeadData(args.track.lead)

        binding.resourceLevelTitle.text = args.track.title
        binding.resourceLevelDescription.text = args.track.description

        val levels : MutableList<LevelPresentation> = mutableListOf()
        args.track.levels.forEach { data ->
            levels.add(
                LevelPresentation(data.key, data.value)
            )
        }
        val sortedLevels = levels.sortedWith(levelComparator)

        binding.resourceLevelRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.resourceLevelRecyclerView.adapter = ResourceLevelAdapter(sortedLevels, args.track.title)

        resourceViewModel.leadProfile.observe(viewLifecycleOwner) { profileState ->
            when(profileState) {
                is SingleProfileUIState.Failure -> {
                    binding.leadProfileLoader.visibility = View.GONE
                    showSnackBar("Could not load profile data.\n${profileState.message}")
                }
                SingleProfileUIState.Loading -> binding.leadProfileLoader.visibility = View.VISIBLE
                SingleProfileUIState.StandBy -> Unit
                is SingleProfileUIState.Success -> {
                    binding.leadProfileLoader.visibility = View.GONE
                    profileState.data?.let { profile -> setupLeadData(profile) }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val levelComparator = Comparator { level_1 : LevelPresentation , level_2 : LevelPresentation ->
        when {
            level_1.title.contains("Beginner") && level_2.title.contains("Intermediate") -> 1
            level_2.title.contains("Beginner") && level_2.title.contains("Expert") -> 3
            level_1.title.contains("Intermediate") && level_2.title.contains("Expert") -> 1
            else -> 0
        }
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

    private fun setupLeadData(lead : ProfilePresentation) {
        Glide.with(binding.root.context)
            .load(lead.profileImage)
            .into(binding.resourceLeadImage)

        binding.resourceLeadName.text = lead.name
        binding.resourceLeadRole.text = "${args.track.title} Lead"
    }
}