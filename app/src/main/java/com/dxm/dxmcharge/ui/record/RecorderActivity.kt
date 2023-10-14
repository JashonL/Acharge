package com.dxm.dxmcharge.ui.record

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.*
import com.dxm.dxmcharge.base.viewholder.EmptyViewHolder
import com.dxm.dxmcharge.databinding.ActivityRecorderBinding
import com.dxm.dxmcharge.databinding.ItemRecorderBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.visible
import com.dxm.dxmcharge.logic.model.Charge
import com.dxm.dxmcharge.logic.model.Recorder
import com.dxm.dxmcharge.ui.charge.ChargePresetViewModel
import com.dxm.dxmcharge.ui.device.ChargeListActivity
import com.dxm.dxmcharge.ui.gun.GunFragment
import com.dxm.dxmcharge.widget.itemdecoration.DividerItemDecoration

class RecorderActivity : BaseActivity() {


    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, RecorderActivity::class.java))
        }
    }


    private lateinit var binding: ActivityRecorderBinding

    private val recordViewModel by lazy { ViewModelProvider(this)[RecoderViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecorderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
//        getRecordlist()
        initData()

        requestData()
    }


    private fun requestData() {
        getRecordlist(0)
    }

    private fun initViews() {

        binding.srlPull.setOnRefreshListener {
            getRecordlist(0)
        }


        binding.rlvRecord.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL,
                resources.getColor(android.R.color.transparent),
                10f
            )
        )
        binding.rlvRecord.adapter = Adapter(this)


    }


    private fun getRecordlist(curentPage: Int) {
        val currentChargeModel = getCurrentChargeModel()
        val chargeId = getCurrentChargeModel()?.chargeId
        val language = LanUtils.getLanguage(this)


        recordViewModel.chargeRecorder(
            "chargeRecord",
            chargeId,
            curentPage.toString(),
            30.toString(),
            language.toString(),
            accountService().user()?.userId,
        )

    }

    private fun initData() {

        recordViewModel.newLiveData.observe(this) {
            dismissDialog()
            val orNull = it.getOrNull()
            if (orNull != null) {
                getAdapter().refresh(orNull)
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }


        }

    }


    private fun getAdapter(): Adapter {
        return binding.rlvRecord.adapter as Adapter
    }

    inner class Adapter(val context: Context?, var recorderList: MutableList<Recorder> = mutableListOf()) : RecyclerView.Adapter<BaseViewHolder>(), OnItemClickListener {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return if (recorderList.isEmpty()) {
                EmptyViewHolder.create(parent)
            } else {
                RecordViewHolder.create(parent, this)
            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            if (holder is RecordViewHolder) {
                holder.bindData(recorderList[position])
            }
        }

        override fun getItemCount(): Int {
            return recorderList.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun refresh(serviceList: List<Recorder>) {
            this.recorderList.clear()
            this.recorderList.addAll(serviceList)
            notifyDataSetChanged()
        }

    }


    class RecordViewHolder(
        itemView: View,
    ) : BaseViewHolder(itemView) {

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener?
            ): RecordViewHolder {
                val binding = ItemRecorderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val holder = RecordViewHolder(binding.root)
                holder.binding = binding
                holder.binding.root.setOnClickListener(holder)
                return holder
            }
        }

        private lateinit var binding: ItemRecorderBinding

        fun bindData(chargeModel: Recorder) {
            //枪数
            val nams = arrayListOf("A", "B", "C", "D")
            val connectorId = chargeModel.connectorId
            if (connectorId!! < nams.size) {
                binding.tvGun.text = nams[connectorId!!] + " " + getString(R.string.gun)
            }
            binding.tvId.text = chargeModel.chargeId
            binding.tvStartTime.text = chargeModel.starttime
            binding.tvEndTime.text = chargeModel.endtime


            binding.tvCtime.text = chargeModel.ctime.toString() + "min"
            binding.tvEle.text = chargeModel.energy.toString() + "kWh"
            binding.tvCost.text = chargeModel.cost.toString()


        }
    }


}