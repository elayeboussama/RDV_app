package com.example.rdv_app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rdv_app.Models.rdvDb.RDV;
import com.example.rdv_app.Models.rdvDb.DbManager;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RdvListActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    private DbManager db;

    Button btn_delete ;
    Button btn_add ;
    Button btn_edit ;

    final int EDIT_REQUEST_CODE = 15;

    ArrayList<RDV> dataSelected = new ArrayList<RDV>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_list_activity);

        mProgressBar = new ProgressDialog(this);

        // setup the table
        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);
        mTableLayout.setStretchAllColumns(true);
        startLoadData();
        btn_delete = findViewById(R.id.btn_rdvList_del);
        btn_add = findViewById(R.id.btn_rdvList_add);
        btn_edit = findViewById(R.id.btn_rdvList_edit);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataSelected.size()>0){
                    db = new DbManager(getApplicationContext());
                    try {
                        db.open();
                    } catch (SQLDataException throwables) {
                        throwables.printStackTrace();
                    }
                    for(int i=0; i<dataSelected.size(); i++) {
                        System.out.println(dataSelected.get(i).toString());
                        db.delete(String.valueOf(dataSelected.get(i).id));
                    }

                    loadData();
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddRdvActivity.class);
                startActivity(i);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dataSelected.size()>0){
                    db = new DbManager(getApplicationContext());
                    try {
                        db.open();
                    } catch (SQLDataException throwables) {
                        throwables.printStackTrace();
                    }

                    Intent intent = new Intent(getApplicationContext(), EditRdvActivity.class);


                    intent.putExtra("id", String.valueOf(dataSelected.get(0).id));
                    intent.putExtra("date", dataSelected.get(0).date);
                    intent.putExtra("time", dataSelected.get(0).time);
                    intent.putExtra("title", dataSelected.get(0).title);
                    intent.putExtra("content", dataSelected.get(0).content);

                    intent.putExtra("requestCode", EDIT_REQUEST_CODE);
                    startActivity(intent);


                    loadData();
                }


            }
        });
    }



    public void startLoadData() {

        mProgressBar.setCancelable(false);
        mProgressBar.setMessage("Fetching Invoices..");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);

    }

    public void loadData() {

        int leftRowMargin=0;
        int topRowMargin=0;
        int rightRowMargin=0;
        int bottomRowMargin = 0;
        int textSize = 0, smallTextSize =0, mediumTextSize = 0;

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
        mediumTextSize = (int) getResources().getDimension(R.dimen.font_size_medium);
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------


        db = new DbManager(this);
        try {
            db.open();
        } catch (SQLDataException throwables) {
            throwables.printStackTrace();
        }

        ArrayList<RDV> data = new ArrayList<RDV>();
        Cursor c = db.fetch();
        if (c == null) {
            Toast.makeText(this, "there is no data in the table", Toast.LENGTH_LONG).show();
        } else {

            if (c.moveToFirst()){


                do{
                    @SuppressLint("Range")
                    int idFromCursor = Integer.parseInt(c.getString(c.getColumnIndex("id_")));
                    @SuppressLint("Range")
                    String dateFromCursor = c.getString(c.getColumnIndex("date"));
                    @SuppressLint("Range")
                    String timeFromCursor = c.getString(c.getColumnIndex("time"));
                    @SuppressLint("Range")
                    String titleFromCursor = c.getString(c.getColumnIndex("title"));
                    @SuppressLint("Range")
                    String contentFromCursor = c.getString(c.getColumnIndex("content"));

                    data.add(new RDV(idFromCursor,dateFromCursor,timeFromCursor,titleFromCursor,contentFromCursor));

                }while(c.moveToNext());


            }

        }
        c.close();
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int rows = data.size();
        getSupportActionBar().setTitle("Rendez-vous programm??s (" + String.valueOf(rows) + ")");
        TextView textSpacer = null;

        mTableLayout.removeAllViews();

        // -1 means heading row
        for(int i = -1; i < rows; i ++) {
            RDV row = null;
            if (i > -1)
                row = data.get(i);
            else {
                textSpacer = new TextView(this);
                textSpacer.setText("");

            }
            // data columns
//            final TextView tv = new TextView(this);
//            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//
//            tv.setGravity(Gravity.LEFT);
//
//            tv.setPadding(5, 15, 0, 15);
//            if (i == -1) {
//                tv.setText("Inv.#");
//                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
//            } else {
//                tv.setBackgroundColor(Color.parseColor("#f8f8f8"));
//                tv.setText(String.valueOf(row.id));
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//            }





            // add table row
            final TableRow tr = new TableRow(this);
            tr.setId(i + 1);
            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);
            tr.setPadding(0,0,0,0);
            tr.setLayoutParams(trParams);









            //-------------------------------------------------------------------------------------------

            final CheckBox cb1= new CheckBox(this);
            final TextView tv1title= new TextView(this);
            if (i == -1) {

                tv1title.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv1title.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tv1title.setGravity(Gravity.LEFT);

                tv1title.setPadding(5, 15, 0, 15);
                tv1title.setText("");
                tr.addView(tv1title);
            }else{
                cb1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                cb1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                cb1.setGravity(Gravity.CENTER);
//            tv2.setLines(2);
                tr.addView(cb1);

                RDV finalRow = row;
                cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                       @Override
                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                           if(finalRow !=null){
                               dataSelected.add(finalRow);
                           }
                       }
                   }
                );
            }


            //-------------------------------------------------------------------------------------------

            final TextView tv2 = new TextView(this);

            if (i == -1) {
                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            } else {
                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }

            tv2.setGravity(Gravity.LEFT);
