package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanQRActivity extends AppCompatActivity {

    private SurfaceView surfaceView ;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector ;
    private TextView msj ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);

        surfaceView = findViewById(R.id.scan_qr);
        msj = findViewById(R.id.qr_msj);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new  CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,680).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();

                if(qrcode.size() != 0){
                    msj.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            msj.setText(qrcode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

    }
}
