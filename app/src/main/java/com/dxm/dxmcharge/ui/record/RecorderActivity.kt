package com.dxm.dxmcharge.ui.record

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.charge.lib.util.LanUtils
import com.charge.lib.util.ToastUtil
import com.dxm.dxmcharge.R
import com.dxm.dxmcharge.base.BaseActivity
import com.dxm.dxmcharge.base.BasePageListAdapter
import com.dxm.dxmcharge.base.BaseViewHolder
import com.dxm.dxmcharge.base.OnItemClickListener
import com.dxm.dxmcharge.databinding.ActivityRecorderBinding
import com.dxm.dxmcharge.databinding.ItemRecorderBinding
import com.dxm.dxmcharge.extend.gone
import com.dxm.dxmcharge.extend.visible
import com.dxm.dxmcharge.logic.model.Recorder
import com.dxm.dxmcharge.ui.charge.ChargePresetViewModel
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
        (binding.rlvRecord.adapter as Adapter).refresh()
    }

    private fun initViews() {

        binding.srlPull.setOnRefreshListener {
            (binding.rlvRecord.adapter as Adapter).refresh()
        }


        binding.rlvRecord.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL,
                resources.getColor(android.R.color.transparent),
                10f
            )
        )
        binding.rlvRecord.adapter = Adapter()


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
                getAdapter().setResultSuccess(orNull)
            } else {
                val message = it.exceptionOrNull()?.message
                ToastUtil.show(message)
            }


        }

    }


    private fun getAdapter(): Adapter {
        return binding.rlvRecord.adapter as Adapter
    }

    inner class Adapter : BasePageListAdapter<Recorder>(), OnItemClickListener {

        override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            /*    return RecordViewHolder.create(parent) { view, position ->
                    AlertDialog.showDialog(
                        supportFragmentManager,
                        getString(R.string.delete_message_or_not)
                    ) {
                        showDialog()

                    }
                }*/

            return RecordViewHolder.create(parent, this)
        }

        override fun onBindItemViewHolder(holder: BaseViewHolder, position: Int) {
            if (holder is RecordViewHolder) {
                holder.bindData(dataList[position])
            }
        }

        override fun onLoadNext() {
            getRecordlist(++currentPage)
        }

        override fun onRefresh() {
            getRecordlist(currentPage)
        }


        override fun showEmptyView() {
            binding.tvEmpty.visible()
        }

        override fun hideEmptyView() {
            binding.tvEmpty.gone()
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
            if (connectorId < nams.size) {
                binding.tvGun.text = nams[connectorId] + " " + getString(R.string.gun)
            }
            binding.tvId.text = chargeModel.cid.toString()
            binding.tvStartTime.text = chargeModel.starttime
            binding.tvEndTime.text = chargeModel.endtime


            binding.tvCtime.text = chargeModel.ctime.toString() + "min"
            binding.tvEle.text = chargeModel.energy.toString() + "kWh"
            binding.tvCost.text = chargeModel.cost.toString()

        }
    }


}