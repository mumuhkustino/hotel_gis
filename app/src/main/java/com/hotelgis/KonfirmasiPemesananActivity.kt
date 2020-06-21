package com.hotelgis

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hotelgis.admin.ui.RoomPaymentActivity
import kotlinx.android.synthetic.main.activity_konfirmasi_pemesanan.*

class KonfirmasiPemesananActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_pemesanan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val roomPlace = intent.getStringExtra("ROOM_PLACE")
        val roomCode = intent.getStringExtra("ROOM_CODE")
        val roomName = intent.getStringExtra("ROOM_NAME")
        val roomCost = intent.getIntExtra("ROOM_COST", 0)
        val pesanTanggal = intent.getStringExtra("PESAN_TANGGAL")
        val pesanJumlahKamar = intent.getIntExtra("PESAN_JUMLAH_KAMAR", 0)
        val pesanNamaPemesan = intent.getStringExtra("PESAN_NAMA_PEMESAN")
        val pesanNoTelepon = intent.getStringExtra("PESAN_NO_TELP")

        tv_tanggal.text = pesanTanggal
        tv_jumlah_kamar.text = pesanJumlahKamar.toString()
        tv_nama_pemesan.text = pesanNamaPemesan
        tv_no_telepon.text = pesanNoTelepon
        tv_room_cost.text = "Rp $roomCost"
        tv_kamar_n_hotel.text = "$roomName - $roomPlace"
        tv_jumlah_kamar_harga.text = "(Jumlah: $pesanJumlahKamar Kamar)"
        tv_harga_total.text = "Rp ${pesanJumlahKamar * roomCost}"

        btn_pembayaran.setOnClickListener {
            val intent = Intent(this, RoomPaymentActivity::class.java)
            startActivity(intent)
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