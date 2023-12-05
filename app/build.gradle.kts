plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 26
        targetSdk = 33
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

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    ////
    // Firebase Core // Firestore // Authentication
    implementation("com.google.firebase:firebase-core:20.0.0")
    implementation("com.google.firebase:firebase-firestore:20.0.0")
    implementation("com.google.firebase:firebase-auth:20.0.0")


    //
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.orhanobut:dialogplus:1.11@aar")
    //
    //implementation ("com.google.firebase:firebase-auth:29.0.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.google.firebase:firebase-firestore:23.0.3")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")


    implementation ("com.github.bumptech.glide:glide:4.16.0")


    // material design 3
    implementation ("androidx.compose.material3:material3:1.0.1")
    implementation ("androidx.compose.material3:material3-window-size-class:1.0.1")
    
    //cardview
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.firebaseui:firebase-ui-firestore:8.0.0")


    //
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.squareup.picasso:picasso:2.5.2")

    //Navegacion
    val nav_version = "2.5.3"
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation ("com.google.android.material:material:1.4.0")

}
apply (plugin = "com.google.gms.google-services")
apply (plugin = "com.android.application")

