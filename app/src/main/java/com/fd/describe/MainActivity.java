package com.fd.describe;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    DeScribePresenter scribePresenter;
    DeScribeColourPalettePresenter colourPalettePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        SurfaceViewDeScribeView view = (SurfaceViewDeScribeView) findViewById(R.id.DeScribeView);
        LinearLayoutColourPalette colourPalette = (LinearLayoutColourPalette) findViewById(R.id.DeScribeColourPalette);

        scribePresenter = new DeScribePresenter(view, new SurfaceViewDeScribeRenderer(view), new DeScribePresenter.ColourPaletteListener() {
            @Override
            public void displayColourPalette() {
                colourPalettePresenter.showColourPalette();   
            }
        });

        colourPalettePresenter = new DeScribeColourPalettePresenter(colourPalette, new DeScribeColourPalettePresenter.DeScribeColourPaletteColourSelectionListener() {
            @Override
            public void colourSelected(int colour) {
                scribePresenter.setColour(colour);
            }
        });
    }
}
