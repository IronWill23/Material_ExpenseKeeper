package com.library.ironwill.expensekeeper.view.MaterialLoginView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.library.ironwill.expensekeeper.R;
import com.rey.material.widget.CheckBox;

public class DefaultLoginView extends FrameLayout {

    public interface DefaultLoginViewListener {
        void onLogin(TextInputLayout loginUser, TextInputLayout loginPass);
    }

    private DefaultLoginViewListener listener;


    public DefaultLoginView(Context context) {
        this(context, null);
    }

    public DefaultLoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefaultLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login_card, this, true);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DefaultLoginView,
                0, 0);

        final TextInputLayout loginUser = (TextInputLayout) findViewById(R.id.login_user);
        final TextInputLayout loginPwd = (TextInputLayout) findViewById(R.id.login_pwd);
        CheckBox mCheckBox = (CheckBox) findViewById(R.id.cb_remember);
        TextView loginBtn = (TextView) findViewById(R.id.login_btn);

        findViewById(R.id.login_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLogin(loginUser, loginPwd);
                }
            }
        });
        try {
            String string;

            string = a.getString(R.styleable.DefaultLoginView_loginHint);
            if (string != null) {
                loginUser.setHint(string);
            }

            string = a.getString(R.styleable.DefaultLoginView_loginPasswordHint);
            if (string != null) {
                loginPwd.setHint(string);
            }

            string = a.getString(R.styleable.DefaultLoginView_loginActionText);
            if (string != null) {
                loginBtn.setText(string);
            }

            int color = a.getColor(R.styleable.DefaultLoginView_loginTextColor, ContextCompat.getColor(getContext(), R.color.black));
            loginUser.getEditText().setTextColor(color);
            loginPwd.getEditText().setTextColor(color);

            Boolean isChecked = a.getBoolean(R.styleable.DefaultLoginView_loginRememberMe, true);
            mCheckBox.setSelected(isChecked);

        } finally {
            a.recycle();
        }
    }

    public void setListener(DefaultLoginViewListener listener) {
        this.listener = listener;
    }
}
