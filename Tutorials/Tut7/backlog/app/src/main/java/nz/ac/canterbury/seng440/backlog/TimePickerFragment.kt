package nz.ac.canterbury.seng440.backlog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, listener, 6, 0, false)
    }

    var listener: TimePickerDialog.OnTimeSetListener? = null
}

