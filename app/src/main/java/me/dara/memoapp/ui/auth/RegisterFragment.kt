package me.dara.memoapp.ui.auth


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_register.*
import me.dara.memoapp.ui.view.Alert
import me.dara.memoapp.ui.view.ProgressDialog
import me.dara.memoapp.R
import me.dara.memoapp.network.model.Status


class RegisterFragment : Fragment() {

  lateinit var listener: OnLoginClickListener
  val progress: ProgressDialog by lazy {
    ProgressDialog().apply {
      isCancelable = false
    }
  }

  lateinit var viewModel: AuthViewModel


  override fun onAttach(context: Context) {
    super.onAttach(context)
    listener = context as OnLoginClickListener
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register, container, false)

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(activity!!).get(AuthViewModel::class.java)
    imgCloseRegister.setOnClickListener {
      listener.onCloseClicked()
    }

    btnRegister.setOnClickListener {
      if (validateInput()) {
        signUp(editRegisterEmail.text?.toString()!!, editConfirmPassword.text?.toString()!!)
      }
    }

    editConfirmPassword.addTextChangedListener(object : FormTextWatcher() {
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! > 0) {
          inputRegisterConfirmPassword.error = ""
        }
      }
    })

    editRegisterEmail.addTextChangedListener(object : FormTextWatcher() {
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! > 0) {
          inputRegisterEmail.error = ""
        }
      }
    })
    editRegisterPassword.addTextChangedListener(object : FormTextWatcher() {
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! > 0) {
          inputRegisterPassword.error = ""
        }
      }
    })

  }

  fun signUp(email: String, password: String) {
    progress.show(childFragmentManager, "ProgressLoading")
    viewModel.signUp(email, password).observe(viewLifecycleOwner, Observer { response ->
      progress.dismiss()
      if (response.status == Status.SUCCESS) {
        val msg = getString(R.string.email_sent, email)
        val title = getString(R.string.info)
        Alert().apply {
          this.msg = msg
          this.title = title
          positiveFun = {
            listener.registerSuccess()
          }
        }.show(childFragmentManager, "Alert")
      } else {
        Toast.makeText(context, R.string.registration_error, Toast.LENGTH_SHORT).show()
      }
    })
  }

  private fun validateInput(): Boolean {
    val email = editRegisterEmail.text?.toString()!!
    val password = editRegisterPassword.text?.toString()!!
    val confirm = editConfirmPassword.text?.toString()!!
    var isValid = true
    if (email.isEmpty()) {
      inputRegisterEmail.error = requireContext().resources.getString(R.string.fill_username)
      isValid = false
    }
    if (password.isEmpty()) {
      inputRegisterPassword.error = requireContext().resources.getString(R.string.fill_password)
      isValid = false
    }
    if (confirm.isEmpty()) {
      inputRegisterConfirmPassword.error = requireContext().resources.getString(R.string.fill_password)
      isValid = false
    }

    return isValid
  }


}