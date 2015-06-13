package com.jiedaoche.tigeroil.ui.activitys;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.library.IUtils.Dialog.IDialogFactory;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @ClassName: CommonWebViewActivity
 * @Description: 公共WebView
 * @author Aaron
 * @date 2015-5-21 下午10:49:49
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
@ContentView(R.layout.activity_webview)
public class CommonWebViewActivity extends BaseActivity {

	@ViewInject(R.id.webview)
	private WebView mWebView;

	private Dialog dialog;

	private String url;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle(getIntent().getExtras().getString("title"));
		url = getIntent().getExtras().getString("url");
		initWebView();
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		if (mWebView.canGoBack())
			mWebView.goBack();
		this.finish();
	}

	private void initWebView() {
		if (IStringUtils.isEmpty(url)) {
			return;
		}

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		mWebView.requestFocus();

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

		});

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				view.getSettings().setJavaScriptEnabled(true);
				dialog = IDialogFactory.createLoadingDialog(
						CommonWebViewActivity.this, "加载中");
				dialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				IDialogFactory.dimissDialog(dialog);
				view.getSettings().setJavaScriptEnabled(true);
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
				// DialogFactory.dismissDialog(dialog);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				IDialogFactory.dimissDialog(dialog);
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				super.onReceivedSslError(view, handler, error);
				// handler.cancel(); 默认的处理方式，WebView变成空白页
				handler.proceed();// 接受证书
				// handleMessage(Message msg); 其他处理
			}
		});

		mWebView.loadUrl(url);
	}
}
