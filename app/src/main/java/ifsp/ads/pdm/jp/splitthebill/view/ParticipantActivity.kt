package ifsp.ads.pdm.jp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ifsp.ads.pdm.jp.splitthebill.databinding.ActivityParticipantBinding
import ifsp.ads.pdm.jp.splitthebill.model.Constant.EXTRA_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Participant

class ParticipantActivity : AppCompatActivity() {

    private val pab : ActivityParticipantBinding by lazy{
        ActivityParticipantBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(pab.root)

        val receivedParticipant = intent.getParcelableExtra<Participant>(EXTRA_PARTICIPANT)
        receivedParticipant?.let { _receivedParticipant ->
            with(pab) {
                with(_receivedParticipant) {
                    nameEt.setText(name);
                    paidValueEt.setText(paidValue.toString())
                    itemsEt.setText(items) //parei aqui
                }
            }
        }
    }
}