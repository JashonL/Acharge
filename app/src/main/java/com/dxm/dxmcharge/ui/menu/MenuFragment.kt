package com.dxm.dxmcharge.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dxm.dxmcharge.base.BaseFragment
import com.dxm.dxmcharge.databinding.UserMenuBinding
import com.dxm.dxmcharge.ui.account.login.LoginActivity

class MenuFragment:BaseFragment() {


    private lateinit var bind: UserMenuBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = UserMenuBinding.inflate(inflater)
        initViews()
        return bind.root
    }

    private fun initViews() {
        bind.llExit.setOnClickListener {
            accountService().logout()
            LoginActivity.startClearTask(requireContext())
        }
    }


}