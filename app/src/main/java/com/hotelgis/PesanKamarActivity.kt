package com.hotelgis

import android.app.DatePickerDialog
import android.content.Intent
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

        btn_pesan_kamar.setOnClickListener {
            val newIntent = Intent(this, KonfirmasiPemesananActivity::class.java).apply{
                putExtra("ROOM_CODE",intent.getStringExtra("ROOM_CODE"))
                putExtra("ROOM_NAME",intent.getStringExtra("ROOM_NAME"))
                putExtra("ROOM_COST",intent.getIntExtra("ROOM_COST", 0))
                putExtra("PESAN_TANGGAL",et_tanggal.text.toString())
                putExtra("PESAN_JUMLAH_KAMAR",et_jumlah_kamar.text.toString().toInt())
                putExtra("PESAN_NAMA_PEMESAN",et_nama_pemesan.text.toString())
                putExtra("PESAN_NO_TELP",et_no_telepon.text.toString())
            }
            startActivity(newIntent)
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