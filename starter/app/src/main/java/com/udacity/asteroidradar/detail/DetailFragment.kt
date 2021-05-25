package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this


//        val application = requireNotNull(this.activity).application

        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid //use requireArguments()?
        binding.asteroid = asteroid

//        val viewModelFactory = DetailViewModelFactory(asteroid, application)

//        // Get a reference to the ViewModel associated with this fragment.
//        val detailViewModel =
//            ViewModelProvider(
//                this, viewModelFactory).get(DetailViewModel::class.java)
//
//        binding.detailViewModel = detailViewModel

        //Help button for more info
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!) //Use requireActivity()?
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
