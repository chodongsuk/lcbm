package kr.com.lcbm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import kr.ds.handler.CategoryHandler;

/**
 * Created by Administrator on 2016-03-28.
 */
public class RegisActivity extends  BaseActivity {
    private CategoryHandler mSavedata;
    private AppCompatEditText mEditText;
    private Button mButton;
    private TextInputLayout mTextInputLayout;
    private Toolbar mToolbar;
    private Bundle mBundle;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        if(savedInstanceState != null){
            mSavedata = (CategoryHandler) savedInstanceState.getParcelable("data");
            name = mSavedata.getName();

        }else{
            mBundle = getIntent().getExtras();
            mSavedata = (CategoryHandler) mBundle.getParcelable("data");
            name = mSavedata.getName();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(name);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mTextInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        mEditText = (AppCompatEditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submitForm()) {
                    Intent it = new Intent(Intent.ACTION_SEND);
                    String[] mailaddr = {"lcbmc@naver.com"};
                    it.setType("plaine/text");
                    it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
                    it.putExtra(Intent.EXTRA_SUBJECT, "입점 문의합니다."); // 제목
                    it.putExtra(Intent.EXTRA_TEXT, mEditText.getText().toString()); // 첨부내용
                    startActivity(it);
                }
            }
        });

        mEditText.addTextChangedListener(new MyTextWatcher(mEditText));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putParcelable("data", mSavedata);
    }

    private Boolean submitForm() {
        if (!validate()) {
            return false;
        }
        return true;
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editText:
                    validate();
                    break;

            }
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validate() {
        if (mEditText.getText().toString().trim().isEmpty()) {
            mTextInputLayout.setError("입력해주시기 바랍니다.");
            requestFocus(mEditText);
            return false;
        } else {
            mTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }
}
