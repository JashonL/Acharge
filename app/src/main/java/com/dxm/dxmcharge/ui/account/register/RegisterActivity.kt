package com.dxm.dxmcharge.ui.account.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }


    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegisterModel::class.java)
        initData()
        initliseners()
    }

    private fun initliseners() {
        binding.btLogin.setOnClickListener(this)
    }


    private fun initData() {
        viewModel.newLiveData.observe(this) {

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


        /*    if (!viewModel.isAgree) {
                ToastUtil.show(getString(R.string.m81_please_check_agree_agreement))
            } else if (TextUtils.isEmpty(username)) {
                ToastUtil.show(getString(R.string.m74_please_input_username))
            } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                ToastUtil.show(getString(R.string.m82_password_cant_empty))
            } else if (password.length < 6 || confirmPassword.length < 6) {
                ToastUtil.show(getString(R.string.m84_password_cannot_be_less_than_6_digits))
            } else if (password != confirmPassword) {
                ToastUtil.show(getString(R.string.m83_passwords_are_inconsistent))
            } else if (TextUtils.isEmpty(phone)) {
                ToastUtil.show(getString(R.string.m85_no_phone_number))
            } else if (TextUtils.isEmpty(city)) {
                ToastUtil.show(getString(R.string.m89_no_email))
            } else if (TextUtils.isEmpty(country)) {
                ToastUtil.show(getString(R.string.m87_no_country))
            } else if (TextUtils.isEmpty(timeZone)) {
                ToastUtil.show(getString(R.string.m86_no_timezone))
            } else {
                viewModel.register(username, password, country, city, phone, timeZone)
            }*/


        viewModel.toRegister(
            "register",
            username,
            phone,
            password,
            email,
            "1",
            "1",
            country,
            "",
            "1",
            "1",
            "1",
            "0"
        )

    }

}