package ifsp.ads.pdm.jp.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ifsp.ads.pdm.jp.splitthebill.adapter.SplitActivityAdapter
import ifsp.ads.pdm.jp.splitthebill.databinding.ActivitySplitBinding
import ifsp.ads.pdm.jp.splitthebill.model.Constant
import ifsp.ads.pdm.jp.splitthebill.model.Participant

class SplitActivity : AppCompatActivity() {
    private val sab : ActivitySplitBinding by lazy {
       ActivitySplitBinding.inflate(layoutInflater)
    }
    private lateinit var adapter : SplitActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(sab.root)
        val participantArrayList = intent.getParcelableArrayListExtra<Participant>(Constant.EXTRA_PARTICIPANT_LIST)
        var splittedValue = 0.0
        if(!participantArrayList.isNullOrEmpty()) {
            participantArrayList.forEach { participant ->
                splittedValue += participant.paidValue
            }
            adapter = SplitActivityAdapter(this, participantArrayList, splittedValue / participantArrayList.size)
            sab.splitLv.adapter = adapter
        }
    }
}