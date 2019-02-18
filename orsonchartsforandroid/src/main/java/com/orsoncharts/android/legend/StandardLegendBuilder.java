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

package com.orsoncharts.android.legend;

import java.io.Serializable;
import java.util.List;

import android.graphics.Typeface;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;

import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.PiePlot3D;
import com.orsoncharts.android.plot.Plot3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.table.ContainerElement;
import com.orsoncharts.android.table.FlowElement;
import com.orsoncharts.android.table.GridElement;
import com.orsoncharts.android.table.HAlign;
import com.orsoncharts.android.table.ShapeElement;
import com.orsoncharts.android.table.TableElement;
import com.orsoncharts.android.table.TextElement;
import com.orsoncharts.android.table.VAlign;
import com.orsoncharts.android.table.VerticalFlowElement;
import com.orsoncharts.android.util.Anchor2D;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.ObjectUtils;
import com.orsoncharts.android.util.Orientation;

/**
 * The standard legend builder, which creates a simple legend
 * with a flow layout and optional header and footer text.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class StandardLegendBuilder implements LegendBuilder, 
        Serializable {

    /** The default header font. */
    public static final TextStyle DEFAULT_HEADER_FONT = new TextStyle(
            Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 14);
    
    /** The default footer font. */
    public static final TextStyle DEFAULT_FOOTER_FONT = new TextStyle(
            Typeface.SANS_SERIF, 10);
    
    /** The default font for legend items. */
    public static final TextStyle DEFAULT_ITEM_FONT = new TextStyle(
            Typeface.SANS_SERIF, 12);
    
    /** An optional header/title for the legend (can be {@code null}). */
    private String header;
    
    /** The header font (never {@code null}). */
    private TextStyle headerFont;
    
    /** The header alignment (never {@code null}). */
    private HAlign headerAlignment;
    
    /** An optional footer for the legend (can be {@code null}). */
    private String footer;
    
    /** The footer font (never {@code null}). */
    private TextStyle footerFont;
    
    /** The footer alignment (never {@code null}). */
    private HAlign footerAlignment;
    
    /** The font used for legend items. */
    private TextStyle itemFont;
    
    /**
     * The row alignment (if {@code null}, the row alignment will be
     * derived from the anchor point).
     */
    private HAlign rowAlignment;
    
    /**
     * The column alignment (if {@code null}, the column alignment will
     * be derived from the anchor point).
     */
    private VAlign columnAlignment;
    
    /**
     * Creates a builder for a simple legend with no header and no footer.
     */
    public StandardLegendBuilder() {
        this(null, null);
    }
    
    /**
     * Creates a builder for a simple legend with the specified header and/or
     * footer.
     * 
     * @param header  the legend header ({@code null} permitted).
     * @param footer  the legend footer ({@code null} permitted).
     */
    public StandardLegendBuilder(String header, String footer) {
        this.header = header;
        this.headerFont = DEFAULT_HEADER_FONT;
        this.headerAlignment = HAlign.LEFT;
        this.footer = footer;
        this.footerFont = DEFAULT_FOOTER_FONT;
        this.footerAlignment = HAlign.RIGHT;
        this.itemFont = DEFAULT_ITEM_FONT;
        this.rowAlignment = null;
        this.columnAlignment = null;
    }
    
    /**
     * Returns the header text.
     * 
     * @return The header text (possibly {@code null}).
     */
    public String getHeader() {
        return this.header;
    }
    
    /**
     * Sets the header text.
     * 
     * @param header  the header ({@code null} permitted).
     */
    public void setHeader(String header) {
        this.header = header;
    }
    
    /**
     * Returns the header font.
     * 
     * @return The header font (never {@code null}).
     */
    public TextStyle getHeaderFont() {
        return this.headerFont;
    }
    
    /**
     * Sets the header font.
     * 
     * @param font  the header font ({@code null} not permitted).
     */
    public void setHeaderFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.headerFont = font;
    }
    
    /**
     * Returns the header alignment.
     * 
     * @return The header alignment (never {@code null}).
     */
    public HAlign getHeaderAlignment() {
        return this.headerAlignment;
    }
    
    /**
     * Sets the header alignment.
     * 
     * @param align  the header alignment ({@code null} not permitted).
     */
    public void setHeaderAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.headerAlignment = align;
    }
    
    /**
     * Returns the footer text.
     * 
     * @return The footer text (possibly {@code null}).
     */
    public String getFooter() {
        return this.footer;
    }
    
    /**
     * Sets the footer text.
     * 
     * @param footer  the footer ({@code null} permitted).
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }
    
    /**
     * Returns the footer font.
     * 
     * @return The footer font (never {@code null}).
     */
    public TextStyle getFooterFont() {
        return this.footerFont;
    }
    
    /**
     * Sets the footer font.
     * 
     * @param font  the footer font ({@code null} not permitted).
     */
    public void setFooterFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.footerFont = font;
    }
    
    /**
     * Returns the footer alignment.
     * 
     * @return The footer alignment (never {@code null}).
     */
    public HAlign getFooterAlignment() {
        return this.footerAlignment;
    }
    
    /**
     * Sets the footer alignment.
     * 
     * @param align  the footer alignment ({@code null} not permitted).
     */
    public void setFooterAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.footerAlignment = align;
    }
    
    /**
     * Returns the font used for each item within the legend (the default value
     * is {@link #DEFAULT_ITEM_FONT}).
     * 
     * @return The item font (never {@code null}).
     */
    @Override
    public TextStyle getItemFont() {
        return this.itemFont;
    }
    
    /**
     * Sets the font used for each item within the legend.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    @Override
    public void setItemFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.itemFont = font;
    }
    
    /**
     * Returns the row alignment.  The default value is {@code null}
     * which means that the row alignment is derived from the anchor point 
     * (left aligned for anchors on the left side, center alignment for 
     * anchors in the middle, and right aligned for anchors on the right side).
     * 
     * @return The row alignment (possibly {@code null}).
     * 
     * @since 1.1
     */
    public HAlign getRowAlignment() {
        return this.rowAlignment;
    }
    
    /**
     * Sets the row alignment (to override the default alignment that is
     * derived from the legend anchor point).  In most circumstances you 
     * should be able to rely on the default behaviour, so leave this
     * attribute set to {@code null}.
     * 
     * @param alignment  the row alignment ({@code null} permitted).
     * 
     * @since 1.1
     */
    public void setRowAlignment(HAlign alignment) {
        this.rowAlignment = alignment;    
    }
    
    /**
     * Returns the column alignment.  The default value is {@code null}
     * which means that the column alignment is derived from the anchor point 
     * (top aligned for anchors at the top, center alignment for 
     * anchors in the middle, and bottom aligned for anchors at the bottom).
     * 
     * @return The column alignment (possibly {@code null}).
     * 
     * @since 1.1
     */
    public VAlign getColumnAlignment() {
        return this.columnAlignment;
    }
    
    /**
     * Sets the column alignment (to override the default alignment that is
     * derived from the legend anchor point).  In most circumstances you 
     * should be able to rely on the default behaviour, so leave this
     * attribute set to {@code null}.
     * 
     * @param alignment  the column alignment ({@code null} permitted).
     * 
     * @since 1.1
     */
    public void setColumnAlignment(VAlign alignment) {
        this.columnAlignment = alignment;
    }
    
    /**
     * Creates and returns a legend (instance of {@link TableElement}) that
     * provides a visual key for the data series in the specified plot.  The
     * plot can be any of the built-in plot types: {@link PiePlot3D}, 
     * {@link CategoryPlot3D} or {@link XYZPlot}.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @return The legend. 
     */
    @Override
    public TableElement createLegend(Plot3D plot, Anchor2D anchor,
            Orientation orientation) {
        TableElement legend = createSimpleLegend(plot.getLegendInfo(), anchor,
                orientation);
        if (this.header != null || this.footer != null) {
            GridElement compositeLegend = new GridElement();
            if (this.header != null) {
                TextElement he = new TextElement(this.header, this.headerFont);
                he.setHorizontalAligment(this.headerAlignment);
                compositeLegend.setElement(he, "R0", "C1");                
            }
            compositeLegend.setElement(legend, "R1", "C1");
            if (this.footer != null) {
                TextElement fe = new TextElement(this.footer, this.footerFont);
                fe.setHorizontalAligment(this.footerAlignment);
                compositeLegend.setElement(fe, "R2", "C1");
            }
            return compositeLegend;
        } else {
            return legend;
        }
    }
    
    /**
     * Creates a simple legend based on a horizontal flow layout of the 
     * individual legend items.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor point ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @return The simple legend. 
     */
    private TableElement createSimpleLegend(List<LegendItemInfo> items,
            Anchor2D anchor, Orientation orientation) {
        ArgChecks.nullNotPermitted(items, "items");
        ArgChecks.nullNotPermitted(orientation, "orientation");
        ContainerElement legend;
        if (orientation == Orientation.HORIZONTAL) {
            FlowElement fe = new FlowElement(horizontalAlignment(anchor), 2);
            fe.setRefPoint(anchor.getRefPt());
            legend = fe;
        } else {
            VerticalFlowElement vfe = new VerticalFlowElement(
                    verticalAlignment(anchor), 2); 
            vfe.setRefPoint(anchor.getRefPt());
            legend = vfe;        
        }
        for (LegendItemInfo item : items) {
            Shape shape = item.getShape();
            if (shape == null) {
                shape = new RectShape();
                shape.resize(16, 8);
            }
            legend.addElement(createLegendItem(item.getLabel(), this.itemFont,
                    shape, item.getPaint()));
        }
        return legend;
    }
    
    /**
     * Returns the horizontal alignment that should be used.
     * 
     * @param anchor  the anchor ({@code null} not permitted).
     * 
     * @return The horizontal alignment. 
     */
    private HAlign horizontalAlignment(Anchor2D anchor) {
        if (this.rowAlignment != null) {
            return this.rowAlignment;
        }
        if (anchor.getRefPt().isLeft()) {
            return HAlign.LEFT;
        }
        if (anchor.getRefPt().isRight()) {
            return HAlign.RIGHT;
        }
        return HAlign.CENTER;
    }
    
    /**
     * Returns the vertical alignment that should be used.
     * 
     * @param anchor  the anchor ({@code null} not permitted).
     * 
     * @return The vertical alignment. 
     */
    private VAlign verticalAlignment(Anchor2D anchor) {
        if (this.columnAlignment != null) {
            return this.columnAlignment;
        }
        if (anchor.getRefPt().isTop()) {
            return VAlign.TOP;
        }
        if (anchor.getRefPt().isBottom()) {
            return VAlign.BOTTOM;
        }
        return VAlign.MIDDLE;
    }
    
    /**
     * Creates a single item in the legend (normally this represents one
     * data series from the dataset).
     * 
     * @param text  the legend item text ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param color  the color ({@code null} not permitted).
     * 
     * @return A legend item (never {@code null}).
     */
    private TableElement createLegendItem(String text, TextStyle font, Shape shape, 
            int color) {
        // defer argument checks...
        ShapeElement se = new ShapeElement(shape, color);
        //se.setBackgroundPaint(new Color(0, 0, 0, 0));
        TextElement te = new TextElement(text, font);
        //te.setBackgroundPaint(new Color(0, 0, 0, 0));
        GridElement ge = new GridElement();
        ge.setElement(se, "R1", "C1");
        ge.setElement(te, "R1", "C2");
        return ge;
    }
    
    /**
     * Tests this legend builder for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardLegendBuilder)) {
            return false;
        }
        StandardLegendBuilder that = (StandardLegendBuilder) obj;
        if (!ObjectUtils.equals(this.header, that.header)) {
            return false;
        }
        if (this.headerAlignment != that.headerAlignment) {
            return false;
        }
        if (!this.headerFont.equals(that.headerFont)) {
            return false;
        }        
        if (!ObjectUtils.equals(this.footer, that.footer)) {
            return false;
        }
        if (this.footerAlignment != that.footerAlignment) {
            return false;
        }
        if (!this.footerFont.equals(that.footerFont)) {
            return false;
        }
        if (!this.itemFont.equals(that.itemFont)) {
            return false;
        }
        return true;
    }

}
