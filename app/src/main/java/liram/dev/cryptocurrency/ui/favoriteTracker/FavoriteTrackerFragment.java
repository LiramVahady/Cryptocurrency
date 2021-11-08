package liram.dev.cryptocurrency.ui.favoriteTracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import liram.dev.cryptocurrency.R;

public class FavoriteTrackerFragment extends Fragment {

    private  TrackerFragmentViewModel trackerFragmentViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View root = inflater.inflate(R.layout.favorite_tracker_fragment, container, false);

        return root;
    }
}