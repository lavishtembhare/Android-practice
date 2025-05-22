package com.example.bluetooth_implementation;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVERABLE_BT = 2;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 3;

    private TextView out;
    private TextView statusText;
    private Button button1, button2, button3;
    private BluetoothAdapter mBluetoothAdapter;

    // Activity Result Launchers for modern Android
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    private ActivityResultLauncher<Intent> discoverableLauncher;

    // Broadcast receiver for Bluetooth state changes
    private final BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                updateBluetoothStatus();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeBluetoothAdapter();
        setupActivityResultLaunchers();
        checkBluetoothPermissions();
        setupClickListeners();
        updateBluetoothStatus();
    }

    private void initializeViews() {
        out = findViewById(R.id.out);
        statusText = findViewById(R.id.statusText);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
    }

    private void initializeBluetoothAdapter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
            mBluetoothAdapter = bluetoothManager.getAdapter();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (mBluetoothAdapter == null) {
            out.setText("❌ Bluetooth not supported on this device");
            statusText.setText("Device not supported");
            disableAllButtons();
        }
    }

    private void setupActivityResultLaunchers() {
        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            showToast("✅ Bluetooth enabled successfully");
                            out.setText("✅ Bluetooth has been turned on");
                        } else {
                            showToast("❌ Bluetooth enable request denied");
                            out.setText("❌ Bluetooth enable request was denied");
                        }
                        updateBluetoothStatus();
                    }
                });

        discoverableLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int duration = result.getResultCode();
                        if (duration > 0) {
                            showToast("✅ Device is discoverable for " + duration + " seconds");
                            out.setText("✅ Device is now discoverable for " + duration + " seconds");
                        } else {
                            showToast("❌ Discoverable request denied");
                            out.setText("❌ Discoverable request was denied");
                        }
                    }
                });
    }

    private void checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            String[] permissions = {
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE
            };

            boolean needsPermission = false;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    needsPermission = true;
                    break;
                }
            }

            if (needsPermission) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_BLUETOOTH_PERMISSIONS);
            }
        }
    }

    private void setupClickListeners() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    showToast("❌ Bluetooth not supported");
                    return;
                }

                if (!hasBluetoothPermission()) {
                    showToast("❌ Bluetooth permissions required");
                    return;
                }

                if (mBluetoothAdapter.isEnabled()) {
                    showToast("ℹ️ Bluetooth is already enabled");
                    out.setText("ℹ️ Bluetooth is already turned on");
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    enableBluetoothLauncher.launch(enableBtIntent);
                    out.setText("🔄 Requesting to enable Bluetooth...");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    showToast("❌ Bluetooth not supported");
                    return;
                }

                if (!hasBluetoothPermission()) {
                    showToast("❌ Bluetooth permissions required");
                    return;
                }

                if (!mBluetoothAdapter.isEnabled()) {
                    showToast("❌ Please enable Bluetooth first");
                    out.setText("❌ Bluetooth must be enabled first");
                    return;
                }

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                discoverableLauncher.launch(discoverableIntent);
                out.setText("🔄 Requesting to make device discoverable...");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    showToast("❌ Bluetooth not supported");
                    return;
                }

                if (!hasBluetoothPermission()) {
                    showToast("❌ Bluetooth permissions required");
                    return;
                }

                if (mBluetoothAdapter.isEnabled()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // Android 13+ doesn't allow programmatic disable
                        showToast("ℹ️ Please disable Bluetooth manually from settings");
                        out.setText("ℹ️ Use device settings to turn off Bluetooth on Android 13+");
                    } else {
                        mBluetoothAdapter.disable();
                        showToast("🔄 Turning off Bluetooth...");
                        out.setText("🔄 Bluetooth is being turned off...");
                    }
                } else {
                    showToast("ℹ️ Bluetooth is already disabled");
                    out.setText("ℹ️ Bluetooth is already turned off");
                }
            }
        });
    }

    private boolean hasBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true; // Older versions don't need runtime permissions
    }

    private void updateBluetoothStatus() {
        if (mBluetoothAdapter == null) {
            statusText.setText("Not supported");
            return;
        }

        if (!hasBluetoothPermission()) {
            statusText.setText("Permissions required");
            return;
        }

        if (mBluetoothAdapter.isEnabled()) {
            statusText.setText("✅ Bluetooth is ON");
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
        } else {
            statusText.setText("❌ Bluetooth is OFF");
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
        }
    }

    private void disableAllButtons() {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register receiver for Bluetooth state changes
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothStateReceiver, filter);
        updateBluetoothStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister receiver
        try {
            unregisterReceiver(bluetoothStateReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver was not registered
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            updateBluetoothStatus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}