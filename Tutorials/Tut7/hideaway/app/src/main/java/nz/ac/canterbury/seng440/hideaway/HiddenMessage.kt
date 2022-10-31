package nz.ac.canterbury.seng440.hideaway

import com.google.android.gms.maps.model.LatLng

data class HiddenMessage(val message: String, val location: LatLng) {
    val guesses = mutableListOf<LatLng>()
    override fun toString() = "$message | $location | $guesses"
}




