package com.skripsi.yogi.planner.Common;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyFormat {

    public static TextWatcher onTextChangedListener(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    editText.setText(formattedString);
                    editText.setSelection(editText.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }
        };
    }

    public static double parseDouble(String input) {
        Number number = null;
        try {
            number = NumberFormat.getInstance(Locale.US).parse(input);
        } catch (ParseException e) {
            System.out.print(e);
        }
        return number.doubleValue();
    }

    public static void onFillEditText(EditText editText) {
        double number = Double.parseDouble(editText.getText().toString());
        String currency = NumberFormat.getNumberInstance(Locale.US).format(number);
        editText.setText(currency);
    }

    public static void onFillTextView(TextView textView) {
        double number = Double.parseDouble(textView.getText().toString());
        Locale localeID = new Locale("in", "ID");
        String currency = NumberFormat.getNumberInstance(Locale.US).format(number);
        textView.setText(currency);
    }
}
