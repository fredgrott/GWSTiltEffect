package com.github.shareme.gwstilteffect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.github.shareme.gwstilteffect.library.TiltEffectAttacher;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TiltEffectAttacher.attach(findViewById(R.id.button));
		TiltEffectAttacher.attach(findViewById(R.id.imageView));
		WebView webView = (WebView) findViewById(R.id.webView);
		TiltEffectAttacher.attach(webView);
		webView.loadUrl("http://www.google.fr");
	}

}
