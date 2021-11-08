package liram.dev.cryptocurrency.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.ui.login.LoginUserFragment;

public class IntroActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureView();
    }

    private void configureView(){
        setContentView(R.layout.activity_intro);
        getSupportFragmentManager().beginTransaction().replace(R.id.intro, new LoginUserFragment()).commit();
    }

}