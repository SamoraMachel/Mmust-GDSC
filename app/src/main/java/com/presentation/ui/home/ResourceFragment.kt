package com.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.home.adapters.ResourceAdapter
import com.presentation.ui.home.viewmodels.ResourceViewModel
import com.presentation.ui.states.ResourceUIState
import com.app.mmustgdsc.databinding.FragmentResourceBinding

class ResourceFragment : Fragment() {

    private var _binding : FragmentResourceBinding?  = null
    private val binding get() = _binding!!

    private val resourceViewModel : ResourceViewModel by activityViewModels()
    private val args : ResourceFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResourceBinding.inflate(inflater, container, false)

        binding.resourceLevel.text = args.level.title
        binding.resourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        resourceViewModel.getResourceByLevel(args.level.title, args.track)

        resourceViewModel.resourceDataList.observe(viewLifecycleOwner) { observer ->
            when(observer) {
                is ResourceUIState.Failure -> {
                    binding.resourceLoader.visibility = View.GONE
                    showSnackBar("Could not load resources.\n${observer.message}")
                }
                ResourceUIState.Loading -> binding.resourceLoader.visibility = View.VISIBLE
                ResourceUIState.StandBy -> Unit
                is ResourceUIState.Success -> {
                    binding.resourceLoader.visibility = View.GONE
                    binding.resourcesRecyclerView.adapter = observer.data?.let { ResourceAdapter(it) }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}