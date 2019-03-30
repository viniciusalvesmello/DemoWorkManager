package io.github.viniciusalvesmello.demoworkmanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _listUrlsDownload = MutableLiveData<List<String>>()
    val listUrlsDownload : LiveData<List<String>>
            get() = _listUrlsDownload
    private val listUrls : MutableList<String> = mutableListOf()

    init {
        for (line in 1..100) {
            listUrls.add("https://picsum.photos/300/300?image=$line")
        }
        _listUrlsDownload.value = listUrls
    }
}
