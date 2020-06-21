package com.hotelgis

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_pesan_kamar.*
import java.util.*

class PesanKamarActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan_kamar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        et_tanggal.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            var day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            var month: Int = cldr.get(Calendar.MONTH)
            var year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            var picker: DatePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    et_tanggal.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                },
                year,
                month,
                day
            )
            picker.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}