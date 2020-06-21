package com.hotelgis.admin.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.admin.adapter.ListPaymentMethodAdapter
import com.hotelgis.model.PaymentMethod
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_room_payment.*

class RoomPaymentActivity : AppCompatActivity(), ListPaymentMethodAdapter.OnItemClickCallback{

    companion object {
        const val EXTRA_ROOM_COST = "EXTRA_ROOM_COST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_payment)

        tvRoomCost.text = "Rp. 2.000.000"

        toolbar.title = "Pembayaran"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null) {
            tvRoomCost.text = intent.getStringExtra(EXTRA_ROOM_COST)
        }

        slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN

        btnPaymentMethod.text = loadPaymentMethod()[1].title
        Glide.with(this)
            .load(loadPaymentMethod()[1].image)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.overrideOf(24, 24))
            .into(imgPaymentMethod)

        btnPaymentMethod.setOnClickListener {
            slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }

        btnPay.setOnClickListener {
            //TODO Pay
        }

        rvPaymentMethod.layoutManager = LinearLayoutManager(this)
        val listPaymentMethodAdapter = ListPaymentMethodAdapter(this)
        listPaymentMethodAdapter.listMethodPayment = loadPaymentMethod()
        rvPaymentMethod.adapter = listPaymentMethodAdapter

    }

    private fun loadPaymentMethod(): List<PaymentMethod> {
        var listPaymentMethod: ArrayList<PaymentMethod> = ArrayList()
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.HEADER,
            "Digital Payment",
            null,
            null))
        listPaymentMethod.add(
        PaymentMethod(ListPaymentMethodAdapter.CONTENT,
            null,
            "https://lh3.googleusercontent.com/11cOz-plgXg8oSH7IW-tVam3CehnPT7Z-A8dDAyKsQoWIB1-XY6RClO6nFbVDOsmRw=s180-rw",
            "Ovo Cash"))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.CONTENT,
                null,
                "https://lh3.googleusercontent.com/pqoyI2JaPd3uOYt-5GzVqi82OvhBC9Jf-EPQqPDhCbyekdKZe5r-hOGlF4qE1ddWe3o=s180-rw",
                "Dana"))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.HEADER,
                "Transfer Virtual Account",
                null,
                null))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.CONTENT,
                null,
                "https://cdn.iconscout.com/icon/free/png-256/bca-225544.png",
                "BCA Virtual Account"))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.CONTENT,
                null,
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAYAAABccqhmAAAAAXNSR0IArs4c6QAAJHlJREFUeAHtnQl0HNWVhutVVbck78YB4k2WZRlIDCEDISzBltgmcWaAIYwCeF8keQthSAjDhAR7CAkkkLB4kGRJtrEskwGTEwYmgQSCLRuy4iwE5wxYkhcJG2wTY7xI6q5l/ttyQ7tV1eqWtXSbv85pqfvVW7+qd999922axosESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESIAESKD7BFT3g/ZcyB3zpkwfEDAubnNstzuxBpWuhR1315gx9T9UyzQnPo4dJVM+EVTGrWHX8Suvo5TyTtt1XE0pR8Pf+Hjh5mq62ym9APITst19ucbhe1XVlnCncAkccqctGm5ka6dpbXqbMsPeeUoQvqtbyjDdcLt7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xcUJNYVomN7+nxYCAC9gFk76OQAj/pdH19TfH19oqbxBpf8iYKiCdqz/FDGb7CeyE6Dm6qjIFdgHoCg+bq/f226emtVSWvgjaCPlaAqHShzJphf1BzW722zz5vzbsILsEWuUEVjQ0WqgI4Ay99RHXn5R7/UPK30L0qlFWa9tzbIvaFhT+WDL+pV/92KTjFt+Mc4WPBiu0wOBa/3yL+44jfgK287+Sd6cxR9PJt6e8LOttvJ/NSf0JQj1yMEoPRHnpOJlQSxGegD7EtzpV97os5P7RiAwvX3Ax546Y9bC0T2R/onEkcwI2InEn1TYXaWFX7Mt/ZXxqzccd6CDLKZpGbJvEbrkd8G6b4Id1nUmFeXxntCAm7I3mOPuQGW+BpuEtBzv4cNfW4uLBg0e6twfMIxp0EZkFknKzb9oM7BNNNmWmoJtyDzX1H+Y4vHfCmaXTcBQ2yM4q28yht16YyqxrIE+AB1cVjK+inQ2t5ntr7as7H6Fjy2BVGbDdh+F+MNZA0nkX1dBEH4Vz3aRLO2Njas3v+dPLztHN4y10FDOFSOoCEMnHP5GY92KB1JJd9L8+ae0hoI/AMdiVG4Lr2dS7wv8B6EI/RWdt1u311b+PpU0e9JvWowC5FbX/8izUJMmudrbm37apjtPuSEzsnjf019XjngqMkqI7cXNd4+Y7yXy/knt1NBu+53vtWr6MkzpSeTV956kFczSrLE1v0p5uaylQu8a5oCFdluoNyq/vOhue2vOoZb1D/bKVOWBR7TDbYPUv6WSf0PPCbgB47guny/cHrqB4cy/FhTPvcrNyfkvtNxfjrTQ3Yj7wF6jLXu4fbcTcu6U4EnV/mPpSLmNoJbyO9KNbPoG6U576hsZb5BAxhGA4a6gce8tWsC4x20Pf69xXeV3M64MzDAJkMCJEZBFUPnTF1xxYrEwNAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAmQAAlECfw/eYY0JFixHroAAAAASUVORK5CYII=",
                "BNI Virtual Account"))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.HEADER,
                "Minimarket Retail",
                null,
                null))
        listPaymentMethod.add(
            PaymentMethod(ListPaymentMethodAdapter.CONTENT,
                null,
                "https://obs.line-scdn.net/0hdY0L7BtFO3YNNRYXUHxEITFwNRt6Gz0-dVJ9QyExNkZ0VigiMwN1FiszbBJ1AHkkZVR2Qi43ZU8p",
                "Indomaret"))
        return listPaymentMethod
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(paymentMethod: PaymentMethod) {
        if (paymentMethod.method == null) {
            btnPaymentMethod.text = paymentMethod.title
            Glide.with(this)
                .load(paymentMethod.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.overrideOf(24, 24))
                .into(imgPaymentMethod)
        }
        slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
    }

}
