package com.example.greenscape;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;// Кнопка входу з Google
import com.google.android.gms.common.api.ApiException;// Обробка помилок API
import com.google.android.gms.tasks.OnCompleteListener;// Слухач завершення завдань
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;// Ініціалізація Firebase
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth; // Автентифікація Firebase
import com.google.firebase.auth.GoogleAuthProvider;// Провайдер автентифікації Google

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;// Об'єкт аутентифікації Firebase
    GoogleSignInClient googleSignInClient;// Клієнт входу Google
    ShapeableImageView imageView;// Зображення користувача
    TextView name, mail,NoneAccountView;// TextView для імені, пошти, підказки відсутності акаунта


    private Button bLogout;
    // Обробник результату запуску активності для входу Google
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                auth = FirebaseAuth.getInstance();
                                Glide.with(MainActivity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imageView);
                                name.setText(auth.getCurrentUser().getDisplayName());
                                mail.setText(auth.getCurrentUser().getEmail());

                                Intent intent = new Intent(MainActivity.this, MenuActivity.class); // Замініть NextActivity.class на клас вашої наступної активності
                                startActivity(intent);
                                finish(); // Опціонально, для закриття поточної активності


                                Toast.makeText(MainActivity.this, "Sing in Succsesfully ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Failed Sing in  " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);// Ініціалізація Firebase
        imageView = findViewById(R.id.profileImage);// Зображення користувача
        name = findViewById(R.id.nameTV);// Ім'я користувача
        NoneAccountView = findViewById(R.id.NoneAccountView);// Підказка про відсутність акаунта
        mail = findViewById(R.id.mailTV);// Електронна пошта користувача
        bLogout = findViewById(R.id.btnLogout); // Кнопка виходу
        NoneAccountView.setOnClickListener(this::ToastNoneAccount);


        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вихід з облікового запису
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish(); // закрити поточне активіті
            }
        });
        // Налаштування опцій для входу Google
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, options);
        auth = FirebaseAuth.getInstance();
        SignInButton singInButton = findViewById(R.id.singIn);
        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });

    }
    // Відображення підказки про відсутність акаунта
    private void ToastNoneAccount(View view) {
        Toast.makeText(MainActivity.this,"Push Sigin", Toast.LENGTH_SHORT).show();
    }




}