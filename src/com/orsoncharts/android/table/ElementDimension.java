/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.io.Serializable;

import com.orsoncharts.android.graphics3d.Dimension2D;

/**
 * An element dimension (in fact a simple implementation of the 
 * <code>Dimension2D</code> interface).
 */
public final class ElementDimension extends Dimension2D 
        implements Serializable {
  
    /**
     * Creates a new dimension object.
     * 
     * @param width  the width.
     * @param height  the height.
     */
    public ElementDimension(float width, float height) {
        super(width, height);
    }
    
    
}
