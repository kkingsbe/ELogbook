package com.example.kkingsbe.elogbook;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ExampleViewHolder> {
    private ArrayList<ExampleFlight> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mDate;
        public TextView mFlightPlan;
        public TextView mFlightTime;
        public TextView mAirFrame;
        public TextView mTailNumber;
        public ImageView mEditButton;
        public ImageView mDeleteButton;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mDate = itemView.findViewById(R.id.Date);
            mFlightPlan = itemView.findViewById(R.id.FlightPlan);
            mFlightTime = itemView.findViewById(R.id.FlightTime);
            mAirFrame = itemView.findViewById(R.id.AirFrame);
            mTailNumber = itemView.findViewById(R.id.TailNumber);
            mEditButton = itemView.findViewById(R.id.FlightEditButton);
            mDeleteButton = itemView.findViewById(R.id.FlightDeleteButton);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mEditButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });

        }
    }

    public FlightAdapter(ArrayList<ExampleFlight> exampleFlights) {
        mExampleList = exampleFlights;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_flight, parent, false);
        return new ExampleViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int position) {
        ExampleFlight flight = mExampleList.get(position);

        exampleViewHolder.mTailNumber.setText(flight.getTailNumber());
        exampleViewHolder.mFlightTime.setText(flight.getFlightTime());
        exampleViewHolder.mFlightPlan.setText(flight.getFlightPlan());
        exampleViewHolder.mAirFrame.setText(flight.getAirframe());
        exampleViewHolder.mDate.setText(flight.getDate());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
