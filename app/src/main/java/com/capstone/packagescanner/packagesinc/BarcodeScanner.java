package com.capstone.packagescanner.packagesinc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scandit.barcodepicker.OnScanListener;
import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.recognition.Barcode;
import com.scandit.barcodepicker.BarcodePicker;

import java.util.List;

/**
 * Created by austi on 2016-03-23.
 */
public class BarcodeScanner extends AppCompatActivity implements OnScanListener {

    private BarcodePicker picker;
    private ScanSettings settings;
    private Intent intent;
    private String scannedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.intent = getIntent();

        this.initScanner();
        setContentView(picker);
    }

    @Override
    protected void onResume() {
        picker.startScanning();
        super.onResume();
    }

    @Override
    protected void onPause() {
        picker.stopScanning();
        super.onPause();
    }

    @Override
    public void didScan(ScanSession scanSession) {
        List<Barcode> barcodes = scanSession.getAllRecognizedCodes();

        if (barcodes.size() >= 1) {
            this.scannedString = barcodes.get(0).getData();
            this.returnScannedCode();
        }
    }

    private void returnScannedCode() {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra(MainPage.SCANNED_BARCODE, this.scannedString);
        startActivity(intent);
    }

    private void initScanner() {
        ScanditLicense.setAppKey(getString(R.string.scandit_default_app_key));
        if (null == settings) {
            this.settings = ScanSettings.create();
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_QR, true);
        }
        if (null == picker) {
            // Instantiate the barcode picker by using the settings defined above.
            this.picker = new BarcodePicker(this, settings);
            // Set the on scan listener to receive barcode scan events.
            this.picker.setOnScanListener(this);
        }
    }
}