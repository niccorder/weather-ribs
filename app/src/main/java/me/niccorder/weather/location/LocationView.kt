package me.niccorder.weather.location

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerViewAdapter
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapter
import com.jakewharton.rxbinding2.widget.RxAdapterView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject
import kotterknife.bindView
import me.niccorder.weather.R
import me.niccorder.weather.data.remote.model.Location
import timber.log.Timber

class LocationView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), LocationInteractor.Presenter {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val headerContainer: FrameLayout by bindView(R.id.header_container)
    private val currentIcon: ImageView by bindView(R.id.current_weather_icon)
    private val currentCity: TextView by bindView(R.id.current_city)
    private val locationRecycler: RecyclerView by bindView(R.id.locations)
    private val locationAdapter: LocationAdapter = LocationAdapter()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        disposable.add(
                RxRecyclerView.scrollEvents(locationRecycler)
                        .doOnNext {
                            val visibleHeaderHeight = headerContainer.top - headerContainer.bottom
                            Timber.i("visible header height: $visibleHeaderHeight")

                            if (visibleHeaderHeight > 0) {
                                val scrollTranslation = headerContainer.translationY - it.dy()

                                if (scrollTranslation < 0) {
                                    headerContainer.translationY = 0f
                                } else {
                                    headerContainer.translationY = scrollTranslation
                                }
                            }
                        }.subscribe()
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposable.clear()
    }

    override fun setCurrentLocation(location: Location) {
        currentCity.text = location.city
    }

    override fun setLocations(locations: List<Location>): Observable<Int> {
        locationAdapter.locations = locations
        return Observable.just(locations.count())
    }

    override fun locationClicks(): Observable<Location> = locationAdapter.clickSubject.map {
        Timber.i("Location @ position ${it.adapterPosition} was clicked")
        it.location
    }
}

data class LocationPlace(val location: Location, val adapterPosition: Int)

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val temperature: TextView by bindView(R.id.temperature)
    internal val name: TextView by bindView(R.id.location_name)
}

class LocationAdapter : RecyclerView.Adapter<LocationViewHolder>() {
    internal val clickSubject = PublishSubject.create<LocationPlace>()
    internal var locations: List<Location> = emptyList()

    override fun onBindViewHolder(holder: LocationViewHolder?, position: Int) {
        val location = locations[position]
        holder?.temperature?.text = "-1"
        holder?.name?.text = location.city
    }

    override fun getItemCount(): Int = locations.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LocationViewHolder {
        val view = View.inflate(parent!!.context, R.layout.item_location, parent)
        val holder = LocationViewHolder(view)

        RxView.clicks(view).map {
            val adapterPosition = holder.adapterPosition
            LocationPlace(locations[adapterPosition], adapterPosition)
        }.subscribe(clickSubject)

        return LocationViewHolder(view)
    }

}