package com.example.whoismillionaireapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> event5050 = new MutableLiveData<>();
    private final MutableLiveData<Boolean> stopEvent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> otherEvent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> enableClickListener = new MutableLiveData<>();

    public void setEvent5050(Boolean event5050) {
        this.event5050.setValue(event5050);
    }

    public LiveData<Boolean> getEvent5050() {
        return event5050;
    }

    public void setStopEvent(Boolean stopEvent) {
        this.stopEvent.setValue(stopEvent);
    }

    public LiveData<Boolean> getStopEvent() {
        return stopEvent;
    }

    public void setOtherEvent(Boolean otherEvent) {
        this.otherEvent.setValue(otherEvent);
    }

    public LiveData<Boolean> getOtherEvent() {
        return otherEvent;
    }

    public void setEnableClickListener(Boolean enableClickListener) {
        this.enableClickListener.setValue(enableClickListener);
    }

    public LiveData<Boolean> getEnableClickListener() {
        return enableClickListener;
    }
}
