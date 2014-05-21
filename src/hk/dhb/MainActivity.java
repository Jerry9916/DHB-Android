package hk.dhb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private WebView webView;//浏览器对象
	private String homeUrl = null;
	private Toast toast = null;
	public final int MENU_BACK = 0;
	public final int MENU_FORWARD = 1;
	public final int MENU_REFRESH = 2;
	public final int MENU_ABOUT = 3;
	public final int MENU_EXIT = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);//设置布局
		if(ifConnect()){
			webView = (WebView) findViewById(R.id.webView);//获取浏览器对象
			webView.getSettings().setJavaScriptEnabled(true);//允许使用javascript
			toast = Toast.makeText(getApplicationContext(), R.string.loading,toast.LENGTH_LONG);
			homeUrl = this.getString(R.string.load_url);
			webView.setWebViewClient(new MyWebViewClient(homeUrl,toast));
			webView.setWebChromeClient(new MyWebChromeClient());
			webView.loadUrl(homeUrl);
		}else{
			showConnectError();
		}
	}
	
	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, MENU_BACK, 1, R.string.menu_back);
		menu.add(1, MENU_FORWARD, 2, R.string.menu_forward);
		menu.add(1, MENU_REFRESH, 3, R.string.menu_refresh);
		menu.add(1, MENU_ABOUT, 4, R.string.menu_about);
		menu.add(1, MENU_EXIT, 5, R.string.menu_exit);
		/*if(webView.canGoBack()){
			menu.getItem(MENU_BACK).setEnabled(true);
		}else{
			menu.getItem(MENU_BACK).setEnabled(false);
		}
		if(webView.canGoForward()){
			menu.getItem(MENU_FORWARD).setEnabled(true);
		}else{
			menu.getItem(MENU_FORWARD).setEnabled(false);
		}*/
		return true;
	}
	
	/**
	 * 菜单点击响应
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		switch(id){
			case MENU_BACK:
				goBack();
				break;
			case MENU_FORWARD:
				if(webView.canGoForward()){
					webView.goForward();
				}
				break;
			case MENU_REFRESH:
				webView.reload();
				break;
			case MENU_ABOUT:
				showAbout();
				break;
			case MENU_EXIT:
				if_exit();
				break;
		}
		return true;
	}
	
	/**
	 * 回退操作
	 */
	public void goBack(){
		if(webView.canGoBack()){
			webView.goBack();
		}else{
			if_exit();
		}
	}
	
	/**
	 * 回退事件响应
	 */
	@Override
	public void onBackPressed() {
		goBack();
	}
	
	/**
	 * 是否退出
	 */
	public void if_exit(){
		new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name)
		   .setIcon(R.drawable.app_ico)
		   .setMessage(R.string.if_exit)
		   .setPositiveButton(R.string.submit_button,new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				appExit();
			}
		}).setNegativeButton(R.string.cancel_button,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}
	
	public void appExit(){
		SysApplication.getInstance().exit();
	}
	
	/**
	 * 关于信息
	 */
	public void showAbout(){
		new AlertDialog.Builder(MainActivity.this).setTitle(R.string.about_title).setIcon(R.drawable.app_ico).setMessage(R.string.about_content).setPositiveButton(R.string.close_button, null).show();
	}
	
	/**
	 * 检查网络是否连接
	 * @return boolean
	 */
	public boolean ifConnect(){
		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if(wifi||internet){
			return true;
		}else{
			return false;
			
		}
	}
	
	/**
	 * 显示网络连接错误
	 */
	public void showConnectError(){
		new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name)
		   .setIcon(R.drawable.app_ico)
		   .setMessage(R.string.no_connect)
		   .setPositiveButton(R.string.close_button,new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				appExit();
			}
		}).show();
	}
}
