package com.dxm.dxmcharge.ui.account.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityRegisterBinding
import com.dxm.dxmcharge.widget.TitleEditext
import com.dxm.dxmcharge.widget.popuwindow.ListPopModel
import com.dxm.dxmcharge.widget.popuwindow.ListPopuwindow

class RegisterActivity : BaseActivity(), View.OnClickListener {

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
        binding.etStateArea.setOnRightClick(object: TitleEditext.OnRightClickListeners{
            override fun onRightIconclick() {
                selectCountry()
            }

        })

    }


    private fun initData() {


        viewModel.newLiveData.observe(this) {

        }


        viewModel.newCountryLiveData.observe(this) {
            dismissDialog()
            selectCountry()
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

/*            val curItem: String? = if (viewModel.currentStation != null) {
                viewModel.currentStation!!.id
            } else {
                ""
            }*/
            ListPopuwindow.showAsDropDownPop(
                this,
                options,
                 binding.etStateArea,
              ""
            ) {

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