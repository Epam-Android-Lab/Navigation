package ru.anfilek.navhomework

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.message_permission_denyed))
                .setMessage(getString(R.string.message_for_user_info))
                .setPositiveButton(getString(R.string.intent_settings)) {
                        _, _ ->  startActivity(Intent(Settings.ACTION_SETTINGS))
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
