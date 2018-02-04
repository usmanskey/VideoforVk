package akhmedoff.usman.videoforvk.main

import akhmedoff.usman.videoforvk.base.BasePresenter
import akhmedoff.usman.videoforvk.data.repository.VideoRepository
import akhmedoff.usman.videoforvk.model.Catalog
import akhmedoff.usman.videoforvk.model.VideoCatalog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.OnLifecycleEvent

class MainPresenter(private val videoRepository: VideoRepository) :
    BasePresenter<MainContract.View>(), MainContract.Presenter {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = refresh()

    override fun refresh() {
        loadCatalogs()
    }

    override fun pagination() {
        loadCatalogs()
    }

    override fun loadCatalogs() {
        view?.showLoading()
        view?.let {
            videoRepository.getCatalog().observe(it, Observer { pagedList ->
                pagedList?.let { catalogs ->
                    view?.hideLoading()
                    view?.showList(catalogs)
                }
            })
        }
    }

    override fun clickCatalog(catalog: Catalog) {
        view?.showCatalog(catalog)
    }

    override fun clickVideo(video: VideoCatalog) {
        view?.showVideo(video)
    }
}