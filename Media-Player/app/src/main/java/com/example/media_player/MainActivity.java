package com.example.media_player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button start, pause, stop;
    private MediaPlayer mediaPlayer;
    private boolean isMediaPlayerPrepared = false;
    private static final String MUSIC_FILE_PATH = "/Music/Kingdom_Dance.mp3";//file path

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean allGranted = true;
                for (Boolean isGranted : permissions.values()) {
                    allGranted = allGranted && isGranted;
                }

                if (allGranted) {
                    initializeMediaPlayer();
                } else {
                    Toast.makeText(this, "Storage permissions required to play music files",
                            Toast.LENGTH_LONG).show();
                    disableButtons();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        start = findViewById(R.id.button1);
        pause = findViewById(R.id.button2);
        stop = findViewById(R.id.button3);

        // Check and request permissions
        checkPermissions();

        // Set click listeners
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying()) {
                        if (!isMediaPlayerPrepared) {
                            try {
                                mediaPlayer.prepare();
                                isMediaPlayerPrepared = true;
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "Error preparing media player",
                                        Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                return;
                            }
                        }
                        mediaPlayer.start();
                        Toast.makeText(MainActivity.this, "Music playing",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(MainActivity.this, "Music paused",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.reset();
                    isMediaPlayerPrepared = false;
                    try {
                        String filePath = Environment.getExternalStorageDirectory().getPath() + MUSIC_FILE_PATH;
                        mediaPlayer.setDataSource(filePath);
                        Toast.makeText(MainActivity.this, "Music stopped",
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, "Error resetting media player",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void checkPermissions() {
        String[] permissions;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }

        if (allPermissionsGranted) {
            initializeMediaPlayer();
        } else {
            requestPermissionLauncher.launch(permissions);
        }
    }

    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(MainActivity.this, "Media player error occurred",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        try {
            String filePath = Environment.getExternalStorageDirectory().getPath() + MUSIC_FILE_PATH;
            File musicFile = new File(filePath);

            if (musicFile.exists()) {
                mediaPlayer.setDataSource(filePath);
                // Don't prepare here - we'll prepare when the user presses play
                isMediaPlayerPrepared = false;
                enableButtons();
            } else {
                Toast.makeText(this, "Music file not found: " + filePath,
                        Toast.LENGTH_LONG).show();
                disableButtons();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing media player: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            disableButtons();
        }
    }

    private void enableButtons() {
        start.setEnabled(true);
        pause.setEnabled(true);
        stop.setEnabled(true);
    }

    private void disableButtons() {
        start.setEnabled(false);
        pause.setEnabled(false);
        stop.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}