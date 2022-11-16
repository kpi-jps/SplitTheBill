package ifsp.ads.pdm.jp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ifsp.ads.pdm.jp.splitthebill.adapter.ParticipantAdapter
import ifsp.ads.pdm.jp.splitthebill.databinding.ActivityMainBinding
import ifsp.ads.pdm.jp.splitthebill.model.Constant.EXTRA_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Constant.VIEW_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Participant

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val participantList = mutableListOf<Participant>()

    private lateinit var participantAdapter: ParticipantAdapter

    private lateinit var carl : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        participantAdapter = ParticipantAdapter(this, participantList)
        amb.appLv.adapter = participantAdapter
        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ) {
            result ->
                if(result.resultCode == RESULT_OK) {
                    val participant = result.data?.getParcelableExtra<Participant>(EXTRA_PARTICIPANT)
                    participant?.let { _participant ->
                        val pos = participantList.indexOfFirst { it.id == _participant.id }
                        if (pos != -1) {
                            participantList[pos] = _participant
                        } else {
                            participantList.add(_participant)
                        }
                        participantAdapter.notifyDataSetChanged()
                    }
                }
        }

        registerForContextMenu(amb.appLv)

        amb.appLv.onItemClickListener = AdapterView.OnItemClickListener {_, _, pos, _ ->
            val participant = participantList[pos]
            val participantIntent = Intent(this@MainActivity, ParticipantActivity::class.java)
            participantIntent.putExtra(EXTRA_PARTICIPANT, participant)
            participantIntent.putExtra(VIEW_PARTICIPANT, true)
            startActivity(participantIntent)
        }

    }
}