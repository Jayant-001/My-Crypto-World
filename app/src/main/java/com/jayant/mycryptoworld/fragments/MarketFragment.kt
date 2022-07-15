package com.jayant.mycryptoworld.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jayant.mycryptoworld.R
import com.jayant.mycryptoworld.adapters.MarketAdapter
import com.jayant.mycryptoworld.api.ApiInterface
import com.jayant.mycryptoworld.api.ApiUtilities
import com.jayant.mycryptoworld.databinding.FragmentMarketBinding
import com.jayant.mycryptoworld.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MarketFragment : Fragment() {

    private lateinit var binding : FragmentMarketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketBinding.inflate(layoutInflater, container, false)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {

        lifecycleScope.launch(Dispatchers.IO) {

            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null) {
                withContext(Dispatchers.Main) {

                    binding.spinKitView.visibility = GONE
                    val dataItem = res.body()!!.data.cryptoCurrencyList
                    binding.currencyRecyclerView.adapter = MarketAdapter(requireContext(), dataItem, "market")

                }
            }
        }
    }

}