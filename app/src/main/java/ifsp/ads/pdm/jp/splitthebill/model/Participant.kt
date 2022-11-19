package ifsp.ads.pdm.jp.splitthebill.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Participant(
    var id: Int,
    var name: String,
    var paidValue: Double,
    var items: String
) : Parcelable
