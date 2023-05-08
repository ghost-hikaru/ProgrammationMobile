package fr.esir.project_game;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMusicActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION = 123;
    private final int REQUEST_STORAGE_PERMISSION = 1;
    private final int REQUEST_CODE_FILE = 456;
    private final String TAG = "AddMusicActivity";
    private Intent requestFileIntent;
    private Button importButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_music);

        importButton = findViewById(R.id.submit_button_add_music);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFileIntent = new Intent(Intent.ACTION_PICK);
                requestFileIntent.setType("audio/mp3");
                requestFile();
            }
        });
    }


    private void requestFile() {
        /**
         * When the user requests a file, send an Intent to the
         * server app.
         * files.
         */
        startActivityForResult(requestFileIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);
        System.out.println("onActivityResult");
        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            System.out.println("Error");
            return;
        } else {
            // Get the file's content URI from the incoming Intent
            Uri returnUri = returnIntent.getData();
            String fileName = getFileName(returnUri);
            try {
                copyFileFromUri(returnUri, new File(getFilesDir(), fileName));
            } catch (IOException e) {
                System.out.println("Error : " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private void copyFileFromUri(Uri uri, File destinationFile) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(destinationFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        inputStream.close();
        System.out.println("File copied");
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        System.out.println("File name : " + fileName);
        return fileName;
    }
}
