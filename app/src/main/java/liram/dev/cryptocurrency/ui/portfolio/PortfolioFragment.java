package liram.dev.cryptocurrency.ui.portfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.adapter.PortfolioAdapter;

public class PortfolioFragment extends Fragment {

    //Components:
    private RecyclerView rvPurchase;
    private PortfolioViewModel portfolioViewModel;
    private PortfolioAdapter adapter;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      portfolioViewModel = new PortfolioViewModel();
      View root = inflater.inflate(R.layout.fragment_portfolio, container, false);

       return  root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("View..");
        initialComponents(view);
        portfolioViewModel.getUserPurchaseList().observe(getViewLifecycleOwner(), observe->{
        // adapter = new PortfolioAdapter(getContext(), observe);
         rvPurchase.setAdapter(adapter);
         rvPurchase.setLayoutManager(new LinearLayoutManager(getContext()));

        });
    }

    private void initialComponents(View view){
        rvPurchase = view.findViewById(R.id.rv_portfolio_crypto);
    }
}
