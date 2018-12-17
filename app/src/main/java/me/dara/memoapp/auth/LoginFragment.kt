package me.dara.memoapp.auth


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import me.dara.memoapp.LoadingDialog
import me.dara.memoapp.R


class LoginFragment : Fragment() {

  var listener: OnLoginClickListener? = null


  lateinit var viewModel: AuthViewModel
  val loading: LoadingDialog by lazy {
    LoadingDialog().apply {
      isCancelable = false
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    listener = context as OnLoginClickListener
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_login, container, false)

    val textRegister = resources.getString(R.string.text_register)
    val register = resources.getString(R.string.register)
    val spannableString = SpannableString(textRegister)
    val startIndex = textRegister.indexOf(register)
    val endIndex = startIndex + register.length
    spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, 0)
    view.text_register.text = spannableString
    view.text_register.setOnClickListener {
      listener?.onRegisterClicked()
    }
    view.btn_login.setOnClickListener {
//      if (validateInput()) {
//        login(edit_username.text?.toString()!!, edit_password.text?.toString()!!)
//      }
      login("xOperatorDragon", "8734798787775633276792")
    }

    view.edit_username.addTextChangedListener(object : FormTextWatcher() {
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! > 0) {
          input_username.error = ""
        }
      }
    })
    view.edit_password.addTextChangedListener(object : FormTextWatcher() {
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! > 0) {
          input_password.error = ""
        }
      }
    })
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(activity!!).get(AuthViewModel::class.java)
  }

  fun login(username: String, password: String) {
    loading.show(childFragmentManager, "LoadingDialog")
  }

  private fun validateInput(): Boolean {
    val username = edit_username.text?.toString()!!
    val password = edit_password.text?.toString()!!
    var isValid = true
    if (username.isEmpty()) {
      input_username.error = requireContext().resources.getString(R.string.fill_username)
      isValid = false
    }
    if (password.isEmpty()) {
      input_password.error = requireContext().resources.getString(R.string.fill_password)
      isValid = false
    }
    return isValid
  }


}

abstract class FormTextWatcher : TextWatcher {
  override fun afterTextChanged(s: Editable?) {
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  abstract override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
}
