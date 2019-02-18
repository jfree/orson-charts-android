/* ========================
 * Orson Charts for Android
 * ========================
 *
 * (C)opyright 2013-2019, by Object Refinery Limited.  All rights reserved.
 *
 * https://github.com/jfree/orson-charts-android
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts for Android home page:
 *
 * https://www.object-refinery.com/orsoncharts/android/index.html
 *
 */

package com.orsoncharts.android;

import android.graphics.Color;
import android.graphics.Typeface;

import com.orsoncharts.android.table.GridElement;
import com.orsoncharts.android.table.HAlign;
import com.orsoncharts.android.table.TableElement;
import com.orsoncharts.android.table.TextElement;
import com.orsoncharts.android.util.Anchor2D;

/**
 * Some utility methods for creating chart titles.
 */
public class TitleUtils {
    
    /** The default title font. */
    public static final TextStyle DEFAULT_TITLE_FONT = new TextStyle(
            Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 20);
    
    /** The default foreground paint for titles. */
    public static final int DEFAULT_TITLE_PAINT = Color.BLACK;
    
    /** The default sub-title font. */
    public static final TextStyle DEFAULT_SUBTITLE_FONT = new TextStyle(
            Typeface.SANS_SERIF, 12);
    
    private TitleUtils() {
        // no need to instantiate this class
    }
    
    /**
     * Creates a chart title using the default font and alignment.
     * 
     * @param title  the chart title ({@code null} not permitted).
     * 
     * @return The chart title. 
     */
    public static TableElement createTitle(String title) {
        return createTitle(title, null);    
    }
    
    /**
     * Creates a chart title and subtitle using default fonts and left 
     * alignment.  The <code>subtitle</code> is optional.
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle) {
        return createTitle(title, subtitle, TitleAnchor.TOP_LEFT);
    }
    
    /**
     * Creates a chart title and subtitle (optional) using default fonts and 
     * alignment that is standard for the specified anchor point (that is, left 
     * alignment when the title is anchored left, center alignment when the 
     * title is anchored centrally, and right alignment when the title is 
     * anchored to the right).
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * @param anchor  the anchor point ({@code null} not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle, 
            Anchor2D anchor) {
        HAlign alignment = HAlign.LEFT;
        if (anchor.getRefPt().isHorizontalCenter()) {
            alignment = HAlign.CENTER;
        } else if (anchor.getRefPt().isRight()) {
            alignment = HAlign.RIGHT;
        }
        return createTitle(title, DEFAULT_TITLE_FONT, subtitle, 
                DEFAULT_SUBTITLE_FONT, alignment);
    }
    
    /**
     * Creates a chart title and subtitle using the specified fonts and 
     * alignment.
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param titleFont  the title font ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * @param subtitleFont  the subtitle font ({@code null} permitted).
     * @param alignment  the horizontal alignment ({@code null} not
     *     permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, TextStyle titleFont,
            String subtitle, TextStyle subtitleFont, HAlign alignment) {
        
        TextElement t = new TextElement(title, titleFont);
        t.setHorizontalAligment(alignment);
        t.setForegroundPaint(DEFAULT_TITLE_PAINT);
        if (subtitle == null) {
            return t;
        }
        GridElement compositeTitle = new GridElement();
        TextStyle stf = subtitleFont != null ? subtitleFont 
                : DEFAULT_SUBTITLE_FONT;
        TextElement st = new TextElement(subtitle, stf);
        st.setHorizontalAligment(alignment);
        st.setForegroundPaint(DEFAULT_TITLE_PAINT);
        compositeTitle.setElement(t, "R1", "C1");
        compositeTitle.setElement(st, "R2", "C1");
        return compositeTitle;
    }

}
