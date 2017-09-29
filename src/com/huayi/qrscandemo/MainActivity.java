package com.huayi.qrscandemo;

import com.zbar.lib.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {

	private WebView mWebView = null;
	
	
	private static final int MESSAGE_SETTEXT_TO_HTML = 0x01;
	private static final int MESSAGE_START_SCANQR = 0x02;
	
	
    @SuppressLint("JavascriptInterface") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        mWebView = (WebView)findViewById(R.id.testWebView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new JSHook(), "hello");
        
        mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.d("test", "shouldOverrideUrlLoading()");
				Log.d("test", " url:"+url);
	            view.loadUrl(url);
	                
	            return true;
				//return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
					String url) {
				// TODO Auto-generated method stub
				Log.d("test", "shouldInterceptRequest()");
				return super.shouldInterceptRequest(view, url);
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				// TODO Auto-generated method stub
				Log.d("test", "shouldOverrideKeyEvent()");
				return super.shouldOverrideKeyEvent(view, event);
			}
        	
        });
        
        mWebView.loadUrl("file:///android_asset/test.html");
    }

    private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_SETTEXT_TO_HTML:
				{
					String info = (String)msg.obj;
					mWebView.loadUrl("javascript:show('"+info+"')");
				}
				break;
			case MESSAGE_START_SCANQR:
				{
					startScanQR();
				}
				break;
			}
		}
    	
    };
    
    public class JSHook{
    	
    	@JavascriptInterface
        public void javaMethod(String p){
            Log.d("test" , "JSHook.JavaMethod() called! + "+p);
            if ( "startScanQR".equalsIgnoreCase(p) ) {
            	// 启动二维码扫描
            	mHandler.sendEmptyMessage(MESSAGE_START_SCANQR);
            }
        }
        
        @JavascriptInterface
        public void showAndroid(){
            String info = "来自手机内的内容！！！";
            //mWebView.loadUrl("javascript:show('"+info+"')");
            Message msg = new Message();
            msg.what = MESSAGE_SETTEXT_TO_HTML;
            msg.obj = info;
            mHandler.sendMessage(msg);
        }
        
        @JavascriptInterface
        public String getInfo(){
            return "获取手机内的信息！！";
        }
    }
    
    private void startScanQR() {
    	Intent intent = new Intent();
    	//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.setClass(this, CaptureActivity.class);
    	startActivityForResult(intent, 0x100);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if ( requestCode == 0x100 
    			&& resultCode == 0x100 
    			&& null != data ) {
    		String text = data.getStringExtra("QR_RESULT");
    		Message msg = new Message();
            msg.what = MESSAGE_SETTEXT_TO_HTML;
            msg.obj = text;
            mHandler.sendMessage(msg);
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
}
