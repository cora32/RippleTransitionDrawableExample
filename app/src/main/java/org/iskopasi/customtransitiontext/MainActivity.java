package org.iskopasi.customtransitiontext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = findViewById(R.id.iv);
        TextView tv = findViewById(R.id.tv);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.dr1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.dr2);
        BitmapDrawable drawable1 = new BitmapDrawable(getResources(), bitmap1);
        BitmapDrawable drawable2 = new BitmapDrawable(getResources(), bitmap2);

        iv.setImageDrawable(drawable1);

        tv.setOnClickListener((l) -> {
            BitmapDrawable[] layers = new BitmapDrawable[2];
            layers[0] = drawable1;
            layers[1] = drawable2;
            RippleTransitionDrawable transition = new RippleTransitionDrawable(layers);
            iv.post(() -> iv.setImageDrawable(transition));
            transition.startTransition(1500);
        });
    }
}
