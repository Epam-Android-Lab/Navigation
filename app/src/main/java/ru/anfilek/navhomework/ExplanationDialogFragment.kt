package ru.anfilek.navhomework

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment

private const val ARG_PARAM1 = "rationale"

class ExplanationDialogFragment : DialogFragment() {
    private var withRationale: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            withRationale = it.getBoolean(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let {
            isCancelable = false
            it.window?.run {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                requestFeature(Window.FEATURE_NO_TITLE)
            }
        }

        return if (withRationale) {
            inflater.inflate(R.layout.fragment_dialog_with_rationale, container, false)
        } else {
            inflater.inflate(R.layout.fragment_dialog_with_no_rationale, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_dismiss).setOnClickListener {
            dialog?.dismiss()
        }
        if (withRationale) {
            view.findViewById<Button>(R.id.button_retry).setOnClickListener {
                (activity as IButtonRetryListener).clicked()
                dialog?.dismiss()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isRationale: Boolean) =
            ExplanationDialogFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, isRationale)
                }
            }
    }
}