package com.example.justtryingout

import android.graphics.Color
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
const val LOG_TAG="Just Fucking Logging"

class MyViewModel:ViewModel() {

    private val prefs = MyPrefRepo.get()
    private val BoxColors = IntArray(3)
    private val States = IntArray(3)

    private val _bcolor = MutableSharedFlow<Int>()
    val bcolor = _bcolor.asSharedFlow()

    fun saveColor(i: Int, clr: String) {
        viewModelScope.launch {
            prefs.saveColor(i, clr)
        }
    }

    fun saveSwitch(state: Boolean, clr: String) {
        viewModelScope.launch {
            prefs.saveSwitchState(state, clr)
        }
    }


    fun Reset() {
        viewModelScope.launch {
            prefs.saveColor(0, "red")
            prefs.saveColor(0, "green")
            prefs.saveColor(0, "blue")
            prefs.saveSwitchState(false, "red")
            prefs.saveSwitchState(false, "green")
            prefs.saveSwitchState(false, "blue")
        }
    }

    fun loadUIValues(act: MainActivity) {
        viewModelScope.launch {
            prefs.rswitch.collectLatest {
                Log.d(LOG_TAG, "loadUIValues:$it ")
                if (!it) {
                    act.rseekbar.isEnabled = false
                    act.reditview.isEnabled = false
                } else {
                    act.rseekbar.isEnabled = true
                    act.reditview.isEnabled = true
                }
            }
        }

        viewModelScope.launch {
            prefs.gswitch.collectLatest {
                Log.d(LOG_TAG, "loadUIValues:$it ")
                if (!it) {
                    act.gseekbar.isEnabled = false
                    act.geditview.isEnabled = false
                } else {
                    act.gseekbar.isEnabled = true
                    act.geditview.isEnabled = true
                }
            }
        }

        viewModelScope.launch {
            prefs.bswitch.collectLatest {
                Log.d(LOG_TAG, "loadUIValues:$it ")
                if (!it) {
                    act.bseekbar.isEnabled = false
                    act.beditview.isEnabled = false
                } else {
                    act.bseekbar.isEnabled = true
                    act.beditview.isEnabled = true
                }
            }
        }
        viewModelScope.launch {
            prefs.bcolor.collectLatest {
                act.bseekbar.setProgress(it)
                act.beditview.setText(String.format("%.2f", (it.toDouble() / 255.toDouble())))
            }
        }
        viewModelScope.launch {
            prefs.rcolor.collectLatest {
                act.rseekbar.setProgress(it)
                act.reditview.setText(String.format("%.2f", (it.toDouble() / 255.toDouble())))
            }
        }
        viewModelScope.launch {
            prefs.gcolor.collectLatest {
                _bcolor.emit(it)
//                act.geditview.setText(String.format("%.2f", (it.toDouble() / 255.toDouble())))
            }
        }


    }
}






