package com.movzikri.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.movzikri.bwamov.R
import com.movzikri.bwamov.checkout.adapter.CheckoutAdapter
import com.movzikri.bwamov.checkout.model.Checkout
import com.movzikri.bwamov.home.HomeActivity
import com.movzikri.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Harus Dibayar", total.toString()))

        btn_tiket.setOnClickListener {
            val intent = Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }

        iv_back.setOnClickListener{
            finish()
        }

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@CheckoutActivity, HomeActivity::class.java)
            startActivity(intent)
        }


        rc_checkout.layoutManager = LinearLayoutManager(this)
        rc_checkout.adapter = CheckoutAdapter(dataList) {

        }

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        tv_saldo.setText(formatRupiah.format(preferences.getValues("saldo")!!.toDouble()))
    }
}
