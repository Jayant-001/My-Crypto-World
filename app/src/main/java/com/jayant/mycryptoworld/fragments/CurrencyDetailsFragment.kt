package com.jayant.mycryptoworld.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jayant.mycryptoworld.R
import com.jayant.mycryptoworld.databinding.FragmentCurrencyDetailsBinding
import com.jayant.mycryptoworld.models.CryptoCurrency

class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding
    private val item : CurrencyDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyDetailsBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val data : CryptoCurrency = item.currencyData!!

        setCurrencyDetails(data)

        loadChart(data)

        setButtonOnClick(data)
        
        setBottomDetails(data)

        binding.backStackButton.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.switch1.setOnCheckedChangeListener { compoundButton, b ->

            if(b) {
                binding.detailChart.visibility = VISIBLE
                Toast.makeText(requireContext(), "Chart ON", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.detailChart.visibility = GONE
                Toast.makeText(requireContext(), "Chart OFF", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setBottomDetails(data: CryptoCurrency) {

        binding.textRanking.text = data.cmcRank.toString()
        binding.textCircularSypply.text = data.circulatingSupply.toString()
        binding.textTotalSupply.text = data.totalSupply.toString()
        binding.textMaxSupply.text = data.maxSupply.toString()
        binding.textMarketCapt.text = data.quotes[0].marketCap.toString()
        binding.textChange1h.text = "${String.format("%.02f", data.quotes[0].percentChange1h)}%"
        binding.textChange24h.text = "${String.format("%.02f", data.quotes[0].percentChange24h)}%"
        binding.textChange7D.text = "${String.format("%.02f", data.quotes[0].percentChange7d)}%"
        binding.textChange30D.text = "${String.format("%.02f", data.quotes[0].percentChange30d)}%"
        binding.textChange60D.text = "${String.format("%.02f", data.quotes[0].percentChange60d)}%"
        binding.textChange90D.text = "${String.format("%.02f", data.quotes[0].percentChange90d)}%"

    }

    private fun setButtonOnClick(item: CryptoCurrency) {

        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fivtMin = binding.button5

        val clickListener = View.OnClickListener {

            when(it.id) {
                fivtMin.id -> loadCharData(it, "15", item, oneDay, oneMonth, oneWeek, fourHour, oneHour)
                oneHour.id -> loadCharData(it, "1H", item, oneDay, oneMonth, oneWeek, fourHour, fivtMin)
                fourHour.id -> loadCharData(it, "4H", item, oneDay, oneMonth, oneWeek, fivtMin, oneHour)
                oneDay.id -> loadCharData(it, "D", item, fivtMin, oneMonth, oneWeek, fourHour, oneHour)
                oneWeek.id -> loadCharData(it, "W", item, oneDay, oneMonth, fivtMin, fourHour, oneHour)
                oneMonth.id -> loadCharData(it, "M", item, oneDay, fivtMin, oneWeek, fourHour, oneHour)
            }
        }

        fivtMin.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)

    }

    private fun loadCharData(
        it: View?,
        s: String,
        item: CryptoCurrency,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {

        it!!.setBackgroundResource(R.drawable.active_button)
        disableButton(oneDay, oneMonth, oneWeek, fourHour, oneHour)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + item.symbol
                .toString() + "USD&interval="+ s +"&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg"+
                    "=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides="+
                    "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap."+
                    "com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun disableButton(oneDay: AppCompatButton, oneMonth: AppCompatButton, oneWeek: AppCompatButton, fourHour: AppCompatButton, oneHour: AppCompatButton) {

        oneDay.background = null
        oneMonth.background = null
        oneWeek.background = null
        fourHour.background = null
        oneHour.background = null

    }

    private fun loadChart(data: CryptoCurrency) {

        binding.detaillChartWebView.settings.javaScriptEnabled = true

        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" + data.symbol
                .toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg"+
                    "=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides="+
                    "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap."+
                    "com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )

    }

    private fun setCurrencyDetails(data: CryptoCurrency) {

        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext())
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png")
            .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)


        binding.detailPriceTextView.text = "${String.format("$%.04f", data.quotes[0].price)}"

        Log.d("jayant", "setCurrencyDetails: ${data.quotes[0]}")
        if(data.quotes!![0].percentChange24h > 0) {

//            binding.detailPriceTextView.text = "+${String.format("%.02f", data.quotes[0].price)}"
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeTextView.text = "+${String.format("%.02f", data.quotes[0].percentChange24h)}%"
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
        }
        else {

//            binding.detailPriceTextView.text = "${String.format("%.02f", data.quotes[0].price)}"
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeTextView.text = "${String.format("%.02f", data.quotes[0].percentChange24h)}%"
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
        }

    }

}