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

package com.orsoncharts.android.table;

/**
 * A {@link TableElement} that contains other elements (provides the
 * <code>addElement()</code> method).
 * 
 * @since 1.1
 */
public interface ContainerElement extends TableElement {
    
    /**
     * Adds a sub-element to the container element.
     * 
     * @param element  the element (<code>null</code> not permitted). 
     */
    void addElement(TableElement element);
    
}
