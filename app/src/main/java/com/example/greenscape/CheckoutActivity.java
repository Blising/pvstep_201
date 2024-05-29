package com.example.greenscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivity extends AppCompatActivity {

    private static final String BACKEND_URL = "http://192.168.0.166:3000/create-payment-intent";
    private static final String STRIPE_PUBLISHABLE_KEY = "pk_test_51PM5g2FMN9f0uOqEdzqsHTDmC38RztgLObvO7ogoI6zMXVoNoOKOHAibl0eg5XX6ILyBv82wfO4KI8yW2hZxz6m700EJii6QSv"; // Використовуйте ваш публічний ключ

    private Stripe stripe;
    private String clientSecret;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        PaymentConfiguration.init(this, STRIPE_PUBLISHABLE_KEY);
        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());

        totalAmount = 40.00; // Змінено на 20.00 ₴ для тестування
        TextView totalAmountTextView = findViewById(R.id.totalAmountTextView);
        totalAmountTextView.setText("Загальна сума: " + totalAmount);

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> createPaymentIntent());
    }

    private void createPaymentIntent() {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        JSONObject paymentRequest = new JSONObject();
        try {
            paymentRequest.put("amount", (int) (totalAmount * 100)); // Конвертація у копійки
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(paymentRequest.toString(), JSON);
        Request request = new Request.Builder()
                .url(BACKEND_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        clientSecret = json.getString("clientSecret");

                        // Виклик startCheckout лише після успішного отримання clientSecret
                        runOnUiThread(() -> startCheckout());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Обробка помилок відповіді
                    runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Failed to create payment intent", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.backtomenu) {
            Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void startCheckout() {
        if (clientSecret == null) {
            Toast.makeText(this, "Client secret not retrieved. Try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

        if (params != null) {
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, clientSecret);
            stripe.confirmPayment(this, confirmParams);
        } else {
            Toast.makeText(this, "Invalid payment details. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        stripe.onPaymentResult(requestCode, data, new ApiResultCallback<PaymentIntentResult>() {
            @Override
            public void onSuccess(@NonNull PaymentIntentResult result) {
                PaymentIntent paymentIntent = result.getIntent();
                PaymentIntent.Status status = paymentIntent.getStatus();
                if (status == PaymentIntent.Status.Succeeded) {
                    Toast.makeText(CheckoutActivity.this, "Успішно! Дякуємо за довіру!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CheckoutActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish(); // Закриваємо поточну активність
                } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                    Toast.makeText(CheckoutActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                Toast.makeText(CheckoutActivity.this, "Payment error: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
