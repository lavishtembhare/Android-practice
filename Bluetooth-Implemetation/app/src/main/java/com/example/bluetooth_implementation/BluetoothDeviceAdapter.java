package com.example.bluetooth_implementation;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.DeviceViewHolder> {

    private List<BluetoothDevice> deviceList;
    private Context context;
    private OnDeviceClickListener listener;

    public interface OnDeviceClickListener {
        void onDeviceClick(BluetoothDevice device);
    }

    public BluetoothDeviceAdapter(List<BluetoothDevice> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        BluetoothDevice device = deviceList.get(position);

        try {
            // Device name
            String deviceName = device.getName();
            if (deviceName == null || deviceName.isEmpty()) {
                deviceName = "Unknown Device";
            }
            holder.deviceName.setText(deviceName);

            // Device address
            holder.deviceAddress.setText(device.getAddress());

            // Device type
            String deviceType = getDeviceType(device);
            holder.deviceType.setText(deviceType);

            // Connection status (simplified - showing bond state)
            int bondState = device.getBondState();
            String status;
            int statusColor;

            switch (bondState) {
                case BluetoothDevice.BOND_BONDED:
                    status = "Paired";
                    statusColor = context.getResources().getColor(R.color.success_green);
                    break;
                case BluetoothDevice.BOND_BONDING:
                    status = "Pairing...";
                    statusColor = context.getResources().getColor(R.color.warning_orange);
                    break;
                default:
                    status = "Not Paired";
                    statusColor = context.getResources().getColor(R.color.secondary_text);
                    break;
            }

            holder.connectionStatus.setText(status);
            holder.connectionStatus.setTextColor(statusColor);

            // Device icon based on type
            int iconResource = getDeviceIcon(device);
            holder.deviceIcon.setImageResource(iconResource);

            // Click listener
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeviceClick(device);
                }
            });

        } catch (SecurityException e) {
            // Handle permission issues for Android 12+
            holder.deviceName.setText("Permission Required");
            holder.deviceAddress.setText("Grant Bluetooth permissions");
            holder.deviceType.setText("Unknown");
            holder.connectionStatus.setText("No Access");
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    private String getDeviceType(BluetoothDevice device) {
        try {
            BluetoothClass bluetoothClass = device.getBluetoothClass();
            if (bluetoothClass != null) {
                int deviceClass = bluetoothClass.getDeviceClass();

                switch (bluetoothClass.getMajorDeviceClass()) {
                    case BluetoothClass.Device.Major.PHONE:
                        return "Phone";
                    case BluetoothClass.Device.Major.COMPUTER:
                        return "Computer";
                    case BluetoothClass.Device.Major.AUDIO_VIDEO:
                        if ((deviceClass & BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES) != 0) {
                            return "Headphones";
                        } else if ((deviceClass & BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER) != 0) {
                            return "Speaker";
                        } else {
                            return "Audio Device";
                        }
                    case BluetoothClass.Device.Major.PERIPHERAL:
                        return "Input Device";
                    case BluetoothClass.Device.Major.IMAGING:
                        return "Camera";
                    case BluetoothClass.Device.Major.WEARABLE:
                        return "Wearable";
                    case BluetoothClass.Device.Major.TOY:
                        return "Toy";
                    case BluetoothClass.Device.Major.HEALTH:
                        return "Health Device";
                    default:
                        return "Bluetooth Device";
                }
            }
        } catch (Exception e) {
            // Handle any exceptions
        }
        return "Unknown Device";
    }

    private int getDeviceIcon(BluetoothDevice device) {
        try {
            BluetoothClass bluetoothClass = device.getBluetoothClass();
            if (bluetoothClass != null) {
                switch (bluetoothClass.getMajorDeviceClass()) {
                    case BluetoothClass.Device.Major.PHONE:
                        return R.drawable.ic_phone;
                    case BluetoothClass.Device.Major.COMPUTER:
                        return R.drawable.ic_computer;
                    case BluetoothClass.Device.Major.AUDIO_VIDEO:
                        return R.drawable.ic_headphones;
                    case BluetoothClass.Device.Major.PERIPHERAL:
                        return R.drawable.ic_keyboard;
                    case BluetoothClass.Device.Major.WEARABLE:
                        return R.drawable.ic_watch;
                    default:
                        return R.drawable.ic_bluetooth_device;
                }
            }
        } catch (Exception e) {
            // Handle any exceptions
        }
        return R.drawable.ic_bluetooth_device;
    }

    public void updateDeviceList(List<BluetoothDevice> newDeviceList) {
        this.deviceList = newDeviceList;
        notifyDataSetChanged();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName, deviceAddress, deviceType, connectionStatus;
        ImageView deviceIcon;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.deviceName);
            deviceAddress = itemView.findViewById(R.id.deviceAddress);
            deviceType = itemView.findViewById(R.id.deviceType);
            connectionStatus = itemView.findViewById(R.id.connectionStatus);
            deviceIcon = itemView.findViewById(R.id.deviceIcon);
        }
    }
}