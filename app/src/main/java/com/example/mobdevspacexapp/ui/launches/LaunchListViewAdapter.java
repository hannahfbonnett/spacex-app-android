package com.example.mobdevspacexapp.ui.launches;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.model.Launch;
import com.example.mobdevspacexapp.util.DateTimeConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LaunchListViewAdapter extends RecyclerView.Adapter<LaunchListViewAdapter.LaunchListViewHolder> {

    private Context context;
    private List<Launch> launches;

    public LaunchListViewAdapter(Context context, List<Launch> launches) {
        this.context = context;
        this.launches = launches;
    }

    /*
        Inflate the launch list item layout.
        Return the view holder.
     */
    @NonNull
    @Override
    public LaunchListViewAdapter.LaunchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.launch_list_item, parent, false);
        return new LaunchListViewHolder(listItemView);
    }

    /*
        Populate a particular row with particular data using
        the row (holder) and the position of the row.
     */
    @Override
    public void onBindViewHolder(@NonNull LaunchListViewAdapter.LaunchListViewHolder holder, int position) {
        Launch launch = launches.get(position);
        holder.bind(launch);
    }

    /*
        Get the number of items (rows) that will be displayed using the list of
        launches that was provided in the constructor.
     */
    @Override
    public int getItemCount() {
        return (launches == null ? 0 : launches.size());
    }


    protected class LaunchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        private AppCompatTextView launchNameText;
        private AppCompatTextView launchFlightNumberText;
        private AppCompatTextView launchDatetimeText;
        private AppCompatImageView launchIcon;

        LaunchListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.launchNameText = itemView.findViewById(R.id.launch_list_item_name);
            this.launchFlightNumberText = itemView.findViewById(R.id.launch_list_item_flight_number);
            this.launchDatetimeText = itemView.findViewById(R.id.launch_list_item_datetime);
            this.launchIcon = itemView.findViewById(R.id.launch_list_item_icon);

            itemView.setOnClickListener(this);
        }

        /*
            Set the text and image using the launch attributes.
         */
        public void bind(Launch launch) {
            launchNameText.setText(launch.getName());
            launchFlightNumberText.setText(context.getString(R.string.launch_item_flight_number, launch.getFlightNumber()));
            launchDatetimeText.setText(DateTimeConverter.getFormattedUnixDateTime(launch.getDateTimeUnix()));
            if(!launch.getPatchLinkSmall().equals("null")) {
                Picasso.get()
                        .load(launch.getPatchLinkSmall())
                        .into(launchIcon);
            }
        }

        /*
            View launch details when a launch item is clicked.
         */
        @Override
        public void onClick(View view) {
            int i = this.getBindingAdapterPosition();
            Launch selectedLaunch = launches.get(i);
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            LaunchDetailFragment launchDetailFragment = new LaunchDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("Launch", selectedLaunch);
            launchDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, launchDetailFragment)
                    .addToBackStack(null)
                    .commit();

        }
    }

}
