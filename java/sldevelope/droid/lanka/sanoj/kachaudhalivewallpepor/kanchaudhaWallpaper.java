package sldevelope.droid.lanka.sanoj.kachaudhalivewallpepor;

import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class kanchaudhaWallpaper extends WallpaperService  {

    @Override
    public Engine onCreateEngine() {
        try{
            Movie movie = Movie.decodeStream(getResources().getAssets().open("wallpepor.gif"));
            return  new kanchaudhaWallpaperEngine(movie);


        } catch (IOException e) {
            Log.d("Wallpapor", "Error");
            return null;

        }
    }

    private class kanchaudhaWallpaperEngine extends Engine {
        private final int frameDuration = 20;

        private SurfaceHolder holder;
        private Movie movie;
        private boolean visible;
        private Handler handler;


        public kanchaudhaWallpaperEngine(Movie movie) {
            this.movie = movie;
            handler = new Handler();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        private Runnable drawGIF = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();
                canvas.scale(1.3f, 1.3f);
                movie.draw(canvas, -100, 0);
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                movie.setTime((int) (System.currentTimeMillis()) % movie.duration());


                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }

        }

        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawGIF);
            } else
                handler.removeCallbacks(drawGIF);

        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawGIF);

        }
    }
}
