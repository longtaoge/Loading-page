package com.pgeeKer.loading_page;

import com.example.loading_page.R;
import com.pgeeKer.loading_page.view.CustomAnimation;
import com.pgeeKer.loading_page.view.CustomTextView;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		CustomTextView textView = (CustomTextView) findViewById(R.id.my_text_view);
		textView.setTypeface(TypeFaces.getTypeface(this, "Satisfy-Regular.ttf"));
		
		new CustomAnimation().start(textView);
	}

}
