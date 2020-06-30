package com.doctorsteep.elementinspector.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.doctorsteep.elementinspector.R;

import java.util.Locale;

public class WebEI extends WebView {

    public String URL_HOME = "https://google.com";
    public String URL_SEARCH = "https://google.com/search?q=";

    private boolean DESKTOP_VERSION = false;

    public String USERAGENT_MOBILE = "Mozilla/5.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; " + Locale.getDefault().getLanguage() + "; " + Build.MODEL + " Build/" + Build.DISPLAY + ") AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.110 Mobile Safari/537.36";
    public String USERAGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

    public WebEI(Context context) {
        super(context);
        init();
    }
    public WebEI(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setUserAgentString(USERAGENT_MOBILE);
        this.getSettings().setSavePassword(true);
        this.getSettings().setSaveFormData(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setDomStorageEnabled(true);
        this.setWebContentsDebuggingEnabled(shared.getBoolean("keyDebugMode", false));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        CookieManager.getInstance().setAcceptCookie(true);
    }

    public void runWeb(final String text) {
        if (text.startsWith("https://") || text.startsWith("http://") || text.startsWith("file://") || text.startsWith("javascript:") || text.startsWith("www.") || text.startsWith("content://") || text.startsWith("view-source:")) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(text);
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(URL_SEARCH + text);
                }
            });
        }
    }
    public void runHome() {
        this.post(new Runnable() {
            @Override
            public void run() {
                loadUrl(URL_HOME);
            }
        });
    }
    public void runJavascript(EditText view) {
        String rAll = view.getText().toString().replaceAll(";$", "");
        String js = "javascript:%s%s%s";
        Object[] oArr = new Object[3];

        oArr[0] = rAll.startsWith("console.") ? "" : "console.log(";
        oArr[1] = rAll;
        oArr[2] = rAll.startsWith("console.") ? "" : ");";
        runWeb(String.format(js, oArr));
        view.setText("");
    }
    public void runJS(final EditText view) {
        if (view.getText().toString().startsWith("javascript:")) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(view.getText().toString());
                }
            });
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    loadUrl("javascript:" + view.getText().toString());
                }
            });
        }

        view.post(new Runnable() {
            @Override
            public void run() {
                view.setText("");
            }
        });
    }
    public void runJS(final String source) {
        if (source.startsWith("javascript:")) {
            loadUrl(source);
        } else {
            loadUrl("javascript:" + source);
        }
    }

    public void checkSSL(ImageView view, String url) {
        if (url.startsWith("https://")) {
            view.setImageResource(R.drawable.ic_ssl_ok);
        } else {
            view.setImageResource(R.drawable.ic_ssl_error);
        }
    }

    public void setDesktopVersion(boolean mode) {
        DESKTOP_VERSION = mode;
        if (mode) {
            this.getSettings().setUserAgentString(USERAGENT_PC);
        } else {
            this.getSettings().setUserAgentString(USERAGENT_MOBILE);
        }
        this.loadUrl(this.getUrl());
    }
    public boolean isDesktopVersion() {
        return DESKTOP_VERSION;
    }
}
