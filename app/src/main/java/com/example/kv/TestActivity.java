package com.example.kv;

import static java.lang.Integer.parseInt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.example.kv.databinding.ActivityTestBinding;

import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref;
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Plant channel";
    RadioButton rad;
    int count;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private void showSettingDialog() {
        new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
                .setTitle("Notification Permission")
                .setMessage("Notification permission is required, Please allow notification permission from setting")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showNotificationPermissionRationale() {
        new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
                .setTitle("Alert")
                .setMessage("Notification permission is required, to show notification")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= 26) {
                            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    boolean hasNotificationPermissionGranted = false;


    private ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                hasNotificationPermissionGranted = isGranted;
                if (!isGranted) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 26) {
                            if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                                showNotificationPermissionRationale();
                            } else {
                                showSettingDialog();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "notification permission granted", Toast.LENGTH_SHORT)
                            .show();
                }
            });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = database.getReference();
        setContentView(R.layout.activity_test);
        String name = getIntent().getStringExtra("CounName");
        int pos = getIntent().getIntExtra("Pos", 0);
        FirebaseUser user = mAuth.getCurrentUser();
        String[] st = {"", "", "", ""};
        List<String> t = Arrays.asList(st);
        TestClass test = new TestClass(t, t);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Plant notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Notifications about plants");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                test.questions = snapshot.child("Questions " + name).getValue(t);
                test.answers = snapshot.child("Answers " + name).getValue(t);
                TextView q1 = (TextView) findViewById(R.id.q1);
                TextView q2 = (TextView) findViewById(R.id.q2);
                TextView q3 = (TextView) findViewById(R.id.q3);
                TextView q4 = (TextView) findViewById(R.id.q4);
                RadioButton r11 = (RadioButton) findViewById(R.id.an11);
                RadioButton r12 = (RadioButton) findViewById(R.id.an12);
                RadioButton r13 = (RadioButton) findViewById(R.id.an13);
                RadioButton r21 = (RadioButton) findViewById(R.id.an21);
                RadioButton r22 = (RadioButton) findViewById(R.id.an22);
                RadioButton r23 = (RadioButton) findViewById(R.id.an23);
                RadioButton r24 = (RadioButton) findViewById(R.id.an24);
                RadioButton r25 = (RadioButton) findViewById(R.id.an25);
                RadioButton r26 = (RadioButton) findViewById(R.id.an26);
                q1.setText(test.questions.get(0));
                q2.setText(test.questions.get(1));
                q3.setText(test.questions.get(2));
                q4.setText(test.questions.get(3));
                r11.setText(test.answers.get(0));
                r12.setText(test.answers.get(1));
                r13.setText(test.answers.get(2));
                r21.setText(test.answers.get(3));
                r22.setText(test.answers.get(4));
                r23.setText(test.answers.get(5));
                r24.setText(test.answers.get(6));
                r25.setText(test.answers.get(7));
                r26.setText(test.answers.get(8));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String[] ans = getResources().getStringArray(R.array.ans);
        String correct = ans[pos];
        RadioGroup rd1 = (RadioGroup) findViewById(R.id.rd1);
        RadioGroup rd2 = (RadioGroup) findViewById(R.id.rd2);
        RadioGroup rd3 = (RadioGroup) findViewById(R.id.rd3);
        Button btn = (Button) findViewById(R.id.result);
        Intent intent = new Intent(this, SecondActivity.class);
        EditText txt = (EditText) findViewById(R.id.userinput);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd1.getCheckedRadioButtonId() == R.id.an13) {
                    count += 1;
                }
                if (rd2.getCheckedRadioButtonId() == R.id.an22) {
                    count += 1;
                }
                if (rd3.getCheckedRadioButtonId() == R.id.an24) {
                    count += 1;
                }
                if (txt.getText().toString().equals(correct)) {
                    count += 1;
                }
                writeNewCompleted(name, count);
                if
                (Build.VERSION.SDK_INT >=
                        33
                ) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
                }
                else
                {
                    hasNotificationPermissionGranted =
                            true
                    ;
                }
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(TestActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.willow)
                                .setContentTitle("Поздравляем!")
                                .setContentText("Вы прошли тему " + name + ". Вы набрали " + count + " балла/ов.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(TestActivity.this);


                if (ActivityCompat.checkSelfPermission(TestActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                notificationManager.notify(NOTIFY_ID, builder.build());
                startActivity(intent);
            }
        });
    }

    public void writeNewCompleted(String name, int count) {
        DatabaseReference ref;
        ref = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        Completed completed = new Completed(name, count);
        ref.child("Completed").child(user.getUid()).child(completed.name).setValue(count);
    }



}
