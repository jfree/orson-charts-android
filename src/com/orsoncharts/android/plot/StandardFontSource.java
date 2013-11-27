/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.plot;

import java.io.Serializable;

import android.graphics.Typeface;

import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A standard implementation of the {@link FontSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class StandardFontSource implements FontSource, Serializable {

    private static TextStyle DEFAULT_FONT = new TextStyle(Typeface.SANS_SERIF, 12);
    
    /** Storage for the fonts assigned to keys. */
    private DefaultKeyedValues<TextStyle> fonts;

    /** The default font (never <code>null</code>). */
    private TextStyle defaultFont;
    
    /**
     * Creates a new instance with default fonts.
     */
    public StandardFontSource() {
        this(DEFAULT_FONT);
    }
    
    /**
     * Creates a new font source with the specified default font.
     * 
     * @param defaultFont  the default font (<code>null</code> not permitted). 
     */
    public StandardFontSource(TextStyle defaultFont) {
        ArgChecks.nullNotPermitted(defaultFont, "defaultFont");
        this.defaultFont = defaultFont;
        this.fonts = new DefaultKeyedValues<TextStyle>();
    }
    
    /**
     * Returns the default font.  The default value is {@link #DEFAULT_FONT}.
     * 
     * @return The default font (never <code>null</code>). 
     */
    public TextStyle getDefaultFont() {
        return this.defaultFont;
    }
    
    /**
     * Sets the default font.
     * 
     * @param font  the font (<code>null</code> not permitted).
     */
    public void setDefaultFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.defaultFont = font;
    }
    
    /**
     * Returns the font for the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The font (never <code>null</code>). 
     */
    @Override
    public TextStyle getFont(Comparable key) {
    	TextStyle result = this.fonts.getValue(key);
        if (result != null) {
            return result;
        } else {
            return this.defaultFont;
        }
    }
    
    /**
     * Sets the font associated with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> permitted).
     */
    @Override
    public void setFont(Comparable key, TextStyle font) {
        if (font != null) {
            this.fonts.put(key, font);
        } else {
            this.fonts.remove(key);
        }
    }
    
    /**
     * Tests this paint source for equality with an arbitrary object.
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
        if (!(obj instanceof StandardFontSource)) {
            return false;
        }
        StandardFontSource that = (StandardFontSource) obj;
        if (!this.defaultFont.equals(that.defaultFont)) {
            return false;
        }
        if (!this.fonts.equals(that.fonts)) {
            return false;
        }
        return true;
    }

}