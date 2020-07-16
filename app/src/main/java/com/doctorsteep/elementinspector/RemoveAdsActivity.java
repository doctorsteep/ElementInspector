package com.doctorsteep.elementinspector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoveAdsActivity extends AppCompatActivity {

    private Button btnBuySub;
    private TextView textRestorePause;

    private BillingClient billingClient;
    private Map<String, SkuDetails> skuDetailsMap = new HashMap<>();
    private String PURCHASE_ID = "sub_remove_ads";
    private SharedPreferences sharedADS;

    private boolean PAUSE_SUB = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_ads);
        btnBuySub = findViewById(R.id.btnBuySub);
        textRestorePause = findViewById(R.id.textRestorePause);

        sharedADS = getSharedPreferences("ads", Context.MODE_PRIVATE);

        btnBuySub.post(new Runnable() {
            @Override
            public void run() {
                btnBuySub.setEnabled(false);
                btnBuySub.setText(String.format(getString(R.string.action_remove_ads_btn), "0.00$"));
            }
        });

        textRestorePause.post(new Runnable() {
            @Override
            public void run() {
                textRestorePause.setText(getString(R.string.action_restore_subscribe));
            }
        });

        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                    // Purchases...
                    payComplete();
                }
            }
        }).build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull final BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    //Load info...
                    querySkuDetails();
                    List<Purchase> purchaseList = queryPurchase();

                    for (int i = 0; i < purchaseList.size(); i++) {
                        final String purchaseID = purchaseList.get(i).getSku();
                        if (TextUtils.equals(PURCHASE_ID, purchaseID)) {
                            payComplete();
                        }
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Snackbar.make(findViewById(R.id.content), getString(R.string.toast_billing_disconnect), Snackbar.LENGTH_LONG).show();
            }
        });


        btnBuySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try { launchBilling(PURCHASE_ID); } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.content), getString(R.string.toast_billing_error), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        textRestorePause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PAUSE_SUB) {

                } else {
                    try { restoreBilling(PURCHASE_ID); } catch (Exception e) {
                        Snackbar.make(findViewById(R.id.content), getString(R.string.toast_billing_error), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void querySkuDetails() {
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        List<String> skuList = new ArrayList<>();
        skuList.add(PURCHASE_ID);
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                if (billingResult.getResponseCode() == 0) {
                    for (final SkuDetails skuDetails : list) {
                        skuDetailsMap.put(skuDetails.getSku(), skuDetails);

                        if (skuDetails.getSku().equals(PURCHASE_ID)) {
                            btnBuySub.post(new Runnable() {
                                @Override
                                public void run() {
                                    btnBuySub.setEnabled(true);
                                    try {
                                        btnBuySub.setText(String.format(getString(R.string.action_remove_ads_btn), (String)skuDetailsMap.get(skuDetails.getSku()).getPrice()));
                                    } catch (Exception e) {
                                        btnBuySub.setText(String.format(getString(R.string.action_remove_ads_btn), "0.00$"));
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void launchBilling(String ID) throws Exception {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetailsMap.get(ID))
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }
    private void restoreBilling(String ID) throws Exception {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setOldSku(PURCHASE_ID, getApplicationContext().getPackageName())
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }

    private void payComplete() {
        // Purchase success!
        btnBuySub.post(new Runnable() {
            @Override
            public void run() {
                btnBuySub.setEnabled(false);
                btnBuySub.setText(getString(R.string.toast_billing_success));
            }
        });
        textRestorePause.post(new Runnable() {
            @Override
            public void run() {
                textRestorePause.setText(getString(R.string.action_pause_subscribe));
            }
        });
        PAUSE_SUB = true;
        sharedADS.edit().putBoolean("ads", false).apply();
        Snackbar.make(findViewById(R.id.content), getString(R.string.toast_billing_success), Snackbar.LENGTH_LONG).show();
    }

    private List<Purchase> queryPurchase() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        return purchasesResult.getPurchasesList();
    }
}
