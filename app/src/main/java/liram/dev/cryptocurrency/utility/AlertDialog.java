package liram.dev.cryptocurrency.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import liram.dev.cryptocurrency.R;

public class AlertDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int resourcesLayout;
    private String dialogTitle;

    public AlertDialog(@NonNull Context context, String dialogTitle) {
        super(context);
        this.context = context;
        this.dialogTitle = dialogTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        initialComponents();
    }

    private void initialComponents(){

        TextView tvTitle = findViewById(R.id.tv_dialog_title);
        Button confirm = findViewById(R.id.ok_deposit_btn);
        Button dismiss = findViewById(R.id.dismiss_dialog_btn);
        TextInputEditText inputValue = findViewById(R.id.et_deposit);
        confirm.setOnClickListener(this);
        tvTitle.setText(dialogTitle);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok_deposit_btn:
                System.out.println("OK CLICKED");
                break;
        }
    }

}
