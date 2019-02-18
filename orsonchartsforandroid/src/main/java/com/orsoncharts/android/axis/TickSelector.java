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

package com.orsoncharts.android.axis;

import java.text.Format;

/**
 * Provides standard tick sizes and formatting for numerical axes.
 */
public interface TickSelector {
    
    /**
     * Selects a standard tick unit as close as possible to the reference 
     * value.
     * 
     * @param reference  the reference value.
     * 
     * @return The standard tick unit.
     */
    public double select(double reference);
    
    /**
     * Move the cursor to the next (larger) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the largest available.
     * 
     * @return A boolean.
     */
    public boolean next();
    
    /**
     * Move the cursor to the previous (smaller) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the smallest available.
     * 
     * @return A boolean.
     */
    public boolean previous();
    
    /**
     * Returns the tick size that the cursor is currently referencing.
     * 
     * @return The tick size. 
     */
    public double getCurrentTickSize();
    
    /**
     * Returns the tick formatter associated with the tick size that the 
     * cursor is currently referencing.
     * 
     * @return The formatter.
     */
    public Format getCurrentTickLabelFormat();
    
}
