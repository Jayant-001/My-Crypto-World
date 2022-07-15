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
import com.jayant.mycryptoworld.databinding.FragmentTopGainLossBinding
import com.jayant.mycryptoworld.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class TopGainLossFragment : Fragment() {

    private lateinit var  binding : FragmentTopGainLossBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopGainLossBinding.inflate(layoutInflater, container, false)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {

        val position = requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO) {

            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null) {
                withContext(Dispatchers.Main) {

                    val dataItem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataItem) {
                        a, b -> (b.quotes[0].percentChange24h.toInt())
                        .compareTo(a.quotes[0].percentChange24h.toInt())
                    }

                    binding.spinKitView.visibility = GONE
                    val list = ArrayList<CryptoCurrency>()

                    if(position == 0) {
                        list.clear()
                        for(i in 0..19) {
                            list.add(dataItem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), list, "home")
                    }
                    else {
                        list.clear()
                        for(i in 0..19) {
                            list.add(dataItem[dataItem.size-i-1])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), list, "home")
                    }

                }
            }
        }
    }

}