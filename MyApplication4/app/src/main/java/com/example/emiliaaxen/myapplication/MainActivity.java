package com.example.emiliaaxen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextMain;
    ListView ListViewMain;
    private ArrayAdapter<String> arrayAdapterMain;
    private ArrayList<String> myDataList;
    private ArrayList<ArrayList<String>> myMasterDataList;
    String bundleMain = "bundleMain";

    private static final int MAIN_REQUEST_CODE = 27;
    // data key to retrieve data from intent. Public so we can retrieve data in DetailActivity
    public static final String DATA_KEY = "myDataKey";
    public static final String DATA_INDEX_KEY = "myDataIndexKey";
    public static final int ERROR_INDEX = -2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextMain = (EditText) findViewById(R.id.editTextMain);
        ListViewMain = (ListView) findViewById(R.id.listViewMain);


        //Creates and ArrayListThe size of the ArrayList instance increases and decreases as necessary based on the number of elements in the data structure
        myDataList = new ArrayList<>();
        myMasterDataList = new ArrayList<>();




        arrayAdapterMain = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myDataList);

        ListViewMain.setAdapter(arrayAdapterMain);

        // OnItemClickListener that takes the user to the detailActivity

        ListViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mainIntent = new Intent(MainActivity.this, DetailActivity.class);


                // Takes the data, using the intent, and takes the user,when clicking on that "item", in the detail activity
                mainIntent.putExtra(DATA_INDEX_KEY, myDataList.get(position));
                mainIntent.putExtra(DATA_KEY, myMasterDataList.get(position));

                startActivityForResult(mainIntent, MAIN_REQUEST_CODE);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInputsLists = editTextMain.getText().toString();

                //if statement that checks if the user has entered a text, and if not, prompts the user, using the snackbar, to enter a name of the list
                if (!userInputsLists.isEmpty()) {
                    myDataList.add(userInputsLists);
                    myMasterDataList.add(new ArrayList<String>());
                    arrayAdapterMain.notifyDataSetChanged();
                    editTextMain.getText().clear();
                } else {
                    Snackbar.make(view, "Add a To-Do List", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });

     ListViewMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override

         //removes the item on the list using remove(position)
         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
             myDataList.remove(position);

             //Notifies the user that the item in the list has been deleted
             Snackbar.make(view, "To-Do List Deleted", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show();
             arrayAdapterMain.notifyDataSetChanged();

             return false;
         }
     });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // make sure returned request code is what we are expecting
        if (requestCode == MAIN_REQUEST_CODE){
            // make sure results were handled correctly
            if (resultCode == RESULT_OK){
                // null pointer check
                if (data != null) {
                    // update data list with the new data
                    ArrayList<String> tempItemList = data.getStringArrayListExtra(DATA_KEY);
                    int index = data.getIntExtra(DATA_INDEX_KEY, ERROR_INDEX);
                    if (index != ERROR_INDEX){
                        myMasterDataList.set(index, tempItemList);
                    } else {
                        Log.e("Main", "Index is not valid: " + index);
                    }

                    // now print our updated data
                    printData(myMasterDataList.get(index));
                }
            } else  if (requestCode == RESULT_CANCELED){
                Log.w("Main", "Failed to get new list back");
            }
        }
    }

    private void printData(ArrayList<String> data){
        if (data == null){
            return;
        }
        for (String item : data){
            Log.d("Main", item);
        }

/*    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
    }
}