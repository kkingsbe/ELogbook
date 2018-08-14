package com.example.kkingsbe.elogbook;

public class ExampleFlight {
    private String mFlightTime;
    private String mAirframe;
    private String mTailNumber;
    private String mDate;
    private String mFlightPlan;

    public ExampleFlight(String FlightTime, String Airframe, String TailNumber, String Date, String FlightPlan){
        mFlightTime = FlightTime;
        mAirframe = Airframe;
        mTailNumber = TailNumber;
        mDate = Date;
        mFlightPlan = FlightPlan;
    }

    public String getFlightTime() {
        return mFlightTime;
    }
    public String getAirframe() {
        return mAirframe;
    }
    public String getTailNumber() {
        return mTailNumber;
    }
    public String getDate() {
        return mDate;
    }
    public String getFlightPlan() {
        return mFlightPlan;
    }
}
