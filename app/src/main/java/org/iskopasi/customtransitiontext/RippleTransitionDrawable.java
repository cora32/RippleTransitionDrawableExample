package org.iskopasi.customtransitiontext;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by cora32 on 04.10.2017.
 */

public class RippleTransitionDrawable extends TransitionDrawable {
    private static final Paint paint = new Paint();
    private static final Paint cropPaint = new Paint();
    private static final Path path = new Path();
    private static Bitmap nextBitmap;
    private static Bitmap output;
    private float nextBitmapWidthHalf;
    private float nextBitmapHeightHalf;
    private float radius;
    private Canvas cropCanvas;
    private ValueAnimator valueAnimator;

    public RippleTransitionDrawable(BitmapDrawable[] layers) {
        super(layers);
        nextBitmap = layers[1].getBitmap();
        if (output != null)
            output.recycle();
        if (output == null || output.isRecycled())
            output = Bitmap.createBitmap(nextBitmap.getWidth(), nextBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        nextBitmapWidthHalf = nextBitmap.getWidth() / 2;
        nextBitmapHeightHalf = nextBitmap.getHeight() / 2;
        cropCanvas = new Canvas(output);

        valueAnimator = ValueAnimator.ofFloat(
                0f,
                (nextBitmap.getHeight() > nextBitmap.getWidth()
                        ? nextBitmap.getHeight()
                        : nextBitmap.getWidth()));
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            radius = (float) animation.getAnimatedValue();
            invalidateSelf();
        });
    }

    public void startTransition(int transitionDuration) {
        paint.setAntiAlias(true);

        cropPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        cropPaint.setAntiAlias(true);

        valueAnimator.setDuration(transitionDuration);
        valueAnimator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        path.reset();
        path.addCircle(nextBitmapWidthHalf, nextBitmapHeightHalf, radius, Path.Direction.CW);
        cropCanvas.drawPath(path, paint);
        cropCanvas.drawBitmap(nextBitmap, 0, 0, cropPaint);
        canvas.drawBitmap(output, 0, 0, paint);
    }
}