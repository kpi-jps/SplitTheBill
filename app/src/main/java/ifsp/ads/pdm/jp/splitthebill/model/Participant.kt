package ifsp.ads.pdm.jp.splitthebill.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Participant(
    val id: Int,
    var name: String,
    var paidValue: Double,
    var receiveOrPaid: Double,
    var items: String
) : Parcelable
