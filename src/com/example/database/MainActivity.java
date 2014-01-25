
package com.example.database;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	EditText t;
    private SimpleCursorAdapter dataAdapter;
    private DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //Testing Commit
		
		  db = new DatabaseHandler(this);
         
	        /**
	         * CRUD Operations
	         * */
	        // Inserting Contacts
	       
	        db.addContact(new Contact("Ravi", "9100000000","asas"));
	        db.addContact(new Contact("Srinivas", "9199999999","aavsga"));
	        db.addContact(new Contact("Tommy", "9522222222","acvbazvc"));
	        db.addContact(new Contact("Karthik", "9533333333","asjsa"));
        displayListView();

	            //t.setText(cn.getName()+ cn.getPhoneNumber());
	        	//Log.e("Check", cn.getName()+ cn.getPhoneNumber());


	        }
    private void displayListView()
    {
        Cursor cursor=db.fetchAllContacts();
        // The desired columns to be bound
        String[] columns = new String[] {
                DatabaseHandler.KEY_NAME
                       };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.name
              };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(this, R.layout.name,
            cursor,
            columns,
            to,0);


        ListView listView = (ListView) findViewById(R.id.list1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);



        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                createNotification(view,cursor);
                }

        });


    }

    public void createNotification(View view,Cursor cursor) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
          String text;
        text="Name: "+cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME))
                +"\nPhone No: "+cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_PH_NO))
                +"\nEmail ID:"+cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_EMAIL_ID));
        // Build notification
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Personal Details");
        builder.setContentText(text);
        builder.setContentIntent(pIntent);
        Notification noti = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }


	        //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	       // public void addItems(View v) {
	         //   listItems.add("Clicked : "+clickCounter++);
	      //      adapter.notifyDataSetChanged();
	       // }

	}
