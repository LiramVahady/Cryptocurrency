package liram.dev.cryptocurrency.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import liram.dev.cryptocurrency.MainActivity;
import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.utility.UserPreferencesManager;


public class LoginUserFragment extends Fragment {

    private LoginUserViewModel userDetailsViewModel;
    //Components:
    private Button login;
    private Button register;
    private Button skip;
    private LinearLayout loginLayout;
    private LinearLayout registerLayout;
    private EditText userName;
    private EditText userAmount;
    private EditText password;


    public static LoginUserFragment newInstance() {
        return new LoginUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.user_details_trade_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("view loaded");
        configureViews(view);
        handleClickListener(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            userDetailsViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), isLogin->{
                //TODO: APPEAR DIALOG
                if (isLogin){
                    UserPreferencesManager.putBooleanValue(getContext(),getString(R.string.USER_LOG_PREFERENCES_KEY),true);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    //TODO: Appear user dialog
                    System.out.println("user name or password are not correct");
                }
            });
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configureViews(View view){
        Application application = this.getActivity().getApplication();
        userDetailsViewModel = new LoginUserViewModel(application);
        userName = view.findViewById(R.id.et_username);
        password = view.findViewById(R.id.et_pass_login);
       // skip = view.findViewById(R.id.skip_btn);
        login = view.findViewById(R.id.login_btn);
        register = view.findViewById(R.id.register_btn);
        handleClickListener(view);
    }

    private void fadeLayoutUp(PopupWindow popupWindow){

        popupWindow.setAnimationStyle(R.anim.anumation_popup_view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleClickListener(View view){
//        skip.setOnClickListener(click ->{
//         userDetailsViewModel.userSignUpLater();
//        });

        userDetailsViewModel.getRegisterNotification().observe(getViewLifecycleOwner(), message -> {
            System.out.println(message);
        });

        login.setOnClickListener(click -> {
            String userName =  this.userName.getText().toString();
            String password = this.password.getText().toString();

            userDetailsViewModel.userLogin(userName, password);

        });

        register.setOnClickListener(click->{
        configureRegisterView(view);

        });
    }


    /*
    PopUp quick register view . option to dismiss with x button,
    this layout doesn't has different java class to wtite the logic for validate user details
    in this method we handle components of layout.
     */
    //TODO: Dialog!
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configureRegisterView(View view){
        //define constraint to layout:
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.register_layout, null);
        popView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anumation_popup_view));

        int width = view.getWidth();
        int height = view.getHeight();


        //using popUp class
        final PopupWindow popupWindow = new PopupWindow(popView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0 , 0 );

        //inital components:
        EditText userName = popView.findViewById(R.id.et_user_name);
        EditText password = popView.findViewById(R.id.et_user_password);
        EditText repeatPassword = popView.findViewById(R.id.et_user_repassword);
        Button registerButton = popView.findViewById(R.id.tap_register);


        //handle in react to components:
//        userDetailsViewModel.getRegisterNotification().observe(getViewLifecycleOwner(), message->{
//           //Update UI if register success
//            System.out.println("Message = " + message);
//        });

       registerButton.setOnClickListener(v->{
            userDetailsViewModel.createUser(userName.getText().toString(),
                                            password.getText().toString(),
                                            repeatPassword.getText().toString());
       });
       //TODO: just when the all fields true -> editText = ""
        userName.setText("");
        password.setText("");
        repeatPassword.setText("");

        Button dismiss = popView.findViewById(R.id.dismiss_btn);
        dismiss.setOnClickListener(click->{
            popupWindow.dismiss();
        });

    }
}