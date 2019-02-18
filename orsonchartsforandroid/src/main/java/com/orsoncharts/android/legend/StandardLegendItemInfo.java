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

import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.shapes.Shape;

import com.orsoncharts.android.util.ArgChecks;

/**
 * A standard implementation of the {@link LegendItemInfo} interface.
 */
public class StandardLegendItemInfo implements LegendItemInfo {

    /** The series key. */
    private Comparable<?> seriesKey;
    
    /** The series label. */
    private String label;
    
    /** A description of the item. */
    private String description;
    
    /** The color to represent this legend item. */
    private int paint;
    
    /** The shape to represent this legend item. */
    private Shape shape;
    
    /** Storage for other properties. */
    private Map<Comparable<?>, Object> properties;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param label  the label ({@code null} not permitted).
     * @param paint  the paint.
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            int paint) {
        this(seriesKey, label, null, paint, null);
    }
    
    /**
     * Creates a new instance with the specified attributes.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param paint  the paint.
     * @param shape the shape ({@code null} permitted).
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            String description, int paint, Shape shape) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(label, "label");
        ArgChecks.nullNotPermitted(paint, "paint");
        this.seriesKey = seriesKey;  
        this.label = label;
        this.description = description;
        this.paint = paint;
        this.shape = shape;
        this.properties = new HashMap<Comparable<?>, Object>();
    }
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never {@code null}).
     */
    @Override
    public Comparable<?> getSeriesKey() {
        return this.seriesKey;
    }

    /**
     * Returns the label for the legend item.
     * 
     * @return The label (never {@code null}).
     */
    @Override
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the description for the legend item.
     * 
     * @return The description (possibly {@code null}).
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the shape for the legend item.
     * 
     * @return The shape (possibly {@code null}).
     */
    @Override
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Returns the paint for the legend item.
     * 
     * @return The paint. 
     */
    @Override
    public int getPaint() {
        return this.paint;    
    }

    /**
     * Returns the properties for the legend item.
     * 
     * @return The properties for the legend item. 
     */
    @Override
    public Map<Comparable<?>, Object> getProperties() {
        return this.properties;
    }
    
}
