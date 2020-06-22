package com.hotelgis

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hotelgis.model.Room
import kotlinx.android.synthetic.main.activity_pesan_kamar.*
import java.text.SimpleDateFormat
import java.util.*

class PesanKamarActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_ROOM = "EXTRA_DETAIL_ROOM"
    }

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
            if (et_tanggal.text.toString() != "" && et_tanggal.text != null &&
                et_jumlah_kamar.text.toString() != "" && et_jumlah_kamar.text != null &&
                et_nama_pemesan.text.toString() != "" && et_nama_pemesan.text != null &&
                et_no_telepon.text.toString() != "" && et_no_telepon.text != null) {
                val room = intent.getParcelableExtra<Room>(EXTRA_DETAIL_ROOM)
                val dateBook: Date = SimpleDateFormat("dd/MM/yyyy").parse(et_tanggal.text.toString())
                if (et_jumlah_kamar.text.toString().toInt() <= room.quantity)
                    if (System.currentTimeMillis() < dateBook.time ||
                        (Date().date.compareTo(dateBook.date)==0)) {
                        val newIntent =
                            Intent(this, KonfirmasiPemesananActivity::class.java).apply {
                                putExtra("ROOM_PLACE", room.place)
                                putExtra("ROOM_CODE", room.code)
                                putExtra("ROOM_NAME", room.name)
                                putExtra("ROOM_COST", room.cost)
                                putExtra("PESAN_TANGGAL", et_tanggal.text.toString())
                                putExtra(
                                    "PESAN_JUMLAH_KAMAR",
                                    et_jumlah_kamar.text.toString().toInt()
                                )
                                putExtra("PESAN_NAMA_PEMESAN", et_nama_pemesan.text.toString())
                                putExtra("PESAN_NO_TELP", et_no_telepon.text.toString())
                            }
                        startActivity(newIntent)
                    } else {
                        Toast.makeText(baseContext, "Tanggal booking telah terlalui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Jumlah Kamar yang tersedia hanya " + room.quantity, Toast.LENGTH_SHORT).show()
                }
            }
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