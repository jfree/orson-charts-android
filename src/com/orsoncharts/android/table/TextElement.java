/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.TextAnchor;
import com.orsoncharts.android.util.TextUtils;

/**
 * A table element consisting of some text that will be drawn on one line.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class TextElement extends AbstractTableElement 
        implements TableElement, Serializable {

    /** The text (never <code>null</code>). */
    private String text;
    
    /** The font (never <code>null</code>). */
    private TextStyle font;
    
    /** The horizontal alignment (never <code>null</code>). */
    private HAlign alignment;
   
    /**
     * Creates a new instance.
     * 
     * @param text  the text (<code>null</code> not permitted).
     */
    public TextElement(String text) {
        this(text, new TextStyle(Typeface.SANS_SERIF, 12));
    }
    
    /**
     * Creates a new instance.
     * 
     * @param text  the text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     */
    public TextElement(String text, TextStyle font) {
        super();
        ArgChecks.nullNotPermitted(text, "text");
        ArgChecks.nullNotPermitted(font, "font");
        this.text = text;
        this.font = font;
        this.alignment = HAlign.LEFT;
    }
    
    /**
     * Returns the font.  The default is 
     * <code>Font("Dialog", Font.PLAIN, 12)</code>.
     * 
     * @return The font (never <code>null</code>). 
     */
    public TextStyle getFont() {
        return this.font;
    }
    
    /**
     * Sets the font.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    public void setFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.font = font;
    }
    
    /**
     * Returns the horizontal alignment.  The default value is 
     * <code>LEFT</code>.
     * 
     * @return The horizontal alignment (never <code>null</code>). 
     */
    public HAlign getHorizontalAlignment() {
        return this.alignment;
    }
    
    /**
     * Sets the horizontal alignment.
     * 
     * @param align  the alignment (<code>null</code> not permitted). 
     */
    public void setHorizontalAligment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.alignment = align;
    }

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    @Override
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        this.font.applyToPaint(paint);
        RectF textBounds = TextUtils.getTextBounds(this.text, paint);
        Insets insets = getInsets();
        float w = Math.min(textBounds.width() + insets.left + insets.right, 
                bounds.width());
        float h = Math.min(textBounds.height() + insets.top + insets.bottom,
                bounds.height());
        return new ElementDimension(w, h);
    }

    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    @Override
    public List<RectF> layoutElements(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        this.font.applyToPaint(paint);
        RectF textBounds = TextUtils.getTextBounds(this.text, paint);
        Insets insets = getInsets();
        float width = textBounds.width() + insets.left + insets.right;
        float x = bounds.left;
        switch (this.alignment) {
            case LEFT: 
                x = bounds.left;
                break;
            case CENTER:
                x = bounds.centerX() - width / 2.0f - insets.left;
                break;
            case RIGHT:
                x = bounds.right - width - insets.right;
                break;
            default: 
                throw new IllegalStateException("HAlign: " + this.alignment);
        }
        float y = bounds.top;
        float w = Math.min(width, bounds.width());
        float h = Math.min(textBounds.height() + insets.top + insets.bottom,
                bounds.height());
        List<RectF> result = new ArrayList<RectF>(1);        
        result.add(new RectF(x, y, x + w, y + h));
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param canvas  the graphics target.
     * @param paint  the paint.
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        this.font.applyToPaint(paint);
        List<RectF> layout = layoutElements(canvas, paint, bounds, null);
        RectF textBounds = layout.get(0);
        paint.setColor(getBackgroundPaint());
        paint.setStyle(Style.FILL);
        canvas.drawRect(textBounds, paint);
        paint.setColor(getForegroundPaint());
        Insets insets = getInsets();
        TextUtils.drawAlignedString(this.text, canvas, paint,
                textBounds.left + insets.left, 
                textBounds.top + insets.top, TextAnchor.TOP_LEFT);
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextElement)) {
            return false;
        }
        TextElement that = (TextElement) obj;
        if (!this.text.equals(that.text)) {
            return false;
        }
        if (!this.font.equals(that.font)) {
            return false;
        }
        if (this.alignment != that.alignment) {
            return false;
        }
        return super.equals(obj);
    }
    
}
