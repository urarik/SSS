package org.sehproject.sss.view

import android.content.Context
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException


class NoticeDialogFragment: DialogFragment() {
    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch(e: ClassCastException) {
            throw ClassCastException(("$context must implement NoticeDialogListner"))
        }
    }
}