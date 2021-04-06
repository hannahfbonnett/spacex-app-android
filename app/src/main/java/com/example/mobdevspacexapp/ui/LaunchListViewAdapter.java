package com.example.mobdevspacexapp.ui;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LaunchListViewAdapter extends RecyclerView.Adapter<LaunchListViewAdapter.LaunchListViewHolder> {

    private Context context;
    private ArrayList<Launch> launches;

    public LaunchListViewAdapter(Context context, ArrayList<Launch> launches) {
        this.context = context;
        this.launches = launches;
    }

    @NonNull
    @Override
    public LaunchListViewAdapter.LaunchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.launch_list_item, parent, false);
        return new LaunchListViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchListViewAdapter.LaunchListViewHolder holder, int position) {
        Launch launch = launches.get(position);
        holder.bind(launch);
//        if (this.launchManager != null) {
//            LaunchListItem launchListItem = this.launchManager.getLaunchByIndex(position);
//            holder.launchNameText.setText(launchListItem.getFlightName());
//            holder.launchDatetimeText.setText(launchListItem.getDateUtc());
//            //holder.launchIcon.setImageResource(launch.getPatchLinkSmall());
//            Picasso.get()
//                    .load(launchListItem.getPatchLinkSmall())
//                    .centerInside()
//                    .fit()
//                    .into(holder.launchIcon);
//        }
    }

    @Override
    public int getItemCount() {
        return (launches == null ? 0 : launches.size());
    }


    protected class LaunchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private AppCompatTextView launchNameText;
        private AppCompatTextView launchFlightNumberText;
        private AppCompatTextView launchDatetimeText;
        private AppCompatImageView launchIcon;

        LaunchListViewHolder(View itemView) {
            super(itemView);
            this.launchNameText = (AppCompatTextView) itemView.findViewById(R.id.launch_list_item_name);
            this.launchFlightNumberText = (AppCompatTextView) itemView.findViewById(R.id.launch_list_item_flight_number);
            this.launchDatetimeText = (AppCompatTextView) itemView.findViewById(R.id.launch_list_item_datetime);
            this.launchIcon = itemView.findViewById(R.id.launch_list_item_icon);

            itemView.setOnClickListener(this);
        }

        public void bind(Launch launch) {
            launchNameText.setText(launch.getName());
            launchFlightNumberText.setText("Flight no: " + launch.getFlightNumber());
            launchDatetimeText.setText(launch.getDateUtc());
            Picasso.get()
                    .load(launch.getPatchLinkSmall())
                    .into(launchIcon);
        }

        @Override
        public void onClick(View view) {
            int i = this.getAdapterPosition();
            Launch selectedLaunch = launches.get(i);
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            LaunchDetailFragment launchDetailFragment = new LaunchDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("Launch", selectedLaunch);
            launchDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.launchesFragmentContainer, launchDetailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
