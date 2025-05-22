package com.example.bluetooth_implementation;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVERABLE_BT = 2;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 3;

    private TextView out;
    private TextView statusText;
    private TextView noPairedDevicesText;
    private Button button1, button2, button3, refreshButton;
    private RecyclerView pairedDevicesRecyclerView;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDeviceAdapter deviceAdapter;
    private List<BluetoothDevice> pairedDevicesList;

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
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                loadPairedDevices();
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
        loadPairedDevices();
    }

    private void initializeViews() {
        out = findViewById(R.id.out);
        statusText = findViewById(R.id.statusText);
        noPairedDevicesText = findViewById(R.id.noPairedDevicesText);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        refreshButton = findViewById(R.id.refreshButton);
        pairedDevicesRecyclerView = findViewById(R.id.pairedDevicesRecyclerView);

        // Setup RecyclerView
        pairedDevicesList = new ArrayList<>();
        deviceAdapter = new BluetoothDeviceAdapter(pairedDevicesList, this);
        pairedDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pairedDevicesRecyclerView.setAdapter(deviceAdapter);

        // Set device click listener
        deviceAdapter.setOnDeviceClickListener(device -> {
            showDeviceInfo(device);
        });
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

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPairedDevices();
                showToast("🔄 Refreshing paired devices list");
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
            refreshButton.setEnabled(true);
            loadPairedDevices();
        } else {
            statusText.setText("❌ Bluetooth is OFF");
            button1.setEnabled(true);
            button2.setEnabled(false);
            button3.setEnabled(false);
            refreshButton.setEnabled(false);
            pairedDevicesList.clear();
            deviceAdapter.notifyDataSetChanged();
            updatePairedDevicesVisibility();
        }
    }

    private void loadPairedDevices() {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            pairedDevicesList.clear();
            deviceAdapter.notifyDataSetChanged();
            updatePairedDevicesVisibility();
            return;
        }

        if (!hasBluetoothPermission()) {
            showToast("❌ Bluetooth permissions required");
            return;
        }

        try {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            pairedDevicesList.clear();

            if (pairedDevices.size() > 0) {
                pairedDevicesList.addAll(pairedDevices);
                out.setText("✅ Found " + pairedDevices.size() + " paired device(s)");
            } else {
                out.setText("ℹ️ No paired devices found");
            }

            deviceAdapter.notifyDataSetChanged();
            updatePairedDevicesVisibility();

        } catch (SecurityException e) {
            showToast("❌ Permission denied to access paired devices");
            out.setText("❌ Cannot access paired devices - permission denied");
        }
    }

    private void updatePairedDevicesVisibility() {
        if (pairedDevicesList.isEmpty()) {
            pairedDevicesRecyclerView.setVisibility(View.GONE);
            noPairedDevicesText.setVisibility(View.VISIBLE);
        } else {
            pairedDevicesRecyclerView.setVisibility(View.VISIBLE);
            noPairedDevicesText.setVisibility(View.GONE);
        }
    }

    private void showDeviceInfo(BluetoothDevice device) {
        try {
            StringBuilder info = new StringBuilder();
            info.append("Device: ").append(device.getName() != null ? device.getName() : "Unknown");
            info.append("\nAddress: ").append(device.getAddress());
            info.append("\nBond State: ");

            switch (device.getBondState()) {
                case BluetoothDevice.BOND_BONDED:
                    info.append("Paired");
                    break;
                case BluetoothDevice.BOND_BONDING:
                    info.append("Pairing...");
                    break;
                default:
                    info.append("Not Paired");
                    break;
            }

            out.setText(info.toString());
            showToast("ℹ️ Device info displayed");

        } catch (SecurityException e) {
            showToast("❌ Cannot access device information");
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
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

}