package com.ics.ar.matri.activity.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ics.ar.matri.Models.CommanDTO;
import com.ics.ar.matri.R;
import com.ics.ar.matri.SysApplication;
import com.ics.ar.matri.database.TestAdapter;
import com.ics.ar.matri.interfaces.Consts;
import com.ics.ar.matri.interfaces.OnSpinerItemClick;
import com.ics.ar.matri.utils.SpinnerDialog;
import com.ics.ar.matri.view.CustomButton;
import com.ics.ar.matri.view.CustomEditText;
import com.ics.ar.matri.view.CustomTextView;
import com.ics.ar.matri.view.InputFieldView;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity implements View.OnClickListener {
    private String TAG = Search.class.getSimpleName();
    private Context mContext;
    private LinearLayout llBride, llGroom, llBack;
    private CustomTextView tvBride, tvGroom;
    private InputFieldView inf_district;
    private CustomEditText etMaritial, etManglik, etState, etDistrict, etCaste;
    private ArrayList<CommanDTO> stateList = new ArrayList<>();
    private ArrayList<CommanDTO> districtList = new ArrayList<>();
    private SpinnerDialog spinnerMaritial, spinnerManglik, spinnerState, spinnerDistrict, spinnerCaste;
    public SysApplication sysApplication;
    HashMap<String, String> parms = new HashMap<>();
    TestAdapter mDbHelper;
    private CustomButton btnSearch;
    private ArrayList<CommanDTO> casteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = Search.this;
        sysApplication = SysApplication.getInstance(mContext);
        mDbHelper = new TestAdapter(mContext);

        mDbHelper.createDatabase();
        mDbHelper.open();
        parms.put(Consts.GENDER, "F");
        setUiAction();
    }

    public void setUiAction() {
        llBride = findViewById(R.id.llBride);
        llGroom = findViewById(R.id.llGroom);
        tvBride = findViewById(R.id.tvBride);
        tvGroom = findViewById(R.id.tvGroom);
        llBack = findViewById(R.id.llBack);

        llBride.setOnClickListener(this);
        llGroom.setOnClickListener(this);
        llBack.setOnClickListener(this);

        etMaritial = findViewById(R.id.etMaritial);
      //  etManglik = findViewById(R.id.etManglik);
        etState = findViewById(R.id.etState);
        etDistrict = findViewById(R.id.etDistrict);
        etCaste = findViewById(R.id.etCaste);

        etMaritial.setOnClickListener(this);
      //  etManglik.setOnClickListener(this);
        etState.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        etCaste.setOnClickListener(this);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        inf_district = findViewById(R.id.inf_district);

        spinnerMaritial = new SpinnerDialog(Search.this, sysApplication.getMaritalList(), getResources().getString(R.string.select_maritial_status), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerMaritial.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etMaritial.setText(item);
                parms.put(Consts.MARITAL_STATUS, id);
            }
        });
        spinnerManglik = new SpinnerDialog(Search.this, sysApplication.getManglikList(), getResources().getString(R.string.select_manglik), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerManglik.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etManglik.setText(item);
                parms.put(Consts.MANGLIK, id);

            }
        });
        casteList = new ArrayList<>();
        casteList.add(new CommanDTO("1", "Muslim"));
        spinnerCaste = new SpinnerDialog(Search.this, casteList, getResources().getString(R.string.select_caste), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerCaste.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
             //   etCaste.setText(item);
                etCaste.setText(item);
                parms.put(Consts.CASTE, id);
            }
        });
        stateList = new ArrayList<>();
        stateList = mDbHelper.getAllState("en");
        spinnerState = new SpinnerDialog(Search.this, stateList, getResources().getString(R.string.select_state), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerState.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etState.setText(item);
                parms.put(Consts.STATE, id);
                inf_district.setVisibility(View.VISIBLE);

                districtList = new ArrayList<>();
                districtList = mDbHelper.getAllDistrict(id, "en");
                showDistrict();
            }
        });

    }

    public void showDistrict() {
        spinnerDistrict = new SpinnerDialog(Search.this, districtList, getResources().getString(R.string.select_district), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDistrict.setText(item);
                parms.put(Consts.DISTRICT, id);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                break;
            case R.id.etMaritial:
                spinnerMaritial.showSpinerDialog();
                break;
            case R.id.etManglik:
                spinnerManglik.showSpinerDialog();
                break;
            case R.id.etState:
                spinnerState.showSpinerDialog();
                break;
            case R.id.etDistrict:
                spinnerDistrict.showSpinerDialog();
                break;
            case R.id.etCaste:
                spinnerCaste.showSpinerDialog();
                break;
            case R.id.llBride:
                setSelected(true, false);
                parms.put(Consts.GENDER, "F");
                break;
            case R.id.llGroom:
                setSelected(false, true);
                parms.put(Consts.GENDER, "M");
                break;
            case R.id.btnSearch:
                Intent in = new Intent(mContext, SearchResult.class);
                in.putExtra(Consts.SEARCH_PARAM, parms);
                startActivity(in);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;

        }
    }

    public void setSelected(boolean firstBTN, boolean secondBTN) {
        if (firstBTN) {
            llBride.setBackground(getResources().getDrawable(R.drawable.gender_selecte));
            llGroom.setBackground(getResources().getDrawable(R.drawable.gender_unselecte));
            tvBride.setTextColor(getResources().getColor(R.color.white));
            tvGroom.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if (secondBTN) {
            llBride.setBackground(getResources().getDrawable(R.drawable.gender_unselecte));
            llGroom.setBackground(getResources().getDrawable(R.drawable.gender_selecte));

            tvBride.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvGroom.setTextColor(getResources().getColor(R.color.white));
        }

        llBride.setSelected(firstBTN);
        llGroom.setSelected(secondBTN);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
