package com.example.emarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

public abstract class DashBoardActivity extends ListActivity {
	// Constant for identifying the dialog
	private static final int DIALOG_ALERT = 10;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
     
    public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible) {
  
          
    	Dialog inflated = null;
		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
    	txtTitle.setText(title);
    		
    	Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
    	if(!btnHomeVisible)
    		btnHome.setVisibility(View.INVISIBLE);
    		
    	Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
    	if(!btnFeedbackVisible)
    		btnFeedback.setVisibility(View.INVISIBLE);
    }
    
    /**
     * Home button click handler
     * @param v
     */
    public void btnHomeClick(View v) {
    	Intent intent = null;
    	intent = new Intent(getApplicationContext(), MainActivity.class);
    	intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);	
    }
    
    /**
     * Logout button click handler
     * @param v
     */
    public void btnFeedbackClick(View v) {
    	showDialog(DIALOG_ALERT);
    }
    
        
    // TODO Wrapper method to encapsulate getApplicationContext() that is needed for displaying Toast message
	protected void showToast(String text) {
		Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
	}
}


