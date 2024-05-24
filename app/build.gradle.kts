plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.greenscape"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.greenscape"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    //noinspection GradleCompatible,GradleCompatible
    implementation(fileTree("libs") {
        include("*.jar")
    })

    implementation ("androidx.room:room-runtime:2.5.1")
    annotationProcessor ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.5.1")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")


    implementation ("com.google.firebase:firebase-database:19.7.0")
    implementation ("com.firebaseui:firebase-ui-database:7.1.1")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.gms:google-services:4.4.1")
    implementation ("com.google.android.material:material:1.3.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.firebase:firebase-firestore")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.orhanobut:dialogplus:1.11@aar")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // For Gson converter (you can use other converters if needed)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1") // For logging interceptors (optional)


    implementation ("androidx.camera:camera-camera2:1.3.1")
    implementation ("androidx.camera:camera-lifecycle:1.3.1")
    implementation ("com.google.mlkit:image-labeling:17.0.8")
implementation("com.google.gms.google-services:com.google.gms.google-services.gradle.plugin:4.4.1")


    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.functions)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}