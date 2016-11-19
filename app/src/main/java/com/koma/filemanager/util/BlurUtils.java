package com.koma.filemanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by koma on 11/19/16.
 */

public class BlurUtils {

    public static Drawable blurBitmap(Context context, Bitmap bitmap) {
        final RenderScript renderScript = RenderScript.create(context);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = Constants.IN_SAMPLE_SIZE;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.COMPRESS_QUALITY, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        Bitmap blurTemplate = BitmapFactory.decodeStream(bis, null, options);
        final Allocation input = Allocation.createFromBitmap(renderScript, blurTemplate);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setRadius(Constants.BLUR_RADIUS);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(blurTemplate);
        renderScript.destroy();
        return new BitmapDrawable(context.getResources(), blurTemplate);
    }
}
