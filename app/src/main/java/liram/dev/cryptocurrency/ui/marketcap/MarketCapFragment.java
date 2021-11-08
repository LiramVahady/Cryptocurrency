

/*
TODO:
package model : arrange
 */

package liram.dev.cryptocurrency.ui.marketcap;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.adapter.CurrenciesAdapter;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.ui.userProfile.UserProfileFragment;
import liram.dev.cryptocurrency.utility.Util;

//TODO: When User Come from login screen back screen crash the app not go back to login screen!!

public class MarketCapFragment extends Fragment {

    private MarketCapViewModel viewModel;
    private CurrenciesAdapter adapter;
    private RecyclerView currenciesRecyclerView;
    private Button profileBtn;
    private ProgressBar pbData;
    private EditText et_search;
    private ImageView ivSearch;
    private TextView title;
    private TextView userName;
    private TextView userBalance;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.marketcap_fragment, container, false);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Application application = getActivity().getApplication();
        viewModel = new MarketCapViewModel(application);
        reloadData();
        configureUI(view);
//       pbData.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void reloadData(){
        viewModel.getMarketList().observe(getViewLifecycleOwner(), crypto->{
            setAdapter(crypto, getContext());
           // pbData.setVisibility(ProgressBar.GONE);

           // searchCurrency(cryptoList);
            //hideComponent(false);
        });
        viewModel.currentUserDetails().observe(getViewLifecycleOwner(), currentUser->{
            userName.setText("Hello, " + currentUser.getUserName());
            String balance = Util.priceFormat(currentUser.getUserBalance());
            userBalance.setText("Your Balance: " + balance);
        });
    }

    private void configureUI(View view){
        initialComponent(view);
       // hideComponent(false);
        moveToUserProfileSetting();
       BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
    }

    private void initialComponent(View view) {
        currenciesRecyclerView = view.findViewById(R.id.rv_marketcap);
        profileBtn = view.findViewById(R.id.profile_btn);
        userName = view.findViewById(R.id.tv_user_name);
        userBalance = view.findViewById(R.id.tv_user_balance);
//        pbData = view.findViewById(R.id.pb_data);
//        et_search = view.findViewById(R.id.et_search);
//        ivSearch = view.findViewById(R.id.iv_search);
//        title = view.findViewById(R.id.tv_title);
        //hideComponent(true);
    }

    private void hideComponent(boolean isHidden){
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(BottomNavigationView.VISIBLE);
        if (isHidden){
            et_search.setVisibility(EditText.GONE);
            ivSearch.setVisibility(ImageView.GONE);
            title.setVisibility(TextView.GONE);
        }else{
         //   currenciesRecyclerView.setVisibility(RecyclerView.VISIBLE);
            et_search.setVisibility(EditText.VISIBLE);
            ivSearch.setVisibility(ImageView.VISIBLE);
            title.setVisibility(TextView.VISIBLE);
        }
    }

    private void setAdapter(List<Crypto> list, Context context) {
        adapter = new CurrenciesAdapter(list, context);
        currenciesRecyclerView.setAdapter(adapter);
        currenciesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void moveToUserProfileSetting(){
        profileBtn.setOnClickListener(listener->{
            System.out.println("Print some ");
            getActivity().getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.frame, new UserProfileFragment())
                    .addToBackStack("BACK_MARKET_CAP")
                    .commit();

        });
    }

    //    private void searchCurrency(List<Crypto> list){
//        et_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//               adapter.getFilter().filter(charSequence.toString().toLowerCase());
//            }
//
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//    }

}