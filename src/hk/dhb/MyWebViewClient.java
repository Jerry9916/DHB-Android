package hk.dhb;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
	
	private String homeUrl = null;
	private Toast toast = null;
	
	public MyWebViewClient(String homeUrl,Toast toast) {
		super();
		this.homeUrl = homeUrl;
		this.toast = toast;
	}

	/**
	 * 重写此方法防止页面在新浏览器中打开
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(!url.toString().startsWith(homeUrl)){
			url = homeUrl;
		}
		view.loadUrl(url);
		return true;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		toast.show();
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		toast.cancel();
		view.setVisibility(WebView.VISIBLE);
	}
	
	
	
}
