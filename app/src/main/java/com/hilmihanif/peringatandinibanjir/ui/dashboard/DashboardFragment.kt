package com.hilmihanif.peringatandinibanjir.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.hilmihanif.peringatandinibanjir.SplashActivity
import com.hilmihanif.peringatandinibanjir.data.ThisAppConst
import com.hilmihanif.peringatandinibanjir.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val button:Button = binding.loginButton
        val notifBanjirSwitch:Switch = binding.banjirSwitch
        val notifDBDSwitch:Switch = binding.dbdSwitch


        val pref = context?.getSharedPreferences(ThisAppConst.PREF, Context.MODE_PRIVATE)
        notifBanjirSwitch.isChecked = pref!!.getBoolean(ThisAppConst.NOTIF_BANJIR,true)
        notifDBDSwitch.isChecked = pref!!.getBoolean(ThisAppConst.NOTIF_DBD,true)

        notifBanjirSwitch.setOnCheckedChangeListener { p0, b ->
            //Toast.makeText(context, "notif banjir $b", Toast.LENGTH_SHORT).show()
            with(pref.edit()) {
                this?.putBoolean(ThisAppConst.NOTIF_BANJIR, b)
                this?.apply()
            }
            ThisAppConst.setNotifBanjir(b)

        }

        notifDBDSwitch.setOnCheckedChangeListener { compoundButton, b ->
            //Toast.makeText(context, "notif banjir $b", Toast.LENGTH_SHORT).show()
            with(pref.edit()){
                this?.putBoolean(ThisAppConst.NOTIF_DBD,b)
                this?.apply()
            }
            ThisAppConst.setNotifDBD(b)
        }






        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


