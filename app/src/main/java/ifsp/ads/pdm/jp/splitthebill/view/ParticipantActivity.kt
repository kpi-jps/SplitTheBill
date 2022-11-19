package ifsp.ads.pdm.jp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ifsp.ads.pdm.jp.splitthebill.databinding.ActivityParticipantBinding
import ifsp.ads.pdm.jp.splitthebill.model.Constant.EXTRA_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Constant.VIEW_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Participant
import kotlin.random.Random

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
                    nameEt.setText(name)
                    paidValueEt.setText(paidValue.toString())
                    itemsEt.setText(items)
                }
            }
        }
        val viewParticipant = intent.getBooleanExtra(VIEW_PARTICIPANT, false)
        if(viewParticipant) {
            pab.nameEt.isEnabled = false
            pab.paidValueEt.isEnabled = false
            pab.itemsEt.isEnabled = false
            pab.saveBtn.visibility = View.GONE
        }

        pab.saveBtn.setOnClickListener {
            val participant = Participant(
                id = receivedParticipant?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = pab.nameEt.text.toString(),
                paidValue =  (pab.paidValueEt.text.toString()).toDouble(),
                items = pab.itemsEt.text.toString()

            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_PARTICIPANT, participant)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}