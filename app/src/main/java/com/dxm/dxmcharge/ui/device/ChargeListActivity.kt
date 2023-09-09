package com.dxm.dxmcharge.ui.device

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.ChargeActivity
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.databinding.ActivityChargeListBinding
import com.dxm.dxmcharge.databinding.ItemCharge2Binding
import com.dxm.dxmcharge.databinding.ItemChargeBinding
import com.dxm.dxmcharge.logic.model.Charge
import com.dxm.dxmcharge.widget.itemdecoration.DividerItemDecoration
import com.dxm.dxmcharge.base.BaseViewHolder
import com.dxm.dxmcharge.base.OnItemClickListener
import com.dxm.dxmcharge.ui.device.monitor.ChargeAactivityMonitor

class ChargeListActivity : BaseActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, ChargeListActivity::class.java))
        }

        fun startClearTask(context: Context?) {
            val intent = Intent(context, ChargeListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
        }
    }


    val ChargeListModel by lazy { ViewModelProvider(this)[ChargeListModel::class.java] }

    private lateinit var bind: ActivityChargeListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityChargeListBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initViews()
        initData()
        setOnResume()
    }


    /**
     * 监听从我的点击返回事件
     */
    var listener1 = fun() {


    }

    /**
     * 监听添加事件
     */
    var lisener2 = fun(_: ChargeAactivityMonitor) {
        freshChage()
    }


    private fun setOnResume() {
        ChargeAactivityMonitor.watch(lifecycle, listener1, lisener2)
    }


    private fun initViews() {
        bind.title.setOnLeftIvClickListener {
            //添加充电桩
            AddYourChargeActivity.start(this)
        }



        bind.rlvCharge.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL,
                resources.getColor(R.color.nocolor),
                10f
            )
        )
        bind.rlvCharge.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bind.rlvCharge.adapter = Adapter(this)



        bind.search.etSearch.addTextChangedListener {
            freshChage(it.toString())
        }

        bind.search.ivSearch.setOnClickListener {
            freshChage()
        }

        freshChage()


    }


    private fun freshChage(chargeId:String?="") {
        val language = LanUtils.getLanguage(this)
        showDialog()
        ChargeListModel.getChargelist(
            "list", accountService().user()?.userId, chargeId,
            language.toString()
        )
    }


    private fun initData() {
        //充电桩列表
        ChargeListModel.newLiveData.observe(this) {
            dismissDialog()
            val provinceList = it.getOrNull()
            if (!provinceList.isNullOrEmpty()) {
                (bind.rlvCharge.adapter as Adapter).refresh(provinceList)
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)

            }
        }


    }

    class Adapter(val context: Context?, var chargeList: MutableList<Charge> = mutableListOf()) :
        RecyclerView.Adapter<BaseViewHolder>(), OnItemClickListener {

        companion object {
            const val ITEM_LEFT = 0
            const val ITEM_RIGHT = 1

        }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder {
            return if (viewType == ITEM_LEFT) {
                ChargeLeftViewHolder.create(parent, this)
            } else {
                ChargeRightViewHolder.create(parent, this)
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//            holder.bindData(chargeList[position], position)
        }

        override fun getItemCount(): Int {
            return chargeList.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun refresh(serviceList: List<Charge>) {
            this.chargeList.clear()
            this.chargeList.addAll(serviceList)
            notifyDataSetChanged()
        }

        override fun onItemClick(v: View?, position: Int) {
            openWebPage(position)
        }


        override fun onItemLongClick(v: View?, position: Int) {
            val tag = v?.tag
            if (tag is Charge) {
                deleteCharge(tag)
            }

        }


        override fun getItemViewType(position: Int): Int {
            return position % 2
        }


        private fun deleteCharge(charge: Charge) {
            /*        AlertDialog.showDialog(
                        supportFragmentManager,
                        getString(R.string.m155_delete_tips),
                        getString(R.string.m16_cancel),
                        getString(R.string.m18_confirm),
                        getString(R.string.m154_delete_charge)
                    ) {
                    }*/
        }


        private fun openWebPage(position: Int) {
            context as ChargeListActivity
            context.setCurrenChargeModel(chargeList[position])
            ChargeActivity.start(context)
        }


        @SuppressLint("NotifyDataSetChanged")
        fun deleteItem() {
        }


    }


    class ChargeRightViewHolder(itemView: View, onItemClickListener: OnItemClickListener? = null) :
        BaseViewHolder(itemView, onItemClickListener) {

        private lateinit var binding: ItemChargeBinding

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener?
            ): ChargeRightViewHolder {
                val binding = ItemChargeBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
                val holder = ChargeRightViewHolder(binding.root, onItemClickListener)
                holder.binding = binding
                holder.binding.root.setOnClickListener(holder)
                holder.binding.root.setOnLongClickListener(holder)
                return holder
            }
        }


        fun bindData(chargeModel: Charge, position: Int) {


        }


    }


    class ChargeLeftViewHolder(itemView: View, onItemClickListener: OnItemClickListener? = null) :
        BaseViewHolder(itemView, onItemClickListener) {

        private lateinit var binding: ItemCharge2Binding

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener?
            ): ChargeLeftViewHolder {
                val binding = ItemCharge2Binding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
                val holder = ChargeLeftViewHolder(binding.root, onItemClickListener)
                holder.binding = binding
                holder.binding.root.setOnClickListener(holder)
                holder.binding.root.setOnLongClickListener(holder)
                return holder
            }
        }


        fun bindData(chargeModel: Charge, position: Int) {
        }


    }


}