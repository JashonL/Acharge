package com.dxm.dxmcharge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityMainBinding
import com.dxm.dxmcharge.ui.gun.GunFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChargeActivity : BaseActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, ChargeActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, ChargeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    private lateinit var bind: ActivityMainBinding
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var fragments: MutableList<GunFragment> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initViews()
    }

    private fun initViews() {
        val chargeId = getCurrentChargeModel()?.chargeId
        bind.titleBar.setTitleText(chargeId)

        //枪数
        val connectors = getCurrentChargeModel()?.connectors ?: 0
        val nams = arrayListOf("A", "B", "C", "D")
        val tabtitles = mutableListOf<String>()
        for (i in 0 until connectors) {
            tabtitles.add(nams[i % nams.size] + " " + getString(R.string.gun))
            fragments.add(GunFragment.newInstance((i+1).toString()))
        }


        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle,fragments)
        bind.vp2.adapter = viewPagerAdapter
        TabLayoutMediator(bind.tlTab, bind.vp2, true, true) { tab: TabLayout.Tab, position: Int ->
            val tabTitle: String = tabtitles.get(position)
            tab.text = tabTitle
        }.attach()


    }


    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragments: MutableList<GunFragment>) :
        FragmentStateAdapter(fragmentManager, lifecycle) {


        override fun createFragment(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getItemCount(): Int {
            return fragments.size
        }
    }


}
