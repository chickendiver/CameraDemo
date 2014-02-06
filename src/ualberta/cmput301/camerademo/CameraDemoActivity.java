package ualberta.cmput301.camerademo;

import java.io.File;

import ualberta.cmput301.camerodemo.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CameraDemoActivity extends Activity {

	private TextView textView;
	private ImageButton imageButton;
	private Uri imageFileUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camero_demo);
		
		// Retrieve handlers
		textView = (TextView) findViewById(R.id.status);
		imageButton = (ImageButton) findViewById(R.id.image);
		
		// Set up the listener
		OnClickListener listener = new OnClickListener() {
			public void onClick(View view) {
				takeAPhoto(); // implement this method
			}
		};
		// Register a callback to be invoked when this view is clicked
		imageButton.setOnClickListener(listener);
	}


	// Implement takeAPhoto() method to allow you to take a photo when you click the ImageButton.
	// Notice that startActivity() method will not return any result when the launched activity 
	// finishes, while startActivityForResult() method will. To retrieve the returned result, you may 
	// need implement onAcitityResult() method.
	public void takeAPhoto() {
		
		private String TAG = "Main"; 
		//create file path
        File imageDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mnt/sdcard/temp/bm.jpg");
        boolean success = imageDirectory.mkdirs();
        if (success){
        	 Log.d(TAG,"Folder created.");
        }
        else{
        	Log.d(TAG,"Folder not created");
        }
        

        //create file uri
        final Uri fileUri = Uri.parse(imageDirectory.getAbsolutePath());

        //create camera intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //put file Uri to intent - this will tell camera where to save file with image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        
        // start activity
        startActivityForResult(intent, 0);

        //start image scanned to add photo to gallery
        //addProductPhotoToGallery(fileUri);
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if (resultCode == RESULT_OK){
				
				//Backup bitmap storage
				Bitmap bm = (Bitmap) data.getExtras().getParcelable("data");
				
				
				Uri imageUri = data.getData();
				try{	
					bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				imageButton.setImageBitmap(Bitmap.createScaledBitmap(bm, imageButton.getWidth(), imageButton.getHeight(), false));
				
				
				//imageButton.setImageBitmap(bm);
				textView.setText("Photo OK");
			}
			else if (resultCode == RESULT_CANCELED){
				textView.setText("Photo Cancelled");
			}
			else {
				textView.setText("Not sure what happened");
			}
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camero_demo, menu);
		return true;
	}

}
