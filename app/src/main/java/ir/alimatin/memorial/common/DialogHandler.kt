package ir.alimatin.memorial.common

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import ir.alimatin.memorial.R


class DialogHandler {

    var mDialogResult: OnMyDialogResult? = null

    interface OnMyDialogResult {
        fun finish(result: String?)
    }

    fun setDialogResult(dialogResult: OnMyDialogResult) {
        mDialogResult = dialogResult
    }

    fun showDialogDatePicker(context: Context?): Dialog {
        val mDialog = createMDialog(R.layout.dialog_date_picker, context)
        val btnSubmit = mDialog.findViewById<Button>(R.id.btnSubmit)
        val datePicker = mDialog.findViewById<DatePicker>(R.id.datePicker)
        btnSubmit.setOnClickListener { v: View? ->
            mDialogResult?.finish("${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}T00:00:00+00:00")
            mDialog.dismiss()
        }
        return mDialog
    }

    fun showDialogPaymentAmount(context: Context?): Dialog {
        val mDialog = createMDialog(R.layout.dialog_paymeny_amount, context)
        val btnSubmit = mDialog.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener { v: View? -> mDialog.dismiss() }
        return mDialog
    }

    fun showDialogLocationSystem(context: Context?): Dialog {
        val mDialog = createMDialog(R.layout.dialog_location_system, context)
        val btnSubmit = mDialog.findViewById<Button>(R.id.btnSubmit)
        val btnCancel = mDialog.findViewById<Button>(R.id.btnCancel)
        btnSubmit.setOnClickListener {
            if (context != null) {
                //context.toast("Hello world!")
                context.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            mDialog.dismiss()
        }
        btnCancel.setOnClickListener { v: View? -> mDialog.dismiss() }
        return mDialog
    }

    fun showDialogAdvancedSetting(context: Context?): Dialog {
        val mDialog = createMDialog(R.layout.dialog_advanced_setting, context)
        val btnSubmit = mDialog.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener { v: View? -> mDialog.dismiss() }
        return mDialog
    }

    fun showDialogSetAsDefult(context: Context?): Dialog {
        val mDialog = createMDialog(R.layout.dialog_set_as_deafult, context)
        val btnSubmit = mDialog.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener { v: View? -> mDialog.dismiss() }
        return mDialog
    }

    fun showDialogDotDirect(context: Context?): Dialog {
        val mDialog = createDotDialog(R.layout.dot_direct, context)
        val ll_DotDirect = mDialog.findViewById<LinearLayout>(R.id.dot_direct)
        ll_DotDirect.setOnClickListener { v: View? -> mDialog.dismiss() }
        return mDialog
    }

    private fun createMDialog(layoutRes: Int, context: Context?): Dialog {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(layoutRes)
        val window = dialog.window
        window!!.attributes.gravity = Gravity.CENTER
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        //window.getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialog.show()
        return dialog
    }

    private fun createDotDialog(layoutRes: Int, context: Context?): Dialog {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(layoutRes)
        val window = dialog.window
        window!!.attributes.gravity = Gravity.BOTTOM
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        //window.getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialog.show()
        return dialog
    }

    private fun createMDialogUnClose(layoutRes: Int, context: Context?): Dialog {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(layoutRes)
        val window = dialog.window
        window!!.attributes.gravity = Gravity.CENTER
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        //window.getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialog.show()
        return dialog
    }
}