/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;

/**
 * Utility methods for working with text.
 */
public class TextUtils {

    private TextUtils() {
        // no need to instantiate this
    }
    
    /**
     * Draws a string such that the specified anchor point is aligned to the
     * given (x, y) location.
     *
     * @param text  the text.
     * @param canvas  the graphics device.
     * @param paint  the paint.
     * @param x  the x coordinate (Java 2D).
     * @param y  the y coordinate (Java 2D).
     * @param anchor  the anchor location.
     *
     * @return The text bounds (adjusted for the text position).
     */
    public static RectF drawAlignedString(String text,
            Canvas canvas, Paint paint, float x, float y, TextAnchor anchor) {

        RectF textBounds = new RectF();
        float[] adjust = deriveTextBoundsAnchorOffsets(canvas, paint, text, 
                anchor, textBounds);
        // adjust text bounds to match string position
        textBounds.set(x + adjust[0], y + adjust[1] + adjust[2],
            x + textBounds.width(), x + textBounds.height());
        canvas.drawText(text, x + adjust[0], y + adjust[1], paint);
        return textBounds;
    }

    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the (x, y) coordinate for drawing text is a point on the
     * baseline at the left of the text string.  If you add these offsets to
     * (x, y) and draw the string, then the anchor point should coincide with
     * the (x, y) point.
     *
     * @param canvas  the graphics device (not <code>null</code>).
     * @param text  the text.
     * @param anchor  the anchor point.
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Canvas canvas, Paint paint,
            String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontMetrics fm = paint.getFontMetrics();
        RectF bounds = getTextBounds(text, paint);
        float ascent = fm.ascent;
        float halfAscent = ascent / 2.0f;
        float descent = fm.descent;
        float leading = fm.leading;
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = -bounds.width() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = -bounds.width();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + bounds.height();
        }
        else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        }
        else if (anchor.isHalfHeight()) {
            yAdj = -descent - leading + (float) (bounds.height() / 2.0);
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = -descent - leading;
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }

    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the (x, y) coordinate for drawing text is a point on the
     * baseline at the left of the text string.  If you add these offsets to
     * (x, y) and draw the string, then the anchor point should coincide with
     * the (x, y) point.
     *
     * @param g2  the graphics device (not <code>null</code>).
     * @param text  the text.
     * @param anchor  the anchor point.
     * @param textBounds  the text bounds (if not <code>null</code>, this
     *                    object will be updated by this method to match the
     *                    string bounds).
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Canvas canvas, Paint paint,
            String text, TextAnchor anchor, RectF textBounds) {

        float[] result = new float[3];
        FontMetrics fm = paint.getFontMetrics();
        RectF bounds = getTextBounds(text, paint);
        float ascent = fm.ascent;
        result[2] = -ascent;
        float halfAscent = ascent / 2.0f;
        float descent = fm.descent;
        float leading = fm.leading;
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = -bounds.width() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = -bounds.width();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + bounds.height();
        }
        else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        }
        else if (anchor.isHorizontalCenter()) {
            yAdj = -descent - leading + bounds.height() / 2.0f;
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = -descent - leading;
        }
        if (textBounds != null) {
            textBounds.set(bounds);
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }
    /**
     * Returns the bounds for the specified text.
     *
     * @param text  the text (<code>null</code> permitted).
     * @param paint  the paint (not <code>null</code>).
     *
     * @return The text bounds (<code>null</code> if the <code>text</code>
     *         argument is <code>null</code>).
     */
    public static RectF getTextBounds(String text, Paint paint) {
        float w = paint.measureText(text);
        FontMetrics fm = paint.getFontMetrics();
        return new RectF(0.0f, fm.top, w, fm.bottom);
    }
    
    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point.
     *
     * @param text  the text.
     * @param canvas  the graphics device.
     * @param paint  the paint.
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor.
     * @param angle  the rotation angle.
     * @param rotationX  the x-coordinate for the rotation anchor point.
     * @param rotationY  the y-coordinate for the rotation anchor point.
     */
    public static void drawRotatedString(String text, Canvas canvas, 
            Paint paint, float x, float y, TextAnchor textAnchor, double angle,
            float rotationX, float rotationY) {

        if (text == null || text.equals("")) {
            return;
        }
        float[] textAdj = deriveTextBoundsAnchorOffsets(canvas, paint, text, textAnchor);
        drawRotatedString(text, canvas, paint, x + textAdj[0], y + textAdj[1], angle,
                rotationX, rotationY);
    }

    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point.
     *
     * @param text  the text.
     * @param canvas  the graphics device.
     * @param paint  the paint.
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor.
     * @param angle  the rotation angle (in radians).
     * @param rotationAnchor  the rotation anchor.
     */
    public static void drawRotatedString(String text, Canvas canvas, 
            Paint paint, float x, float y, TextAnchor textAnchor,
            double angle, TextAnchor rotationAnchor) {

        if (text == null || text.equals("")) {
            return;
        }
        float[] textAdj = deriveTextBoundsAnchorOffsets(canvas, paint, text, textAnchor);
        float[] rotateAdj = deriveRotationAnchorOffsets(canvas, paint, text,
                rotationAnchor);
        drawRotatedString(text, canvas, paint, x + textAdj[0], y + textAdj[1],
                angle, x + textAdj[0] + rotateAdj[0],
                y + textAdj[1] + rotateAdj[1]);

    }
        /**
     * A utility method that calculates the rotation anchor offsets for a
     * string.  These offsets are relative to the text starting coordinate
     * (BASELINE_LEFT).
     *
     * @param canvas  the graphics device.
     * @param text  the text.
     * @param anchor  the anchor point.
     *
     * @return  The offsets.
     */
    private static float[] deriveRotationAnchorOffsets(Canvas canvas, 
            Paint paint, String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontMetrics fm = paint.getFontMetrics();
        RectF bounds = TextUtils.getTextBounds(text, paint);
        float ascent = fm.ascent;
        float halfAscent = ascent / 2.0f;
        float descent = fm.descent;
        float leading = fm.leading;
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isLeft()) {
            xAdj = 0.0f;
        }
        else if (anchor.isHorizontalCenter()) {
            xAdj = bounds.width() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = bounds.width();
        }

        if (anchor.isTop()) {
            yAdj = descent + leading - bounds.height();
        }
        else if (anchor.isHalfHeight()) {
            yAdj = descent + leading - bounds.height() / 2.0f;
        }
        else if (anchor.isHalfAscent()) {
            yAdj = -halfAscent;
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = descent + leading;
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }
    
    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is -Math.PI/2 which draws text 'vertically' (with the
     * top of the characters on the left).
     *
     * @param text  the text.
     * @param canvas  the graphics device.
     * @param paint  the paint.
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    public static void drawRotatedString(String text, Canvas canvas, 
            Paint paint, double angle, float x, float y) {
        drawRotatedString(text, canvas, paint, x, y, angle, x, y);
    }

    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is -Math.PI/2 which draws text 'vertically' (with the
     * top of the characters on the left).
     *
     * @param text  the text.
     * @param canvas  the graphics device.
     * @param paint  the paint.
     * @param textX  the x-coordinate for the text (before rotation).
     * @param textY  the y-coordinate for the text (before rotation).
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param rotateX  the point about which the text is rotated.
     * @param rotateY  the point about which the text is rotated.
     */
    public static void drawRotatedString(String text, Canvas canvas, 
            Paint paint, float textX, float textY, double angle,
            float rotateX, float rotateY) {

        if ((text == null) || (text.equals(""))) {
            return;
        }
        double degrees = angle * 180.0 / Math.PI;
        canvas.rotate((float) degrees, rotateX, rotateY);
        canvas.drawText(text, textX, textY, paint);
        canvas.rotate((float) -degrees, rotateX, rotateY);
    }
}


