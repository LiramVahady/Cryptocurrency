package liram.dev.cryptocurrency.ui.favoriteTracker;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrackerFragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TrackerFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}