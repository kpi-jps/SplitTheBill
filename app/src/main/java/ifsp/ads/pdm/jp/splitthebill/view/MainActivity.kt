package ifsp.ads.pdm.jp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ifsp.ads.pdm.jp.splitthebill.R
import ifsp.ads.pdm.jp.splitthebill.adapter.MainActivityAdapter
import ifsp.ads.pdm.jp.splitthebill.databinding.ActivityMainBinding
import ifsp.ads.pdm.jp.splitthebill.model.Constant.EXTRA_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Constant.EXTRA_PARTICIPANT_LIST
import ifsp.ads.pdm.jp.splitthebill.model.Constant.VIEW_PARTICIPANT
import ifsp.ads.pdm.jp.splitthebill.model.Participant

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val participantList = mutableListOf<Participant>()

    private lateinit var adapter: MainActivityAdapter

    private lateinit var carl : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        adapter = MainActivityAdapter(this, participantList)
        amb.mainLv.adapter = adapter
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
                        adapter.notifyDataSetChanged()
                    }
                }
        }

        registerForContextMenu(amb.mainLv)

        amb.mainLv.onItemClickListener = AdapterView.OnItemClickListener {_, _, pos, _ ->
            val participant = participantList[pos]
            val participantIntent = Intent(this@MainActivity, ParticipantActivity::class.java)
            participantIntent.putExtra(EXTRA_PARTICIPANT, participant)
            participantIntent.putExtra(VIEW_PARTICIPANT, true)
            startActivity(participantIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addParticipantMi -> {
                carl.launch(Intent(this, ParticipantActivity::class.java))
                true
            }
            R.id.calculateMi -> {
                val splitIntent = Intent(this, SplitActivity::class.java)
                val participantArrayList = ArrayList<Participant>()
                participantArrayList.addAll(participantList)
                splitIntent.putParcelableArrayListExtra(EXTRA_PARTICIPANT_LIST, participantArrayList)
                startActivity(splitIntent)
                //carl.launch(splitIntent);
                true

            }
            else -> { false}
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.removeParticipatMi -> {
               participantList.removeAt(pos)
                adapter.notifyDataSetChanged()
                true
            }
            R.id.editParticipantMi -> {
                val participant = participantList[pos]
                val participantIntent = Intent(this, ParticipantActivity::class.java)
                participantIntent.putExtra(EXTRA_PARTICIPANT, participant)
                participantIntent.putExtra(VIEW_PARTICIPANT, false)
                carl.launch(participantIntent)
                true
            }
            else -> { false}
        }
    }
}