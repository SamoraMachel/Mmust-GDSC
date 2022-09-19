package com.presentation.ui.members

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val args : ProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val toolbar = requireParentFragment().activity?.findViewById<ConstraintLayout>(R.id.toolbarConstraint)
        toolbar?.visibility = View.GONE

        Glide.with(requireContext())
            .load(args.profile.profileImage)
            .into(binding.profileImage)

        binding.profileUserName.text = args.profile.name
        binding.profileRole.text = args.profile.profession
        binding.profileDescription.text = args.profile.description

        args.profile.instagram.let { instagram ->
            binding.instagramIcon.visibility = View.VISIBLE
            binding.instagramIcon.setOnClickListener {
                openIntent(Uri.parse("https://www.instagram.com/${instagram}"))
            }
        }

        args.profile.twitter.let { twitter ->
            binding.twitterIcon.visibility = View.VISIBLE
            binding.twitterIcon.setOnClickListener {
                openIntent(Uri.parse("https://twitter.com/{$twitter}"))
            }
        }

        args.profile.linkedin.let { linkedin ->
            binding.linkedInIcon.visibility = View.VISIBLE
            binding.linkedInIcon.setOnClickListener {
                openIntent(Uri.parse("https://www.linkedin.com/${linkedin}"))
            }
        }

        args.profile.dribble.let { dribble ->
            binding.drbbleIcon.visibility = View.VISIBLE
            binding.drbbleIcon.setOnClickListener {
                openIntent(Uri.parse("https://dribbble.com/${dribble}"))
            }
        }

        args.profile.github.let { github ->
            binding.githubIcon.visibility = View.VISIBLE
            binding.githubIcon.setOnClickListener {
                openIntent(Uri.parse("https://github.com/${github}"))
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openIntent(_uri : Uri) {
        val intent = Intent(Intent.ACTION_VIEW, _uri)
        startActivity(intent)
    }
}