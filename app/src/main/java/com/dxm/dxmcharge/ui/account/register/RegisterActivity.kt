package com.dxm.dxmcharge.ui.account.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.charge.lib.util.*
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityRegisterBinding
import com.dxm.dxmcharge.ui.account.login.LoginModel
import com.dxm.dxmcharge.ui.device.ChargeListActivity
import com.dxm.dxmcharge.widget.TitleEditext
import com.dxm.dxmcharge.widget.popuwindow.ListPopModel
import com.dxm.dxmcharge.widget.popuwindow.ListPopuwindow
import com.shuoxd.lib.service.account.User

class RegisterActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }


    private lateinit var binding: ActivityRegisterBinding

    val viewModel by lazy { ViewModelProvider(this)[RegisterModel::class.java] }
    val logViewModel by lazy { ViewModelProvider(this)[LoginModel::class.java] }


/*    private var viewModel: RegisterModel by lazy {
        ViewModelProvider(this).get(RegisterModel::class.java)
    }*/
//    private lateinit var logViewModel: LoginModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initData()
        initliseners()
    }


    private fun initViews() {
        binding.etStateArea.setContent("US")
    }


    private fun initliseners() {
        binding.btLogin.setOnClickListener(this)
        binding.etStateArea.setOnRightClick(object : TitleEditext.OnRightClickListeners {
            override fun onRightIconclick() {
                KeyBoardUtil.hideInput(this@RegisterActivity)
                selectCountry()
            }

        })

    }


    private fun initData() {
        viewModel.newLiveData.observe(this) {
            val orNull = it.getOrNull()
            if (orNull != null) {
                //请求登录
                login()
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }


        }


        viewModel.newCountryLiveData.observe(this) {
            dismissDialog()
            selectCountry()
        }



        logViewModel.newLoginLiveData.observe(this) {
            dismissDialog()
            val orNull = it.getOrNull()
            if (orNull != null) {
                loginSuccess(orNull)
            } else {
                val message = it.exceptionOrNull()?.message
                showResultDialog(message, null, null)


            }
        }

    }



    private fun loginSuccess(user: User?) {
        user?.password = binding.etPassword.getText()
        accountService().saveUserInfo(user)
        ChargeListActivity.start(this)
        finish()

    }



    private fun login() {

        val password = binding.etPassword.getText()
        val userName = binding.etUserName.getText()


        if (!TextUtils.isEmpty(password)) {
            showDialog()
//            val pwdMd5 = MD5Util.md5(password)
            val version = Util.getVersion(this)
            val deviceId = deviceService().getDeviceId()
            val language = LanUtils.getLanguage(this)

            logViewModel.login(
                "login", userName, deviceId, password, version,
                language.toString()
            )
 /*           if (pwdMd5 != null) {
                logViewModel.login(
                    "login", userName, deviceId, password, version,
                    language.toString()
                )
            }*/
        }

    }


    private fun selectCountry() {
        val provinceList = viewModel.newCountryLiveData.value?.getOrNull()
        if (provinceList.isNullOrEmpty()) {
            showDialog()
            viewModel.getCountry("countryList")
        } else {

            val options = mutableListOf<ListPopModel>()

            //显示下拉框
            for (plant in provinceList) {
                options.add(ListPopModel(plant.country, false))
            }

            val curItem = viewModel.curCountry
            ListPopuwindow.showAsDropDownPop(
                this,
                options,
                binding.etStateArea,
                curItem
            ) {
                viewModel.curCountry = options[it].title
                binding.etStateArea.setContent(viewModel.curCountry)

            }


        }
    }


    override fun onClick(v: View?) {
        when {
            v === binding.btLogin -> checkInputInfo()
        }
    }


    /**
     * 检查输入信息是否合规
     */
    private fun checkInputInfo() {

        val username = binding.etUserName.getText()
        val password = binding.etPassword.getText()
        val password2 = binding.etPassword2.getText()
        val phone = binding.etStateArea.getText()
        val email = binding.etEmail.getText()
        val country = binding.etPhone.getText()

        val checked = binding.ivSelect.isChecked

        if (!checked) {
            ToastUtil.show(getString(R.string.m81_please_check_agree_agreement))
        } else if (TextUtils.isEmpty(username)) {
            ToastUtil.show(getString(R.string.m74_please_input_username))
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            ToastUtil.show(getString(R.string.m82_password_cant_empty))
        } else if (password.length < 6 || password2.length < 6) {
            ToastUtil.show(getString(R.string.m84_password_cannot_be_less_than_6_digits))
        } else if (password != password2) {
            ToastUtil.show(getString(R.string.m83_passwords_are_inconsistent))
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtil.show(getString(R.string.m85_no_phone_number))
        } else if (TextUtils.isEmpty(email)) {
            ToastUtil.show(getString(R.string.m89_no_email))
        } else if (TextUtils.isEmpty(country)) {
            ToastUtil.show(getString(R.string.m87_no_country))
        } else {
            viewModel.toRegister(
                "register",
                username,
                country,//
                password,
                email,
                "1",
                "1",
                phone,
                "",
                "1",
                "1",
                "1",
                "0"
            )
        }


    }

}