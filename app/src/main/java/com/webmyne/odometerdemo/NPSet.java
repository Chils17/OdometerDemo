package com.webmyne.odometerdemo;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by chiragpatel on 15-05-2017.
 */

public class NPSet extends LinearLayout {

    private LinearLayout llParent;
    private int slot, odo_bg_color, bg_color, odo_text_color, background;
    private String read;
    private TextView tvOutPut;

    public NPSet(Context context) {
        super(context);
    }

    public NPSet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public NPSet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {

        setOrientation(HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker, this, true);

        llParent = (LinearLayout) findViewById(R.id.llParent);
        tvOutPut = (TextView) findViewById(R.id.tvOutPut);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NPSet);

        try {
            bg_color = typedArray.getColor(R.styleable.NPSet_bg_color, ContextCompat.getColor(context, R.color.black));
            background = typedArray.getResourceId(R.styleable.NPSet_background, ContextCompat.getColor(context, R.color.black));
            odo_bg_color = typedArray.getColor(R.styleable.NPSet_odo_bg_color, ContextCompat.getColor(context, R.color.black));
            odo_text_color = typedArray.getColor(R.styleable.NPSet_odo_text_color, ContextCompat.getColor(context, R.color.white));
            slot = typedArray.getInt(R.styleable.NPSet_slots, 0);
            read = typedArray.getString(R.styleable.NPSet_reading);
        } finally {
            typedArray.recycle();
        }

        llParent.setBackgroundColor(bg_color);


        if (TextUtils.isEmpty(read) || read.length() != slot) {

            TextView textView = new TextView(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            textView.setLayoutParams(lp);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            textView.setText("Invalid Values");
            llParent.addView(textView);

        } else {
            createDynamicNumberPicker(context);
        }

        /*final int N = array.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.NPSet_slots:
                    slot = array.getInt(attr, 0);
                    setNumberPicker(context);
                    break;

                case R.styleable.NPSet_reading:
                    read = array.getString(attr);
                    setReading(context);
                    break;
            }
        }*/

    }

    private void createDynamicNumberPicker(Context context) {

        for (int i = 1; i <= slot; i++) {
            NumberPicker numberPicker = new NumberPicker(context);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
            //lp.setMargins(2, 0, 2, 0);
            lp.gravity = Gravity.CENTER;
            numberPicker.setLayoutParams(lp);

            setNumberPickerTextColor(numberPicker, odo_text_color);
//            numberPicker.setBackgroundColor(odo_bg_color);
            numberPicker.setBackgroundResource(background);

            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setId(i - 1);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(9);
            numberPicker.setWrapSelectorWheel(true);

            int read_val = Character.getNumericValue(read.charAt(i - 1));
            numberPicker.setValue(read_val);

            llParent.addView(numberPicker);
        }

    }

    public static void setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                } catch (NoSuchFieldException e) {
                    Log.w("NumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Log.w("NumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Log.w("NumberPickerTextColor", e);
                }
            }
        }
    }


    public String getFinalOdoMiterValue() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < llParent.getChildCount(); i++) {

            NumberPicker localNumberPicker = (NumberPicker) llParent.getChildAt(i);
            localNumberPicker.getValue();

            stringBuilder.append(localNumberPicker.getValue());
            stringBuilder.append(" ");

        }

        return stringBuilder.toString();
    }


}
