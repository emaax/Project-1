package com.example.emiliaaxen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    EditText editTextDetail;
    ListView listViewDetail;
    TextView textViewDetail;
    private ArrayList<String> arrayListDetail; //the data source
    private int index;
    ArrayAdapter<String> arrayAdapterDetail;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editTextUserInput = editTextDetail.getText().toString();
                if (!editTextUserInput.isEmpty()) {
                //adds user input to the arrayList
                arrayListDetail.add(editTextUserInput);
                // Notifies the ArrayAdapter that the user entered text
                arrayAdapterDetail.notifyDataSetChanged();
                /** clears the current Edittext (eg after the user entered text,
                 * this will clear this text from the Edittex, so that the user can enter new text)**/
                editTextDetail.getText().clear();
                    //
                } else {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });


        textViewDetail = (TextView) findViewById(R.id.text_view_detail);
        editTextDetail = (EditText) findViewById(R.id.edit_text_detail);
        listViewDetail = (ListView) findViewById(R.id.list_view_detail); //listView = visual representation of the data
        Intent mainIntent = getIntent();
        arrayListDetail = mainIntent.getStringArrayListExtra(MainActivity.DATA_KEY);

       assert arrayListDetail != null;
        arrayAdapterDetail = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListDetail);
        // should it be android.R.layout.simple_list_item_1?

        listViewDetail.setAdapter(arrayAdapterDetail);


        // takes the "item" name from the masterArrayList, for example groceries,and "stores" it in a new String,
        String toDoItem = (String) getIntent().getStringExtra(MainActivity.DATA_INDEX_KEY);


        /**sets the text of the declared TextViev in detail ( setting the text by using a  reference to the text defined in the strings.xml)
         In other words, what I'm doing is that I'm taking the toDOItem text(for ex groceries) and sets the text(groceries) to be displayed in the textView "field"
         of the detail activity **/

        textViewDetail.setText(toDoItem.toUpperCase());


        listViewDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                arrayListDetail = getDataList();

                index = getDataIndex();

                printList();

                // removes the "To-do" item from the arrayList
                arrayListDetail.remove(position);

                //notifies the arrayAdapter that item has been deleted
                arrayAdapterDetail.notifyDataSetChanged();
                Snackbar.make(view,
                        "To-Do item deleted",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return false;
            }
        });


    }


            private ArrayList<String> getDataList() {
                Intent arrayListDetail = getIntent();
                if (arrayListDetail == null) {
                    return null;
                }
                return arrayListDetail.getStringArrayListExtra(MainActivity.DATA_KEY);
            }


            private int getDataIndex() {
                Intent intentDetail = getIntent();
                if (intentDetail == null) {
                    return MainActivity.ERROR_INDEX;
                }
                return intentDetail.getIntExtra(MainActivity.DATA_INDEX_KEY, MainActivity.ERROR_INDEX);
            }


            private void printList() {
                if (arrayListDetail == null) {
                    return;
                }
                for (String item : arrayListDetail) {
                    Log.d("Detail", item);
                }
            }



            private void sendNewListBack() {
                Intent newDetailIntent = getIntent();
                if (newDetailIntent == null) {
                    return;
                }
                newDetailIntent.putExtra(MainActivity.DATA_KEY, arrayListDetail);
                newDetailIntent.putExtra(MainActivity.DATA_INDEX_KEY, index);
                setResult(RESULT_OK, newDetailIntent);
                finish();
            }

            public void onBackPressed() {
                sendDataBack();
            }


            private void sendDataBack() {
                // send the data back
                sendNewListBack();
            }

        }

