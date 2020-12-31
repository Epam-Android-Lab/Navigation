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
import ru.anfilek.navhomework.databinding.FragmentDialogWithNoRationaleBinding
import ru.anfilek.navhomework.databinding.FragmentDialogWithRationaleBinding

private const val ARG_PARAM1 = "rationale"

class ExplanationDialogFragment : DialogFragment() {
    private var withRationale: Boolean = false

    private var bindingWithRationale: FragmentDialogWithRationaleBinding? = null
    private var bindingWithNoRationale: FragmentDialogWithNoRationaleBinding? = null

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
            bindingWithRationale = FragmentDialogWithRationaleBinding.inflate(inflater, container, false)
            bindingWithRationale?.root
        } else {
            bindingWithNoRationale = FragmentDialogWithNoRationaleBinding.inflate(inflater, container, false)
            bindingWithNoRationale?.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (withRationale) {
            bindingWithRationale?.buttonRetry?.setOnClickListener {
                (activity as IButtonRetryListener).clicked()
                dialog?.dismiss()
            }
            bindingWithRationale?.buttonDismiss?.setOnClickListener {
                dialog?.dismiss()
            }
        } else {
            bindingWithNoRationale?.buttonDismiss?.setOnClickListener {
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