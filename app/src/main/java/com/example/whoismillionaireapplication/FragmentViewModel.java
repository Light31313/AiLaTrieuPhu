package com.example.whoismillionaireapplication;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class FragmentViewModel extends ViewModel {
    private final MutableLiveData<Integer> level = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isVisible = new MutableLiveData<>();
    private final MutableLiveData<Integer> trueCase = new MutableLiveData<>();

    public void setLevel(Integer input) {
        level.setValue(input);
    }

    public void setVisible(Boolean input) {
        isVisible.setValue(input);
    }

    public void setTrueCase(Integer trueCase) {
        this.trueCase.setValue(trueCase);
    }

    public LiveData<Integer> getLevel() {
        return level;
    }

    public LiveData<Boolean> getVisible() {
        return isVisible;
    }

    public LiveData<Integer> getTrueCase() {
        return trueCase;
    }

}