//            tv2.setLines(2);
            tv2.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tv2.setText("Title");
//                tv2.setBackgroundColor(Color.parseColor("#f7f7f7"));
            }else {
//                tv2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv2.setTextColor(Color.parseColor("#000000"));
                tv2.setText(row.title);
                tv2.setMaxLines(20);

            }


            //Date & Time

            final LinearLayout layCustomer = new LinearLayout(this);
            layCustomer.setOrientation(LinearLayout.VERTICAL);
            layCustomer.setPadding(0, 10, 0, 10);
            layCustomer.setBackgroundColor(Color.parseColor("#f8f8f8"));
            layCustomer.setMinimumWidth(250);

            final TextView tv3 = new TextView(this);
            if (i == -1) {
                tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv3.setPadding(5, 5, 5, 5);
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
//                tv2.setMaxLines(20);

            } else {
                tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv3.setPadding(5, 0, 0, 5);
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//                tv2.setMaxLines(20);

            }

            tv3.setGravity(Gravity.TOP);
//            tv3.setLines(2);
//            tv3.setMaxLines(20);


            if (i == -1) {
                tv3.setText("Date");
                tv3.setBackgroundColor(Color.parseColor("#f0f0f0"));

            } else {
//                tv3.setBackgroundColor(Color.parseColor("#f8f8f8"));
                tv3.setTextColor(Color.parseColor("#000000"));
                tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tv3.setText((CharSequence) row.date);
                tv2.setMaxLines(20);

            }
            layCustomer.addView(tv3);


            if (i > -1) {
                final TextView tv3b = new TextView(this);
                tv3b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                tv3b.setGravity(Gravity.RIGHT);
                tv3b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tv3b.setPadding(5, 0, 0, 5);
                tv3b.setTextColor(Color.parseColor("#aaaaaa"));
//                tv3b.setBackgroundColor(Color.parseColor("#f8f8f8"));
                tv3b.setText((CharSequence) row.time);
                layCustomer.addView(tv3b);
//                tv2.setMaxLines(20);

            }


            //Content
            final LinearLayout layAmounts = new LinearLayout(this);
            layAmounts.setOrientation(LinearLayout.VERTICAL);
            layAmounts.setGravity(Gravity.RIGHT);
            layAmounts.setPadding(0, 10, 0, 10);
            layAmounts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));



            final TextView tv4 = new TextView(this);
            if (i == -1) {
                tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv4.setPadding(5, 5, 1, 5);
                layAmounts.setBackgroundColor(Color.parseColor("#f7f7f7"));
            } else {
                tv4.setLayoutParams(new TableRow.LayoutParams(400,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv4.setPadding(1, 0, 1, 5);
                layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));

                tv4.setMaxLines(20);
            }

            tv4.setGravity(Gravity.LEFT);

            if (i == -1) {
                tv4.setText("Content");
                tv4.setBackgroundColor(Color.parseColor("#f7f7f7"));
                tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            } else {
                
                tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                tv4.setTextColor(Color.parseColor("#000000"));
                tv4.setText((CharSequence)row.content);
                tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tv4.setLines(4);
                tv4.setMaxLines(20);
                tv4.setSingleLine(false);
            }

            layAmounts.addView(tv4);









//            tr.addView(tv);
            tr.addView(tv2);
            tr.addView(layCustomer);
            tr.addView(layAmounts);

            if (i > -1) {

                tr.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TableRow tr = (TableRow) v;
                        //do whatever action is needed

                    }
                });


            }
            mTableLayout.addView(tr, trParams);

            if (i > -1) {

                // add separator row
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin, rightRowMargin, bottomRowMargin);

                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 4;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);

                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }


        }

    }

    //////////////////////////////////////////////////////////////////////////////

    //
    // The params are dummy and not used
    //
    class LoadDataTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            mProgressBar.hide();
            loadData();
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }




}