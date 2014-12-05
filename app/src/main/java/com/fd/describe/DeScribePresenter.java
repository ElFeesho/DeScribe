package com.fd.describe;

public class DeScribePresenter {
    public interface DeScribeView {
        public static class DeScribePoint
        {
            public final float x, y, pressure;

            public DeScribePoint(float x, float y, float pressure) {
                this.x = x;
                this.y = y;
                this.pressure = pressure;
            }
        }

        public interface Listener {
            void canDraw();

            void size(int width, int height);

            void cannotDraw();
        }

        public interface DrawListener
        {
            void penDown(DeScribePoint point);
            void penUp(DeScribePoint point);
            void lineTo(DeScribePoint point);
            void eraseAt(DeScribePoint point);
        }

        void setListener(Listener listener);
        void setDrawListener(DrawListener listener);
        void removeDrawListener();
    }

    public interface DeScribeRenderer
    {
        void beginPath(DeScribeView.DeScribePoint point);
        void endPath(DeScribeView.DeScribePoint point);
        void quadTo(DeScribeView.DeScribePoint point);

        void setCanvasSize(int width, int height);

        void setLineThickness(float thickness);
        void setColour(int colour);

        void erasePathAt(DeScribeView.DeScribePoint point);
    }

    private final DeScribeView view;
    private final DeScribeRenderer renderer;

    public DeScribePresenter(DeScribeView view, DeScribeRenderer renderer)
    {
        this.view = view;
        this.renderer = renderer;

        this.view.setListener(new DeScribeView.Listener() {
            @Override
            public void canDraw() {
                DeScribePresenter.this.view.setDrawListener(new DeScribeView.DrawListener() {
                    @Override
                    public void penDown(DeScribeView.DeScribePoint point) {
                        DeScribePresenter.this.renderer.beginPath(point);
                    }

                    @Override
                    public void penUp(DeScribeView.DeScribePoint point) {
                        DeScribePresenter.this.renderer.endPath(point);
                    }

                    @Override
                    public void lineTo(DeScribeView.DeScribePoint point) {
                        DeScribePresenter.this.renderer.quadTo(point);
                    }

                    @Override
                    public void eraseAt(DeScribeView.DeScribePoint point) {
                        DeScribePresenter.this.renderer.erasePathAt(point);
                    }
                });
            }

            @Override
            public void size(int width, int height) {
                DeScribePresenter.this.renderer.setCanvasSize(width, height);
            }

            @Override
            public void cannotDraw() {
                DeScribePresenter.this.view.removeDrawListener();
            }
        });
    }
}
