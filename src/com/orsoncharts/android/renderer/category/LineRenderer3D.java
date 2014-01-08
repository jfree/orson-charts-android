/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.android.renderer.category;

import java.io.Serializable;

import android.graphics.Color;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DFactory;
import com.orsoncharts.android.Range;
import com.orsoncharts.android.axis.CategoryAxis3D;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.Object3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.renderer.Renderer3DChangeEvent;
import com.orsoncharts.android.util.ObjectUtils;

/**
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D lines charts from data in a {@link CategoryDataset3D}.  The 
 * <code>createLineChart()</code> method in the {@link Chart3DFactory} class 
 * will construct a chart that uses this renderer.  Here is a sample:
 * <div>
 * <object id="LineChart3DDemo1" data="../../../../doc-files/LineChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to <code>LineChart3DDemo1.java</code> for the code to generate the
 * above chart).
 * <br><br>
 * Some attributes in the renderer are specified in "world units" - see the
 * {@link Chart3D} class description for more information about world units.
 * <br><br>
 * There is a factory method to create a chart using this renderer - see
 * {@link Chart3DFactory#createLineChart(String, String, CategoryDataset3D, String, String, String)}.
 * <br><br> 
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class LineRenderer3D extends AbstractCategoryRenderer3D 
        implements Serializable {

    /** The line width (in world units). */
    private double lineWidth;
    
    /** The line height (in world units). */
    private double lineHeight;

    /**
     * The color source that determines the color used to highlight clipped
     * items in the chart.
     */
    private CategoryColorSource clipColorSource;
    
    /**
     * Creates a new instance with default attribute values.
     */
    public LineRenderer3D() {
        this.lineWidth = 0.4;
        this.lineHeight = 0.2;
        this.clipColorSource = new StandardCategoryColorSource(Color.RED);
    }
    
    /**
     * Returns the line width in world units.  The default value is 
     * <code>0.4</code>.
     * 
     * @return The line width in world units. 
     */
    public double getLineWidth() {
        return this.lineWidth;
    }
    
    /**
     * Sets the line width (in world units) and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param width  the width (in world units). 
     */
    public void setLineWidth(double width) {
        this.lineWidth = width;
        fireChangeEvent();
    }

    /**
     * Returns the line height in world units.  The default value is 
     * <code>0.2</code>.
     * 
     * @return The line height in world units. 
     */
    public double getLineHeight() {
        return this.lineHeight;
    }
    
    /**
     * Sets the line height (in world units) and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param height  the height (in world units). 
     */
    public void setLineHeight(double height) {
        this.lineHeight = height;
        fireChangeEvent();
    }

    
    /**
     * Returns the color source used to determine the color used to highlight
     * clipping in the chart elements.  If the source is <code>null</code>,
     * then the regular series color is used instead.
     * 
     * @return The color source (possibly <code>null</code>). 
     */
    public CategoryColorSource getClipColorSource() {
        return this.clipColorSource;
    }
    
    /**
     * Sets the color source that determines the color used to highlight
     * clipping in the chart elements, and sends a {@link Renderer3DChangeEvent}
     * to all registered listeners.
     * 
     * @param source  the source (<code>null</code> permitted). 
     */
    public void setClipColorSource(CategoryColorSource source) {
        this.clipColorSource = source;
        fireChangeEvent();
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the plot dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        
        // there is a lot of brute force code underneath this compose method
        // because I haven't seen the pattern yet that will let me reduce it
        // to something more elegant...probably I'm not smart enough.
        double value = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value)) {
            return;
        }

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        ValueAxis3D valueAxis = plot.getValueAxis();
        Range r = valueAxis.getRange();
        
        Comparable<?> rowKey = dataset.getRowKey(row);
        Comparable<?> columnKey = dataset.getColumnKey(column);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double columnValue = columnAxis.getCategoryValue(columnKey);
        double ww = dimensions.getWidth();
        double hh = dimensions.getHeight();
        double dd = dimensions.getDepth();

        // for all but the last item, we will add a segment extending from the
        // current data value to the next data value
        if (column < dataset.getColumnCount() - 1) {
            double wdelta = this.lineWidth / 2.0;
            double x0 = columnAxis.translateToWorld(columnValue, ww) + xOffset;
            double y0 = valueAxis.translateToWorld(value, hh) + yOffset;
            double z0 = rowAxis.translateToWorld(rowValue, dd) + zOffset;
            double zf = z0 - wdelta;
            double zb = z0 + wdelta;
            double wmin = valueAxis.translateToWorld(r.getMin(), hh) + yOffset;
            double wmax = valueAxis.translateToWorld(r.getMax(), hh) + yOffset;
    
            Comparable<?> nextColumnKey = dataset.getColumnKey(column + 1);
            double nextColumnValue = columnAxis.getCategoryValue(nextColumnKey);
            double x1 = columnAxis.translateToWorld(nextColumnValue, ww) 
                    + xOffset;
            double value1 = dataset.getDoubleValue(series, row, column + 1);
            double y1 = valueAxis.translateToWorld(value1, hh) + yOffset;
            
            int color = getColorSource().getColor(series, row, column);
            int clipColor = color;  
            if (getClipColorSource() != null) {
                Integer c = getClipColorSource().getColor(series, row, column);
                if (c != null) {
                    clipColor = c;
                }
            }
            boolean closingFace = column == dataset.getColumnCount() - 2;
            boolean openingFace = column == 0;
            // create a line shape - this is complex because of the polygon
            // clipping that is necessary when the axis range is limited
            // there might be a more elegant way to do it of course
            Object3D obj = new Object3D();
            if (this.lineHeight > 0.0) {
                double hdelta = this.lineHeight / 2.0;
                double y0b = y0 - hdelta;
                double y0t = y0 + hdelta;
                double y1b = y1 - hdelta;
                double y1t = y1 + hdelta;
                double[] xpts = calcCrossPoints(x0, x1, y0b, y0t, y1b, y1t, 
                        wmin, wmax);
                Object3D seg = null;
                if (y0b >= wmax) {  // CASE A 
                    seg = createSegmentA(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, false, 
                            closingFace);
                } else if (y0t > wmax && y0b > wmin) {  // CASE B
                    seg = createSegmentB(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, openingFace, 
                            closingFace);
                } else if (y0t > wmax && y0b <= wmin) {  // CASE C
                     seg = createSegmentC(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, openingFace, 
                            closingFace);
                } else if (y0t > wmin && y0b >= wmin) { // CASE D
                    seg = createSegmentD(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, openingFace, 
                            closingFace);                    
                } else if (y0t > wmin && y0b < wmin) { // CASE E
                    seg = createSegmentE(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, openingFace, 
                            closingFace);                    
                } else if (y0t <= wmin) {  // CASE F
                     seg = createSegmentF(x0, x1, xpts, y0b, y0t, y1b, y1t, 
                            wmin, wmax, zf, zb, color, clipColor, false, 
                            closingFace);                   
                }
                if (seg != null) {
                    world.add(seg);
                }
            } else {
                // ribbon
                obj.addVertex(x0, y0, z0 - wdelta);
                obj.addVertex(x0, y0, z0 + wdelta);
                obj.addVertex(x1, y1, z0 + wdelta);
                obj.addVertex(x1, y1, z0 - wdelta);
                obj.addFace(new int[] {0, 1, 2, 3}, color, true);
                obj.addFace(new int[] {3, 2, 1, 0}, color, true);
            }
            world.add(obj);
        } 
    }

    /**
     * Calculates the four intersection points between two horizontal lines
     * (ymin and ymax) and the lines (x0, y0b, x1, y1b) and (x0, y1t, x1, y1t)
     * and returns the x-coordinates in an array.
     * 
     * @param x0
     * @param x1
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param ymin
     * @param ymax
     * 
     * @return An array of 4 x-coordinates. 
     */
    private double[] calcCrossPoints(double x0, double x1, double y0b, 
            double y0t, double y1b, double y1t, double ymin, double ymax) {
        double[] xpts = new double[] { Double.NaN, Double.NaN, Double.NaN, 
            Double.NaN };
        double factor = (y0b - ymin) / (y0b - y1b);
        xpts[0] = x0 + factor * (x1 - x0);
        factor = (y0t - ymin) / (y0t - y1t);
        xpts[1] = x0 + factor * (x1 - x0);
        factor = (y0b - ymax) / (y0b - y1b);
        xpts[2] = x0 + factor * (x1 - x0);
        factor = (y0t - ymax) / (y0t - y1t);
        xpts[3] = x0 + factor * (x1 - x0);
        return xpts;
    }
    
    /**
     * Creates a segment for the case where the start of the segment is 
     * completely above the upper bound of the axis at the left side of the
     * chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace  ignored because there is no opening face for this
     *     case.
     * @param closingFace
     * 
     * @return A segment (<code>null</code> if the segment is entirely clipped). 
     */
    private Object3D createSegmentA(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {
        if (y1b > wmax) {
            return null;  // nothing is visible
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                // create a triangle with the top and right
                Object3D seg = new Object3D();
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4}, color, true); // front
                seg.addFace(new int[] {1, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 1, 0}, color, true); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip bottom
                seg.addFace(new int[] {6, 7, 1, 0}, color, true); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4}, color, true);
                }
                return seg;
            }
        } else if (y1t >= wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, clipColor, true); // clip top
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {6, 7, 1, 0}, color, true); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true);
                seg.addFace(new int[] {0, 1, 3, 2}, clipColor, true); // clip top
                seg.addFace(new int[] {6, 7, 9, 8}, clipColor, true); // clip bottom
                seg.addFace(new int[] {8, 9, 1, 0}, color, true);
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            }
        } else {
            Object3D seg = new Object3D();
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
            seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
            seg.addFace(new int[] {4, 2, 3, 5}, color, true); // top
            seg.addFace(new int[] {0, 6, 7, 1}, color, true); // bottom
            seg.addFace(new int[] {0, 1, 3, 2}, clipColor, true); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip bottom
            // there are no opening or closing faces in this case
            return seg;
        }
    }
    
    /**
     * Creates a segment for the case where the left end of the line spans
     * the axis maximum on the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentB(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {
        
        if (y1b >= wmax) {
            Object3D seg = new Object3D();
            seg.addVertex(x0, y0b, zf);
            seg.addVertex(x0, y0b, zb);
            seg.addVertex(x0, wmax, zf);
            seg.addVertex(x0, wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addFace(new int[] {0, 2, 4}, color, true); // front
            seg.addFace(new int[] {1, 5, 3}, color, true);  // rear
            seg.addFace(new int[] {0, 4, 5, 1}, color, true); // bottom
            seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2}, color, true); 
            }
            // there is no closing face in this case
            return seg; 
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true);  // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {0, 6, 7, 1}, color, true); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true);  // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {8, 6, 7, 9}, clipColor, true); // clip bottom
                seg.addFace(new int[] {0, 8, 9, 1}, color, true);
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, color, true); // top
                seg.addFace(new int[] {0, 8, 9, 1}, color, true); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8, 10}, color, true); // front
                seg.addFace(new int[] {1, 11, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, color, true); // top
                seg.addFace(new int[] {8, 9, 11, 10}, clipColor, true); // clip bottom
                seg.addFace(new int[] {10, 11, 1, 0}, color, true); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true);                    
                }
                return seg;
            }
        }
        Object3D seg = new Object3D();
        seg.addVertex(x0, y0b, zf);
        seg.addVertex(x0, y0b, zb);
        seg.addVertex(x0, wmax, zf);
        seg.addVertex(x0, wmax, zb);
        seg.addVertex(xpts[3], wmax, zf);
        seg.addVertex(xpts[3], wmax, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addVertex(xpts[0], wmin, zf);
        seg.addVertex(xpts[0], wmin, zb);
        seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
        seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
        seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
        seg.addFace(new int[] {4, 5, 7, 6}, color, true); // top
        seg.addFace(new int[] {6, 7, 9, 8}, clipColor, true); // clip bottom
        seg.addFace(new int[] {8, 9, 1, 0}, color, true); // bottom
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2}, color, true);
        }
        // there is no closing face in this case
        return seg;
    }

    /**
     * Creates a segment for the case where the line end spans the entire axis
     * range at the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentC(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {

        // the first 4 vertices and the opening face are common to all 
        // segments in this case
        Object3D seg = new Object3D();
        seg.addVertex(x0, wmin, zf);
        seg.addVertex(x0, wmin, zb);
        seg.addVertex(x0, wmax, zf);
        seg.addVertex(x0, wmax, zb);
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2}, color, true);
        }
        
        if (y1b >= wmax) {
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
            seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
            seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}, color, true); // bottom
            seg.addFace(new int[] {7, 1, 0, 6}, clipColor, true); // bottom clip
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // top clip
                seg.addFace(new int[] {6, 7, 9, 8}, color, true); // bottom
                seg.addFace(new int[] {8, 9, 1, 0}, clipColor, true); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, color, true); // bottom
                seg.addFace(new int[] {7, 1, 0, 6}, clipColor, true); // bottom clip
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                return null; // in practice I don't think this case
                             // can occur
            } else {
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, color, true); // top
                seg.addFace(new int[] {9, 1, 0, 8}, clipColor, true); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true);
                }
                return seg; 
            }
        }
        seg.addVertex(xpts[3], wmax, zf);
        seg.addVertex(xpts[3], wmax, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
        seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
        seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
        seg.addFace(new int[] {4, 5, 7, 6}, color, true); // top
        seg.addFace(new int[] {6, 7, 1, 0}, clipColor, true); // clip bottom
        return seg;
    }
    
    /**
     * Creates a segment for the case where the segment is contained within
     * the axis range at the left side.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentD(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {

        Object3D seg = new Object3D();
        seg.addVertex(x0, y0b, zf);
        seg.addVertex(x0, y0b, zb);
        seg.addVertex(x0, y0t, zf);
        seg.addVertex(x0, y0t, zb);
        if (y1b >= wmax) {
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addFace(new int[] {0, 2, 4, 6}, color, true);  // front
            seg.addFace(new int[] {1, 7, 5, 3}, color, true);  // rear
            seg.addFace(new int[] {2, 3, 5, 4}, color, true);  // top
            seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip top
            seg.addFace(new int[] {0, 6, 7, 1}, color, true); // bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2}, color, true);
            }
            // there is no closing face in this case
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip top
                seg.addFace(new int[] {0, 8, 9, 1}, color, true);
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true);
                }
                return seg;  
            } else {
                return null;  // this case should not be possible
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                // this is the regular segment, no clipping
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true);  // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true);  // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true);  // top
                seg.addFace(new int[] {0, 6, 7, 1}, color, true);  // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {0, 8, 9, 1}, color, true);  // bottom
                seg.addFace(new int[] {6, 7, 9, 8}, clipColor, true); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            }
        } else {
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
            seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
            seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
            seg.addFace(new int[] {0, 6, 7, 1}, color, true); // bottom
            seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2}, color, true);
            }
            // there is no closing face in this case
            return seg;
        }
    }
    
    /**
     * Returns a segment for the case where the line height spans the lower 
     * bound of the axis range at the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return The segment. 
     */
    private Object3D createSegmentE(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {
        if (y1b > wmax) {
            Object3D seg = new Object3D();
            seg.addVertex(x0, wmin, zf);
            seg.addVertex(x0, wmin, zb);
            seg.addVertex(x0, y0t, zf);
            seg.addVertex(x0, y0t, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
            seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
            seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
            seg.addFace(new int[] {4, 5, 7, 6}, clipColor, true); // clip top
            seg.addFace(new int[] {6, 7, 9, 8}, color, true); // bottom
            seg.addFace(new int[] {0, 8, 9, 1}, clipColor, true); // clip bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2}, color, true);
            }
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8, 10}, color, true); // front
                seg.addFace(new int[] {1, 11, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {5, 7, 6, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {8, 9, 11, 10}, color, true); // bottom
                seg.addFace(new int[] {1, 0, 10, 11}, clipColor, true);
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true); 
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true); 
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {5, 7, 6, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {0, 8, 9, 1}, clipColor, true); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true); 
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}, color, true); 
                }
                return seg;  
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(x1, y1t, zf);                
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);                
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {6, 7, 9, 8}, color, true); // bottom
                seg.addFace(new int[] {0, 8, 9, 1}, clipColor, true); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(x1, y1t, zf);                
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
                seg.addFace(new int[] {0, 6, 7, 1}, clipColor, true); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            }
        }
        Object3D seg = new Object3D();
        seg.addVertex(x0, wmin, zf);
        seg.addVertex(x0, wmin, zb);
        seg.addVertex(x0, y0t, zf);
        seg.addVertex(x0, y0t, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addFace(new int[] {0, 2, 4}, color, true); // front
        seg.addFace(new int[] {1, 5, 3}, color, true); // rear
        seg.addFace(new int[] {2, 3, 5, 4}, color, true); // top
        seg.addFace(new int[] {0, 4, 5, 1}, clipColor, true); // clip bottom
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2}, color, true);
        }
        // there is no closing face in this case
        return seg;
    }
    
    /**
     * Creates and returns a segment for the case where the line is completely
     * below the axis range at the left side.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace  ignored because there is no opening face in this 
     *     case.
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentF(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, int color, int clipColor, 
            boolean openingFace, boolean closingFace) {

        if (y1b > wmax) {
            Object3D seg = new Object3D();
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
            seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
            seg.addFace(new int[] {0, 1, 3, 2}, color, true); // top
            seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}, color, true); // bottom
            seg.addFace(new int[] {0, 6, 7, 1}, clipColor, true); // clip bottom
            // there are no opening and closing faces for this case
            return seg;
        }
        if (y1t > wmax) {
            if (y1b > wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}, color, true); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); //clip top
                seg.addFace(new int[] {0, 1, 3, 2}, color, true); // top
                seg.addFace(new int[] {0, 8, 9, 1}, clipColor, true); // clip bottom
                seg.addFace(new int[] {6, 7, 9, 8}, color, true); // bottom
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, color, true); // top
                seg.addFace(new int[] {2, 3, 5, 4}, clipColor, true); // clip top
                seg.addFace(new int[] {6, 7, 1, 0}, clipColor, true); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6}, color, true);
                }
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);                
                seg.addFace(new int[] {0, 2, 4, 6}, color, true); // front
                seg.addFace(new int[] {1, 7, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, color, true); // top
                seg.addFace(new int[] {4, 5, 7, 6}, color, true); // bottom
                seg.addFace(new int[] {0, 6, 7, 1}, clipColor, true); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4}, color, true);
                }
                return seg;
            } else {
                Object3D seg = new Object3D();
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4}, color, true); // front
                seg.addFace(new int[] {1, 5, 3}, color, true); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, color, true); // top
                seg.addFace(new int[] {0, 4, 5, 1}, clipColor, true); // clip bottom
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4}, color, true);
                }
                return seg;
            }
        }
        return null;  // nothing to see
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LineRenderer3D)) {
            return false;
        }
        LineRenderer3D that = (LineRenderer3D) obj;
        if (this.lineWidth != that.lineWidth) {
            return false;
        }
        if (this.lineHeight != that.lineHeight) {
            return false;
        }
        if (!ObjectUtils.equals(this.clipColorSource, that.clipColorSource)) {
            return false;
        }
        return super.equals(obj);
    }
}

