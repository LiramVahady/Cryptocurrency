package liram.dev.cryptocurrency.ui.userProfile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.utility.Util;

//TODO: UPDATE THE BALANCE WHEN USER PRESS OK IMMEDIATELY
public class UserProfileFragment extends Fragment {

    private UserProfileViewModel viewModel;
    private ActivityResultLauncher<Intent> resultLauncher;
    String userBalance;
    //Components:
    private Button plusBth;
    private ImageView profileImage;
    private TextView tvHello;
    private TextView tvBalance;
    private Button portfolioBtn;
    private Button depositBtn;
    private Button changeCoinBtn;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configure();
        initialComponent(view);

    }

    private void configure(){
        viewModel = new UserProfileViewModel(getActivity().getApplication());
        getCurrentUserDetails();
    }

    private void initialComponent(View view){
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        navView.setVisibility(BottomNavigationView.GONE);
        plusBth = view.findViewById(R.id.add_pic_btn);
        tvHello = view.findViewById(R.id.tv_hello_user);
        tvBalance = view.findViewById(R.id.tv_balance);
        changeCoinBtn = view.findViewById(R.id.convert_coins_btn);
        depositBtn = view.findViewById(R.id.deposit_btn);
        portfolioBtn = view.findViewById(R.id.portfolio_btn);
        changeCoinBtn.setText("CHANGE \n" + "Coins");
        depositDialog();
    }

    private void uploadProfileImage(){
        plusBth.setOnClickListener(click->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null ){
                resultLauncher.launch(intent);
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null ){
                            Intent bundle = result.getData();
                        }
                    }
                });
    }

    private void getCurrentUserDetails(){
        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser->{
            if (currentUser != null){
                final String userName = currentUser.getUserName();
                userBalance = Util.priceFormat(currentUser.getUserBalance());
                tvHello.setText("Hello, " + userName);
                tvBalance.setText("Your Balance: \n" + userBalance);
            }
        });
    }

    private void depositDialog(){
        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDepositDialog();
            }
        });
    }

    public void configureDepositDialog(){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
        validUserDeposit();

    }

    private void validUserDeposit() {
        Button  ok = dialog.findViewById(R.id.ok_deposit_btn);
        Button dismiss = dialog.findViewById(R.id.dismiss_dialog_btn);
        EditText depositTextField = dialog.findViewById(R.id.et_deposit);

        depositTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textChange = charSequence.toString();
                        if (textChange.equals("")){
                            Toast.makeText(getContext(), "Insert Amount", Toast.LENGTH_LONG).show();
                            return;
                        }
                            Double deposit = new Double(textChange).doubleValue();
                            viewModel.updateUserBalance(deposit);
                            String depositFormat = Util.priceFormat(deposit);
                            reloadView();
                            //TODO: improve toast UI
                            Toast.makeText(getContext(), "Update Amount has been successfully " + depositFormat, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dismiss.setOnClickListener(clickDismiss->{
            dialog.dismiss();
        });
    }

    private void  reloadView(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new UserProfileFragment()).commit();
    }

}