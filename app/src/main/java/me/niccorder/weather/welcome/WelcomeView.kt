package me.niccorder.weather.welcome

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.RelativeLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotterknife.bindView
import me.niccorder.weather.R

open class WelcomeView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), WelcomeInteractor.WelcomePresenter {

    private val beginBtn: Button by bindView(R.id.begin_btn)

    override fun beginClicks(): Observable<Unit> = RxView.clicks(beginBtn).map { Unit }
}