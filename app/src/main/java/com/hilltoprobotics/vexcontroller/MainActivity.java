package com.hilltoprobotics.vexcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.InputDevice;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "VEXController";
    public BluetoothAdapter mBTAdapter = null;
    public BluetoothSocket mBTSocket = null;
    public BluetoothDevice mBTDevice = null;
    public InputStream mBTInputStream  = null;
    public OutputStream mBTOutputStream = null;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address;
    boolean clockwise;
    public static boolean enableDataSend;
    public SharedPreferences preferences;
    TextView connectionStatus;
    Button Forward; //0
    Button ForwardRight; //45
    Button CenterRight; //90
    Button ReverseRight; //135
    Button Reverse; //180
    Button ReverseLeft;
    Button CenterLeft; //270
    Button ForwardLeft;
    Button Neutral;
    Button RotateLeft;
    Button RotateRight;
    Button ArmUp;
    Button ArmDown;
    Button ClawClose;
    Button ClawOpen;
    Button ClawHold;
    int leftX = 0;
    int leftY = 0;
    int rightX = 0;
    int rightY = 0;
    int rightTrigger = 0;
    int leftTrigger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        clockwise = preferences.getBoolean("direction", false);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        Forward = (Button) findViewById(R.id.F);
        Forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        ForwardRight = (Button) findViewById(R.id.FRi);
        ForwardRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(8);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        CenterRight = (Button) findViewById(R.id.CRi);
        CenterRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(10);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        ReverseRight = (Button) findViewById(R.id.RRi);
        ReverseRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(6);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        Reverse = (Button) findViewById(R.id.R);
        Reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(5);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        ReverseLeft = (Button) findViewById(R.id.RLe);
        ReverseLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(4);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        CenterLeft = (Button) findViewById(R.id.CLe);
        CenterLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(11);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        ForwardLeft = (Button) findViewById(R.id.FLe);
        ForwardLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        Neutral = (Button) findViewById(R.id.Neutral);
        Neutral.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataByte((byte) 9);
            }
        });
        RotateLeft = (Button) findViewById(R.id.RoLe);
        RotateLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(7);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        RotateRight = (Button) findViewById(R.id.RoRi);
        RotateRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(3);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(9);
                    return true;
                }
                return false;
            }
        });
        ArmUp = (Button) findViewById(R.id.ArmU);
        ArmUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(12);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(14);
                    return true;
                }
                return false;
            }
        });
        ArmDown = (Button) findViewById(R.id.ArmD);
        ArmDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(13);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(14);
                    return true;
                }
                return false;
            }
        });
        ClawOpen = (Button) findViewById(R.id.ClO);
        ClawOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(15);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(17);
                    return true;
                }
                return false;
            }
        });
        ClawHold = (Button) findViewById(R.id.ClH);
        ClawHold.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(25);
                    return true;
                }
                return false;
            }
        });
        ClawClose = (Button) findViewById(R.id.ClC);
        ClawClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendDataInt(16);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendDataInt(17);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                ThreadExecutor.runTask(new Runnable() {
                    public void run() {
                        if (connect()) {
                            enableDataSend = true;
                            runOnUiThread(new Thread(new Runnable() {
                                public void run() {
                                    connectionStatus.setText("Connected");
                                    connectionStatus.setTextColor(Color.GREEN);
                                }
                            }));
                        }
                    }
                });
                return true;
            case R.id.disconnect:
                enableDataSend = false;
                resetConnection();
                connectionStatus.setText("Disconnected");
                connectionStatus.setTextColor(Color.RED);
                return true;
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        if((ev.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK && ev.getAction() == MotionEvent.ACTION_MOVE) {
            final int historySize = ev.getHistorySize();
            for(int i=0;i<historySize;i++) {
                processJoystickInput(ev, i);
            }
            processJoystickInput(ev, -1);
            return true;
        }
        return super.dispatchGenericMotionEvent(ev);
    }

    private void sendDataByte(byte message) {
        if(enableDataSend) {
            Log.d(TAG, "Send data: " + message);
            try {
                mBTOutputStream.write(message);
            } catch (IOException e) {
            }
        }
    }
    private void sendDataInt(Integer message) {
        if(enableDataSend) {
            Log.d(TAG, "Send data: " + message);
            try {
                mBTOutputStream.write(message);
            } catch (IOException e) {
            }
        }
    }
    private void resetConnection() {
        if (mBTInputStream != null) {
            try {mBTInputStream.close();} catch (Exception e) {}
            mBTInputStream = null;
        }
        if (mBTOutputStream != null) {
            try {mBTOutputStream.close();} catch (Exception e) {}
            mBTOutputStream = null;
        }
        if (mBTSocket != null) {
            try {mBTSocket.close();} catch (Exception e) {}
            mBTSocket = null;
        }
    }

    public boolean connect() {
        address = preferences.getString("btdevice", "00:00:00:00:00:00");
        Log.d(TAG, "connecting to " + address);
        resetConnection();
        if (mBTDevice == null) {
            mBTDevice = mBTAdapter.getRemoteDevice(address);
        }
        try {mBTSocket = mBTDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (Exception e1) {
            Log.d(TAG, "connect(): Failed to bind to RFCOMM by UUID. msg=" + e1.getMessage());
            return false;
        }
        try {
            mBTSocket.connect();
        } catch (Exception e) {
            return false;
        }
        Log.d(TAG, "connect(): CONNECTED!");
        try {
            mBTOutputStream = mBTSocket.getOutputStream();
            mBTInputStream  = mBTSocket.getInputStream();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice mInputDevice = event.getDevice();
        float x = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_X, historyPos);
        float x2 = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_Z, historyPos);
        float y = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_Y, historyPos);
        float y2 = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_RZ, historyPos);
        float lt = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_HAT_Y, historyPos);
        float rt = getCenteredAxis(event, mInputDevice, MotionEvent.AXIS_HAT_X, historyPos);
        leftX = (int) ((x2 * 127) + 127);
        leftY = (int) ((y * -127) + 127);
        rightX = (int) ((x * 127) + 127);
        rightY = (int) ((y2 * -127) + 127);
        leftTrigger = (int) ((lt * -127) + 127);
        rightTrigger = (int) ((rt * 127) + 127);
        sendDataInt(255);
        sendDataInt(leftX);
        sendDataInt(leftY);
        sendDataInt(rightX);
        sendDataInt(rightY);
        sendDataInt(leftTrigger);
        sendDataInt(rightTrigger);
    }
    private static float getCenteredAxis(MotionEvent event, InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range = device.getMotionRange(axis, event.getSource());
        if (range != null) {
            final float flat = range.getFlat();
            final float value = historyPos < 0 ? event.getAxisValue(axis):event.getHistoricalAxisValue(axis, historyPos);
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }
}
