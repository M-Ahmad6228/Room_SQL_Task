package com.example.room_sql_task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmployeeActivity extends AppCompatActivity {

    int PHOTO_PICK_CODE = 102;
    int PHOTO_CLICK_CODE = 103;
    ProgressBar progressBar;
    LinearLayout takePicture;
    LinearLayout uploadPicture;
    LinearLayout removePicture;
    ImageView image;
    TextView fullName;
    TextView emailAddress;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    Employee userEmployee;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        initializeViews();
    }

    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar2);
        image = findViewById(R.id.profile_image);
        fullName = findViewById(R.id.emp_fullName);
        emailAddress = findViewById(R.id.emp_email);
        userEmployee = LoginFragment._this.getUserEmployee();
        fullName.setText(userEmployee.getFullName());
        emailAddress.setText(userEmployee.getEmailAddress());
        if(!userEmployee.getImageSource().equals(""))
            image.setImageURI(Uri.parse(userEmployee.getImageSource()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == PERMISSION_ALL){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for(int i = 0; i < grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if(deniedCount == 0)
                return;
            else{
                for(Map.Entry<String, Integer> entry : permissionResults.entrySet()){
                    String permName = entry.getKey();

                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        checkAndRequestPermissions();
                    }
                }
            }
        }
    }

    private void openActionSheet() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        takePicture = sheetView.findViewById(R.id.takePicture);
        uploadPicture = sheetView.findViewById(R.id.uploadPicture);
        removePicture = sheetView.findViewById(R.id.removePicture);

        takePicture.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, PHOTO_CLICK_CODE);});
        uploadPicture.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_PICK_CODE);
        });
        removePicture.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {

                mBottomSheetDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar);
                image.setImageBitmap(bitmap);
                userEmployee.setImageSource("");
                LoginFragment.db.employeeDAO().update(userEmployee);

            }, 1000);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_CLICK_CODE) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(thumbnail);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, "TEMP" , "");
                if(path != null) {
                    userEmployee.setImageSource(path);
                    LoginFragment.db.employeeDAO().update(userEmployee);
                }
                mBottomSheetDialog.dismiss();
            } else if (requestCode == PHOTO_PICK_CODE) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {

                    progressBar.setVisibility(View.GONE);
                    Uri path = Objects.requireNonNull(data).getData();
                    image.setImageURI(path);
                    if(path != null) {
                        userEmployee.setImageSource(path.toString());
                        LoginFragment.db.employeeDAO().update(userEmployee);
                    }
                    mBottomSheetDialog.dismiss();

                }, 1500);
            }
        }
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String perm: PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(perm);
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_ALL);
            return false;
        }
        return true;
    }

    public void tapAvatar(View view){
        if(!checkAndRequestPermissions())
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        else
            openActionSheet();
    }

    public void logout(View view){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            PreferenceData.setUserLoggedInStatus(this, false);
            PreferenceData.setLoggedInUserEmail(this, "");
            Intent intent = new Intent(this, FragmentActivity.class);
            startActivity(intent);
            finish();
        }, 1500);
    }
}
