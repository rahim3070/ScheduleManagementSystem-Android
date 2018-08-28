package com.example.rahi.schedulemanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewPDFActivity extends AppCompatActivity {
    PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfView = findViewById(R.id.pdfView);

        // bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            file = new File(bundle.getString("path", ""));
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }
}
