package com.fd.describe;

public class DeScribeColourPalettePresenter {


    private final DeScribeColourPaletteView paletteView;

    public interface DeScribeColourPaletteView
    {
        public interface Listener
        {
            public void colourSelected(int colour);
        }

        public void setListener(Listener listener);
        public void displayColour(int colour);
        public void show();
        public void hide();
    }

    public interface DeScribeColourPaletteColourSelectionListener
    {
        void colourSelected(int colour);
    }

    public DeScribeColourPalettePresenter(DeScribeColourPaletteView paletteView, final DeScribeColourPaletteColourSelectionListener listener)
    {
        this.paletteView = paletteView;
        this.paletteView.setListener(new DeScribeColourPaletteView.Listener() {
            @Override
            public void colourSelected(int colour) {
                listener.colourSelected(colour);
            }
        });
    }

    public void showColourPalette() {
        paletteView.show();
    }
}
