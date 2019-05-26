package com.barmej.weatherforecasts.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.weatherforecasts.R;
import com.barmej.weatherforecasts.data.entity.Forecast;
import com.barmej.weatherforecasts.databinding.ItemDayForecastBinding;

import java.util.List;

/**
 * {@link DaysForecastAdapter} exposes a list contain the next 4 days weather forecasts
 * from a List of {@link List<Forecast>} to a {@link RecyclerView}.
 */
public class DaysForecastAdapter extends RecyclerView.Adapter<DaysForecastAdapter.ForecastAdapterViewHolder> {

    /**
     * The context to access app resources and inflate layouts
     */
    private final Context mContext;

    private List<List<Forecast>> mForecasts;

    /**
     * DaysForecastAdapter constructor
     *
     * @param context Used to access the the UI and app resources
     */
    public DaysForecastAdapter(@NonNull Context context) {
        mContext = context;
    }

    /**
     * This method called when the RecyclerView is presented.
     * Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that will contain the inflated View
     * @param viewType  If your RecyclerView has more than one type of item you
     *                  can use this viewType integer to provide a different layout.
     *                  Check {@link RecyclerView.Adapter#getItemViewType(int)} for more details.
     * @return A new ForecastAdapterViewHolder that holds the list item view
     */
    @Override
    public @NonNull
    ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemDayForecastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_day_forecast, viewGroup, false);
        return new ForecastAdapterViewHolder(binding);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        Forecast forecast = mForecasts.get(position).get(0);
        forecastAdapterViewHolder.bind(forecast);
    }

    /**
     * This method simply returns the number of items to display.
     *
     * @return The number of items available to display
     */
    @Override
    public int getItemCount() {
        if (mForecasts == null) {
            return 0;
        } else {
            return mForecasts.size();
        }
    }

    /**
     * Update the current forecasts data with new list
     *
     * @param forecasts a list of {@link List<Forecast>}
     */
    public void updateData(List<List<Forecast>> forecasts) {
        this.mForecasts = forecasts;
        notifyDataSetChanged();
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a list item.
     */
    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

        final ItemDayForecastBinding binding;

        ForecastAdapterViewHolder(ItemDayForecastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Forecast forecast) {
            binding.setForecast(forecast);
        }

    }

}