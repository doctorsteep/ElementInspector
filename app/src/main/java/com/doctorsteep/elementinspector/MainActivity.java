package com.doctorsteep.elementinspector;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctorsteep.elementinspector.app.EditSearchManager;
import com.doctorsteep.elementinspector.app.JSManager;
import com.doctorsteep.elementinspector.model.BackHistoryModel;
import com.doctorsteep.elementinspector.model.ConsoleModel;
import com.doctorsteep.elementinspector.web.BackHistory;
import com.doctorsteep.elementinspector.web.WebEIConsole;
import com.doctorsteep.elementinspector.widget.WebEI;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.AppBarLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private WebEI mWeb;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private EditText mEditSearch;
    private ProgressBar mProgress;
    private ImageView imageSsl;
    private TextView textUrl;
    private LinearLayout linBottomMenu;

    private ImageView actionHome, actionMore;
    private ImageView menuBack, menuForward, menuRefresh;

    private PopupMenu popupMore;

    public static ArrayList<ConsoleModel> consoleList = new ArrayList<>();
    public static ArrayList<BackHistoryModel> backHistoryList = new ArrayList<>();

    private int COUNTER_ADS_DEFAULT = 3;
    private int COUNTER_ADS = 3;

    private InterstitialAd mAd;
    private SharedPreferences shared;
    private SharedPreferences sharedADS;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    // ПОИСК ПО СТРАНИЦЕ
    private LinearLayout linMainIncludeFindToPage;
    private EditText editFind;
    private ImageView imageFindBack, imageFindNext, imageFindClose;
    private TextView textFind;

    private TextView textTabs;
    private RelativeLayout actionTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Id's
        mWeb = findViewById(R.id.web);
        toolbar = findViewById(R.id.toolbar);
        appbar = findViewById(R.id.appbar);
        mEditSearch = findViewById(R.id.edit_search);
        mProgress = findViewById(R.id.progressBar);
        imageSsl = findViewById(R.id.imageSsl);
        textUrl = findViewById(R.id.textUrl);
        linBottomMenu = findViewById(R.id.linBottomMenu);

        actionHome = findViewById(R.id.actionHome);
        actionMore = findViewById(R.id.actionMore);

        menuBack = findViewById(R.id.menuBack);
        menuForward = findViewById(R.id.menuForward);
        menuRefresh = findViewById(R.id.menuRefresh);

        textTabs = findViewById(R.id.textTabs);
        actionTabs = findViewById(R.id.actionTabs);

        // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------
        linMainIncludeFindToPage = findViewById(R.id.mainIncludeFindToPage);
        editFind = findViewById(R.id.editFind);
        imageFindBack = findViewById(R.id.actionFindBack);
        imageFindNext = findViewById(R.id.actionFindNext);
        imageFindClose = findViewById(R.id.actionFindClose);
        textFind = findViewById(R.id.textFind);
        // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------

        shared = PreferenceManager.getDefaultSharedPreferences(this);
        sharedADS = getSharedPreferences("ads", Context.MODE_PRIVATE);

        if (sharedADS.getBoolean("ads", true) == false) {

        } else {
            MobileAds.initialize(this, "ca-app-pub-2543059856849154~8614667321");
            mAd = new InterstitialAd(this);
            mAd.setAdUnitId("ca-app-pub-2543059856849154/2773870051");
            mAd.loadAd(new AdRequest.Builder().addTestDevice("C28C687B0AF29F93B8AC8D5C9C195D71").build());
            mAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mAd.loadAd(new AdRequest.Builder().addTestDevice("C28C687B0AF29F93B8AC8D5C9C195D71").build());
                    }
            });
        }

        closeFindToPage();

        popupMore = new PopupMenu(MainActivity.this, actionMore);
        popupMore.inflate(R.menu.main_more);
        popupMore.setGravity(Gravity.END);
        popupMore.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.actionConsole) {
                    new WebEIConsole(MainActivity.this, mWeb, consoleList);
                }
                if (item.getItemId() == R.id.actionContenteditable) {
                    mWeb.runJS(JSManager.CONTENTEDITABLE);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionHighlightBorder) {
                    mWeb.runJS(JSManager.HIGHLIGHT_BORDER);
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                }
                if (item.getItemId() == R.id.actionSourcePage) {
                    mWeb.runJS(JSManager.SOURCE);
                }
                if (item.getItemId() == R.id.actionDesktopVersion) {
                    if (item.isChecked()) {
                        item.setChecked(false);
                        mWeb.setDesktopVersion(false);
                    } else {
                        item.setChecked(true);
                        mWeb.setDesktopVersion(true);
                    }
                }
                if (item.getItemId() == R.id.actionSettings) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
                if (item.getItemId() == R.id.actionFeedback) {
                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                }
                if (item.getItemId() == R.id.actionHelp) {
                    startActivity(new Intent(MainActivity.this, HelpActivity.class));
                }
                if (item.getItemId() == R.id.actionExit) {
                    finish();
                }
                if (item.getItemId() == R.id.actionFindToPage) {
                    if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
                        openFindToPage();
                    } else {
                        closeFindToPage();
                    }
                }
                if (item.getItemId() == R.id.actionHistory) {
                    new BackHistory(MainActivity.this, mWeb, backHistoryList);
                }
                return true;
            }
        });




        // Content
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        if (mWeb != null) {
            try {
                Uri url = getIntent().getData();
                if (url != null) {
                    mWeb.loadUrl(url.toString());
                } else {
                    mWeb.runHome();
                }
            } catch (Exception e) {
                mWeb.runHome();
            }
            mWeb.setWebViewClient(new WebClient());
            mWeb.setWebChromeClient(new WebChrome());

            mWeb.setFindListener(new WebView.FindListener() {
                @Override
                public void onFindResultReceived(final int i, final int i1, final boolean b) {
                    if (i1 != 0) {
                        textFind.post(new Runnable() {
                            @Override
                            public void run() {
                                textFind.setText(i + 1 + " / " + i1);
                            }
                        });
                    } else {
                        textFind.post(new Runnable() {
                            @Override
                            public void run() {
                                textFind.setText("0 / 0");
                            }
                        });
                    }
                }
            });
        }
        if (mProgress != null) {
            hideProgress();
            mProgress.setMax(100);
        }
        if (mEditSearch != null) {
            mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handler = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        mWeb.runWeb(mEditSearch.getText().toString());
                        EditSearchManager.hideKeyboard(MainActivity.this);
                        handler = true;
                    }
                    return handler;
                }
            });
            mEditSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasfocus) {
                    if (hasfocus) {
                        editSearchChange(mWeb.getUrl());
                    } else {
                        editSearchChange(mWeb.getTitle());
                    }
                }
            });
        }
        if (textUrl != null) {
            textUrl.post(new Runnable() {
                @Override
                public void run() {
                    textUrl.setText(getString(R.string.loading_page));
                }
            });
        }
        if (editFind != null) {
            editFind.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && ((keyCode == KeyEvent.KEYCODE_ENTER))) {
                        searchFindToPage();
                    }
                    return false;
                }
            });
        }

        if (menuBack != null) {
            TooltipCompat.setTooltipText(menuBack, getString(R.string.tooltip_back_page));
        }
        if (menuForward != null) {
            TooltipCompat.setTooltipText(menuForward, getString(R.string.tooltip_forward_page));
        }
        if (menuRefresh != null) {
            TooltipCompat.setTooltipText(menuRefresh, getString(R.string.tooltip_refresh_page));
        }

        if (imageFindBack != null) {
            TooltipCompat.setTooltipText(imageFindBack, getString(R.string.tooltip_back_find));
        }
        if (imageFindNext != null) {
            TooltipCompat.setTooltipText(imageFindNext, getString(R.string.tooltip_next_find));
        }



        // Content other
        actionHome.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mWeb.runHome();
                }
            });
        actionMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (popupMore != null) {
                            popupMore.show();
                        }
                    }
                });
            }
        });
        actionTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabsActivity.class));
            }
        });






        // Meu bottom
        menuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeb.canGoBack()) {
                    mWeb.goBack();
                }
            }
        });
        menuForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeb.canGoForward()) {
                    mWeb.goForward();
                }
            }
        });
        menuRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeb.reload();
            }
        });



        imageFindBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.findNext(false);
                    }
                });
            }
        });
        imageFindNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.findNext(true);
                    }
                });
            }
        });
        imageFindClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFindToPage();
                EditSearchManager.hideKeyboard(MainActivity.this);
            }
        });



        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 5);
        } else {

        }
    }

    @Override
    public void onBackPressed() {
        if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
            if (mWeb.canGoBack()) {
                mWeb.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeb.goBack();
                    }
                });
            } else {
                super.onBackPressed();
            }
        } else {
            closeFindToPage();
        }
    }

    @Override
    protected void onResume() {
        try {
            setLoadSettings();
        } catch (Exception e) {}
        super.onResume();
    }

    // On key's
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: // On long click back button
                new BackHistory(MainActivity.this, mWeb, backHistoryList);
                return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU: // Open popup menu
                popupMore.show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------
    private void openFindToPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (linMainIncludeFindToPage.getVisibility() == View.GONE) {
                    linMainIncludeFindToPage.setVisibility(View.VISIBLE);
                    appbar.setVisibility(View.GONE);
                    linBottomMenu.setVisibility(View.INVISIBLE);
                    textUrl.setVisibility(View.GONE);
                }
            }
        });
    }
    private void closeFindToPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (linMainIncludeFindToPage.getVisibility() == View.VISIBLE) {
                    linMainIncludeFindToPage.setVisibility(View.GONE);
                    appbar.setVisibility(View.VISIBLE);
                    if (shared.getBoolean("keyViewBottomMenuBar", true) == true) {
                        linBottomMenu.setVisibility(View.VISIBLE);
                    } else {
                        linBottomMenu.setVisibility(View.GONE);
                    }
                    textUrl.setVisibility(View.VISIBLE);
                    editFind.post(new Runnable() {
                        @Override
                        public void run() {
                            editFind.setText("");
                            searchFindToPage();
                        }
                    });
                }
            }
        });
    }
    private void searchFindToPage() {
        mWeb.post(new Runnable() {
            @Override
            public void run() {
                mWeb.findAll(editFind.getText().toString());
            }
        });
        try {
            Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
            m.invoke(mWeb, true);
        } catch (Exception e) {}
    }
    // ПОИСК ПО СТРАНИЦЕ-------------------------------------------------------------------------------------



    // WebView client's
    private class WebClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            changeTextUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            changeTextUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            if (!isReload) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backHistoryList.add(new BackHistoryModel(mWeb.getTitle(), mWeb.getUrl(), mWeb.getFavicon(), String.valueOf(backHistoryList.size() + 1)));
                        }
                }, 2000);
            }
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
            editSearchChange(view.getTitle());
            changeTextUrl(url);
            mWeb.checkSSL(imageSsl, url);
            showADS();
            try {
                if (popupMore != null) {
                    popupMore.getMenu().findItem(R.id.actionContenteditable).setChecked(false);
                    popupMore.getMenu().findItem(R.id.actionHighlightBorder).setChecked(false);
                }
            } catch (Exception e) {}
            if (consoleList.size() > 0) {
                consoleList.clear();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgress();
            editSearchChange(view.getTitle());
            changeTextUrl(url);
            mWeb.checkSSL(imageSsl, url);
        }
    }
    private class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, final int newProgress)  {
            super.onProgressChanged(view, newProgress);
            mProgress.post(new Runnable() {
                @Override
                public void run() {
                    mProgress.setProgress(newProgress);
                }
            });
            if (newProgress > 99) {
                changeTextUrl(view.getUrl().toString());
            } else {
                changeTextUrl(getString(R.string.loading_page) + " " + newProgress + " %");
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            try {
                setTitle(title);
            } catch (Exception e) {}
            editSearchChange(title);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String typeConsole = "";
            switch (consoleMessage.messageLevel()) {
                case TIP:
                    typeConsole = "tip";
                    break;
                case LOG:
                    typeConsole = "log";
                    break;
                case WARNING:
                    typeConsole = "warning";
                    break;
                case ERROR:
                    typeConsole = "error";
                    break;
                case DEBUG:
                    typeConsole = "debug";
                    break;
            }
            consoleList.add(new ConsoleModel(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId(), new Long(System.currentTimeMillis()/1000).toString(), typeConsole));
            return true;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 5);
            } else {
                AlertDialog alertGeolocation = new AlertDialog.Builder(MainActivity.this).create();
                alertGeolocation.setTitle(getString(R.string.title_geolocation));
                alertGeolocation.setMessage(String.format(getString(R.string.message_geolocation), origin));
                alertGeolocation.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, true, false);
                    }
                });
                alertGeolocation.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, false, false);
                    }
                });
                alertGeolocation.show();
            }
        }
    }




    // Progress
    private void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        });
    }
    private void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    // Edit search
    private void editSearchChange(final String text) {
        mEditSearch.post(new Runnable() {
            @Override
            public void run() {
                mEditSearch.setText(text);
            }
        });
    }

    // Text url
    private void changeTextUrl(final String text) {
        textUrl.post(new Runnable() {
            @Override
            public void run() {
                textUrl.setText(text);
            }
        });
    }

    // App ads
    private void showADS() {
        if (sharedADS.getBoolean("ads", true) == false) {

        } else {
            if (COUNTER_ADS == 0) {
                COUNTER_ADS = COUNTER_ADS_DEFAULT;
                // Show ads
                if (mAd.isLoaded()) {
                    mAd.show();
                }
            } else {
                COUNTER_ADS = COUNTER_ADS - 1;
            }
        }
    }

    // App shared settings
    private void setLoadSettings() throws Exception {
        if (shared.getBoolean("keyLinkText", true) == false) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textUrl.setVisibility(View.GONE);
                }
            });
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textUrl.setVisibility(View.VISIBLE);
                }
            });
        }


        if (shared.getBoolean("keyViewBottomMenuBar", true) == true) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linBottomMenu.setVisibility(View.VISIBLE);
                }
            });
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    linBottomMenu.setVisibility(View.GONE);
                }
            });
        }


        if (shared.getBoolean("keyRoundedDisplay", false) == true && shared.getBoolean("keyViewBottomMenuBar", true) == false) {
            textUrl.setPadding(50, 3, 3, 7);
        } else {
            textUrl.setPadding(7, 3, 3, 7);
        }
    }
}
