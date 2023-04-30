package fr.esir.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import fr.esir.wifi.DeviceDetailFragment;
import fr.esir.wifi.DeviceListFragment;
import fr.esir.wifi.MyReceiver;
/*
//implements WifiP2pManager.ChannelListener, DeviceListFragment.DeviceActionListener
public class SettingGameActivityB extends Activity  {

    EditText name_player_edit;
    TextView deviceName;
    Button onOff,discover;
    ListView listDevice;
    public static final String TAG = "SettingGameActivity";

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairDevices;


    /**
     * WIFI declaration
     * @param savedInstanceState
     */
    /*
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1001;

    private boolean isWifiP2pEnabled = false;
    private boolean retryChannel = false;

    private final IntentFilter intentFilter = new IntentFilter();
    WifiP2pManager.Channel channel;
    WifiP2pManager manager;
    private MyReceiver receiver = null;
    */
    /**
     * @param //isWifiP2pEnabled the isWifiP2pEnabled to set
     */
    /*
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    private boolean initP2p() {
        // Device capability definition check
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
            Log.e(TAG, "Wi-Fi Direct is not supported by this device.");
            return false;
        }
        Log.d(TAG, "Wi-Fi Direct is supported by this device.");
        // Hardware capability check
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            Log.e(TAG, "Cannot get Wi-Fi system service.");
            return false;
        }
        Log.d(TAG, "Wi-Fi system service retrieved.");
        if (!wifiManager.isP2pSupported()) {
            Log.e(TAG, "Wi-Fi Direct is not supported by the hardware or Wi-Fi is off.");
            return false;
        }
        Log.d(TAG, "Wi-Fi Direct is supported by the hardware and Wi-Fi is on.");
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        if (manager == null) {
            Log.e(TAG, "Cannot get Wi-Fi Direct system service.");
            return false;
        }
        Log.d(TAG, "Wi-Fi Direct system service retrieved.");
        channel = manager.initialize(this, getMainLooper(), null);
        if (channel == null) {
            Log.e(TAG, "Cannot initialize Wi-Fi Direct.");
            return false;
        }
        Log.d(TAG, "WiFi Direct initialization successful!");
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Fine location permission is not granted!");
                    finish();
                }
                break;
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_game_bluetooth);

        onOff = (Button) findViewById(R.id.p2POnOff_button_setting);
        discover = (Button) findViewById(R.id.discover_button_setting);
        listDevice = (ListView) findViewById(R.id.player_bluetooth_setting);
        deviceName = (TextView) findViewById(R.id.device_name_setting_game);

        deviceName.setText(getLocalBluetoothName());

        BA = BluetoothAdapter.getDefaultAdapter();

        if (BA == null){
            Toast.makeText(this,"Bluetooth not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        onOff.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (BA.isEnabled()){
                    BA.disable();
                    //Toast.makeText(this,"Turned off", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentOn,0);
                    //Toast.makeText(this,"Turned on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        discover.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible,0);
                list();
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void list(){
        pairDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : pairDevices){
            list.add(bt.getName());
        }
        Toast.makeText(this,"Showing devices", Toast.LENGTH_SHORT).show();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        listDevice.setAdapter(adapter);

        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String deviceName = (String) adapterView.getItemAtPosition(i);
                BluetoothDevice device = null;
                for (BluetoothDevice bt : pairDevices){
                    if (bt.getName().equals(deviceName)){
                        device = bt;
                        break;
                    }
                }
                if (device != null) {
                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID générique pour les connexions Bluetooth série
                    try {
                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
                        socket.connect();
                        BluetoothDevice finalDevice = device;

                        // Créer un fichier avec le nom du destinataire et la date courante
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String fileName = finalDevice.getName() + "_" + dateFormat.format(new Date()) + ".txt";
                        File file = new File(getExternalFilesDir(null), fileName);
                        FileWriter writer = new FileWriter(file);
                        writer.write("Bonjour " + finalDevice.getName() + ",\n");
                        writer.write("Je vous envoie ce fichier à " + dateFormat.format(new Date()) + ".\n");
                        writer.close();

                        // Envoyer le fichier via Bluetooth
                        sendFile(finalDevice, file);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingGameActivity.this);
                                builder.setTitle("Bluetooth connection");
                                builder.setMessage("Bluetooth connection successful with " + finalDevice.getName());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // fermer la popup et effectuer une autre action si nécessaire
                                    }
                                });
                                builder.create().show();
                            }
                        });
                    } catch (IOException e) {
                        // erreur lors de la connexion, gérer l'exception ici
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingGameActivity.this);
                                builder.setTitle("Bluetooth connection");
                                builder.setMessage("Bluetooth connection lost");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // fermer la popup et effectuer une autre action si nécessaire
                                    }
                                });
                                builder.create().show();
                            }
                        });
                    }
                }
            }
        });
    }
    
    public String getLocalBluetoothName(){
        if(BA == null){
            BA = BluetoothAdapter.getDefaultAdapter();
        }
        @SuppressLint("MissingPermission") String name = BA.getName();
        if(name==null){
            name = BA.getAddress();
        }
        return name;
    }

    @SuppressLint("MissingPermission")
    private void sendFile(BluetoothDevice device, File file) {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
            socket.connect();

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
            socket.close();

            // Envoyer un message de réussite
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingGameActivity.this);
                    builder.setTitle("Bluetooth file transfer");
                    builder.setMessage("File sent successfully to " + device.getName());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // fermer la popup et effectuer une autre action si nécessaire
                        }
                    });
                    builder.create().show();
                }
            });
        } catch (IOException e) {
            // erreur lors de l'envoi du fichier, gérer l'exception ici
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingGameActivity.this);
                    builder.setTitle("Bluetooth file transfer");
                    builder.setMessage("File transfer failed to " + device.getName());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // fermer la popup et effectuer une autre action si nécessaire
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }










    /** register the BroadcastReceiver with the intent values to be matched */
    /*
    @Override
    public void onResume() {
        super.onResume();
        receiver = new MyReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }*/

    /**
     * Remove all peers and clear all fields. This is called on
     * BroadcastReceiver receiving a state change event.
     */
    /*
    public void resetData() {
        DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
        DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        if (fragmentList != null) {
            fragmentList.clearPeers();
        }
        if (fragmentDetails != null) {
            fragmentDetails.resetViews();
        }
    }

    private void initSettting(){
        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        if (!initP2p()) {
            finish();
        }

    }

    private void settingButton(){
        onOff = (Button) findViewById(R.id.p2POnOff_button_setting);
        discover = (Button) findViewById(R.id.discover_button_setting);
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager != null && channel != null) {
                    // Since this is the system wireless settings activity, it's
                    // not going to send us a result. We will be notified by
                    // WiFiDeviceBroadcastReceiver instead.
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } else {
                    Log.e(TAG, "channel or manager is null");
                }
            }
        });

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWifiP2pEnabled) {
                    Toast.makeText(SettingGameActivity.this, R.string.p2p_off_warning,Toast.LENGTH_SHORT).show();
                    //return true;
                }
                final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
                fragment.onInitiateDiscovery();
                if (ActivityCompat.checkSelfPermission(SettingGameActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //return false;
                }
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(SettingGameActivity.this, "Discovery Initiated",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(SettingGameActivity.this, "Discovery Failed : " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onChannelDisconnected() {
        // we will try once more
        if (manager != null && !retryChannel) {
            Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
            resetData();
            retryChannel = true;
            manager.initialize(this, getMainLooper(), this);
        } else {
            Toast.makeText(this, "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
        DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        fragment.showDetails(device);
    }

    @Override
    public void connect(WifiP2pConfig config) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(SettingGameActivity.this, "Connect failed. Retry.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void disconnect() {
        final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        fragment.resetViews();
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
            }
            @Override
            public void onSuccess() {
                fragment.getView().setVisibility(View.GONE);
            }
        });
    }
}*/

