package ifsp.ads.pdm.jp.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ifsp.ads.pdm.jp.splitthebill.R
import ifsp.ads.pdm.jp.splitthebill.model.Participant

class MainActivityAdapter (
    context : Context,
    private val participantList : MutableList<Participant>
        ) : ArrayAdapter<Participant>(context, R.layout.tile_participant_info, participantList) {
    private data class TileAppHolder(val nameTv: TextView, val valuePaidTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val participant = participantList[position]
        var participantTileView = convertView
        if (participantTileView == null) {
            participantTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tile_participant_info, parent, false )
            val tileAppHolder = TileAppHolder(
                participantTileView.findViewById(R.id.nameTv),
                participantTileView.findViewById(R.id.paymentTv),
                )
            participantTileView.tag = tileAppHolder
        }
        with(participantTileView?.tag as TileAppHolder) {
            nameTv.text = participant.name
            valuePaidTv.text = participant.paidValue.toString()
        }
        return participantTileView
    }
}