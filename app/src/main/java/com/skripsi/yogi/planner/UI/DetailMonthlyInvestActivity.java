package com.skripsi.yogi.planner.UI;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.skripsi.yogi.planner.Common.CurrencyFormat;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.Common.Session;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.R;
import com.tooltip.Tooltip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailMonthlyInvestActivity extends AppCompatActivity {

    private Session session;
    private PlanningAPI repository;
    private int id;

    private TextView bunga, pajak, biayaAdmin, target, totalKenaPajak, totalBiayaAdmin, tabunganBersih,
            totalBunga, jangkaWaktu, monthlyInvest, alreadyInvest;
    private TextView txtTargetDana, txtTabunganBersih;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_monthly_invest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new Session(this);
        repository = new PlanningAPI(this);

        table = (TableLayout) findViewById(R.id.table);
        bunga = (TextView) findViewById(R.id.bunga);
        pajak = (TextView) findViewById(R.id.pajakBunga);
        target = (TextView) findViewById(R.id.target);
        biayaAdmin = (TextView) findViewById(R.id.biayaAdmin);
        totalKenaPajak = (TextView) findViewById(R.id.totalPajakBunga);
        totalBiayaAdmin = (TextView) findViewById(R.id.totalBiayaAdmin);
        tabunganBersih = (TextView) findViewById(R.id.tabunganBersih);
        totalBunga = (TextView) findViewById(R.id.totalBunga);
        jangkaWaktu = (TextView) findViewById(R.id.jangkaWaktu);
        monthlyInvest = (TextView) findViewById(R.id.monthlyInvest);
        alreadyInvest = (TextView) findViewById(R.id.alreadyInvest);

        txtTargetDana = (TextView) findViewById(R.id.txtTarget);
        txtTabunganBersih = (TextView) findViewById(R.id.txtTabunganBersih);
        txtTargetDana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tooltip tooltip = new Tooltip.Builder(txtTargetDana)
                        .setText("Target dana adalah biaya perencanaan yang sudah terpengaruh inflasi")
                        .setDismissOnClick(true)
                        .setBackgroundColor(Color.CYAN)
                        .show();
            }
        });

        txtTabunganBersih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tooltip tooltip = new Tooltip.Builder(txtTabunganBersih)
                        .setText("Tabungan bersih = tabungan akhir - biaya admin - pajak bunga")
                        .setDismissOnClick(true)
                        .setBackgroundColor(Color.CYAN)
                        .show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        repository.getById(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    progressDialog.hide();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        target.setText(jsonObject.getString("futureCost"));
                        CurrencyFormat.onFillTextView(target);
                        bunga.setText(jsonObject.getString("interestRate"));
                        pajak.setText(jsonObject.getString("pajakBunga"));
                        biayaAdmin.setText(jsonObject.getString("biayaAdmin"));
                        CurrencyFormat.onFillTextView(biayaAdmin);
                        totalBunga.setText(jsonObject.getString("totalBunga"));
                        CurrencyFormat.onFillTextView(totalBunga);
                        totalKenaPajak.setText(jsonObject.getString("totalPajakBunga"));
                        CurrencyFormat.onFillTextView(totalKenaPajak);
                        totalBiayaAdmin.setText(jsonObject.getString("totalBiayaAdmin"));
                        CurrencyFormat.onFillTextView(totalBiayaAdmin);
                        jangkaWaktu.setText(jsonObject.getString("jangkaWaktu"));
                        monthlyInvest.setText(jsonObject.getString("monthlyInvest"));
                        CurrencyFormat.onFillTextView(monthlyInvest);
                        alreadyInvest.setText(jsonObject.getString("alreadyInvest"));
                        CurrencyFormat.onFillTextView(alreadyInvest);
                        double calTabunganBersih = jsonObject.getDouble("futureCost") - jsonObject.getDouble("totalPajakBunga");
                        tabunganBersih.setText(String.valueOf(calTabunganBersih));
                        CurrencyFormat.onFillTextView(tabunganBersih);

                        repository.getRincianTabungan(new ResponseCallBack() {
                            @Override
                            public void onResponse(JSONArray response) {
                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        TableRow tableRow = new TableRow(getApplicationContext());
                                        tableRow.setBackgroundColor(getResources().getColor(R.color.colorMidnightBlue));

                                        TextView bulan = new TextView(getApplicationContext());
                                        bulan.setTextColor(getResources().getColor(R.color.colorWhite));
                                        bulan.setBackground(getDrawable(R.drawable.cellborder));
                                        bulan.setTextSize(15);
                                        bulan.setGravity(Gravity.RIGHT);
                                        bulan.setPadding(10, 10, 10, 10);
                                        bulan.setText(jsonObject.getString("bulan"));
                                        tableRow.addView(bulan);

                                        TextView tabunganAwal = new TextView(getApplicationContext());
                                        tabunganAwal.setTextColor(getResources().getColor(R.color.colorWhite));
                                        tabunganAwal.setBackground(getDrawable(R.drawable.cellborder));
                                        tabunganAwal.setTextSize(15);
                                        tabunganAwal.setGravity(Gravity.RIGHT);
                                        tabunganAwal.setPadding(10, 10, 10, 10);
                                        tabunganAwal.setText(jsonObject.getString("tabunganAwal"));
                                        CurrencyFormat.onFillTextView(tabunganAwal);
                                        tableRow.addView(tabunganAwal);

                                        TextView setoranBulanan = new TextView(getApplicationContext());
                                        setoranBulanan.setTextColor(getResources().getColor(R.color.colorWhite));
                                        setoranBulanan.setBackground(getDrawable(R.drawable.cellborder));
                                        setoranBulanan.setTextSize(15);
                                        setoranBulanan.setGravity(Gravity.RIGHT);
                                        setoranBulanan.setPadding(10, 10, 10, 10);
                                        setoranBulanan.setText(jsonObject.getString("setoranBulanan"));
                                        CurrencyFormat.onFillTextView(setoranBulanan);
                                        tableRow.addView(setoranBulanan);

                                        TextView bunga = new TextView(getApplicationContext());
                                        bunga.setTextColor(getResources().getColor(R.color.colorWhite));
                                        bunga.setBackground(getDrawable(R.drawable.cellborder));
                                        bunga.setTextSize(15);
                                        bunga.setGravity(Gravity.RIGHT);
                                        bunga.setPadding(10, 10, 10, 10);
                                        bunga.setText(jsonObject.getString("bunga"));
                                        CurrencyFormat.onFillTextView(bunga);
                                        tableRow.addView(bunga);

                                        TextView pajak = new TextView(getApplicationContext());
                                        pajak.setTextColor(getResources().getColor(R.color.colorWhite));
                                        pajak.setBackground(getDrawable(R.drawable.cellborder));
                                        pajak.setTextSize(15);
                                        pajak.setGravity(Gravity.RIGHT);
                                        pajak.setPadding(10, 10, 10, 10);
                                        pajak.setText(jsonObject.getString("pajak"));
                                        CurrencyFormat.onFillTextView(pajak);
                                        tableRow.addView(pajak);

                                        TextView tabunganAkhir = new TextView(getApplicationContext());
                                        tabunganAkhir.setTextColor(getResources().getColor(R.color.colorWhite));
                                        tabunganAkhir.setBackground(getDrawable(R.drawable.cellborder));
                                        tabunganAkhir.setTextSize(15);
                                        tabunganAkhir.setGravity(Gravity.RIGHT);
                                        tabunganAkhir.setPadding(10, 10, 10, 10);
                                        tabunganAkhir.setText(jsonObject.getString("tabunganAkhir"));
                                        CurrencyFormat.onFillTextView(tabunganAkhir);
                                        tableRow.addView(tabunganAkhir);

                                        table.addView(tableRow);
                                    }
                                } catch (JSONException e) {
                                    Log.d("Error: ", e.getMessage());
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Snackbar.make(findViewById(R.id.txtTabunganBersih), "Error: " + error, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }, id);
                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onError(String error) {
                progressDialog.hide();

                Snackbar.make(findViewById(R.id.txtTabunganBersih), "Error: " + error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
