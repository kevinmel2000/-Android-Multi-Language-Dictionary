package com.unsri.kamus;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.app.ListActivity;
import android.widget.ListView;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{
	private TextView selection;
	private TextView txtOutput;
	private ListView lvOutput;
	private EditText txtInput;
	private DBAdapter db; 
	private Cursor kamusCursor=null;
	private ArrayAdapter<String> adapter;
	
	public static final String INDONESIA="indonesia";
	public static final String INGGRIS="inggris";
	public static final String INDIA="india";
	public static final String ITALIA="italia";
	public static final String JERMAN="jerman";
	public static final String PERANCIS="perancis";
	public static final String SPANYOL="spanyol";
	
	String[] items={"Indonesia","Inggris","India","Italia","Jerman","Perancis","Spanyol"};
	String[] result={"","","","","","",""};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db=new DBAdapter(this);
		db.createDatabase();
		db.open();
		
		selection=(TextView)findViewById(R.id.selection);
		txtInput=(EditText)findViewById(R.id.txtInput);
		//txtOutput=(TextView)findViewById(R.id.output);
		Spinner spin=(Spinner)findViewById(R.id.spinner);
		lvOutput=(ListView)findViewById(R.id.output);
		
		spin.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,result);
		lvOutput.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void onItemSelected(AdapterView<?> parent,View v,int position,long id){
		selection.setText(items[position]);
	}
	public void onNothingSelected(AdapterView<?> parent){
		selection.setText("");
	}
	
	public void getTerjemahan(View view){
		//String[] result=new String[7];
		String input=txtInput.getText().toString();
		//String[] last_result=new String[7];
		try{
			db.open();
			Cursor res=db.getAlldata(selection.getText().toString().toUpperCase(), input.toLowerCase());
			if(res.moveToFirst()){
				for(int i=0;i<items.length;i++){
					//if(input.toLowerCase().length()!=res.getString(i+1).length()){
						result[i]=items[i]+"\n"+res.getString(i+1);
					//}
				}
				
			}	
		}
		catch(SQLException sqle){
			Toast.makeText(this,sqle.getMessage() , Toast.LENGTH_LONG).show();
		}
		if(result.equals("")){
			result[0]="Terjemahan tidak ditemukan";
			for(int i=1;i<items.length;i++){
				result[i]="";
			}
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,result);
			lvOutput.setAdapter(adapter);
			//txtOutput.setText("Terjemahan Tidak Ditemukan");
		}
		else{
			//txtOutput.setText(result);
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,result);
			//lvOutput.setAdapter(adapter);
			lvOutput.setAdapter(adapter);
		}
		
	}

}
