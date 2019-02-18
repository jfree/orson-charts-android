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

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;

/**
 * A {@link TickSelector} implementation that selects tick units in multiples 
 * of 1, 2 and 5.
 */
public class NumberTickSelector implements TickSelector, Serializable {

    private int power = 0;
    
    private int factor = 1;
    
    /** 
     * A flag to track if the units are percentage values, in which case the
     * formatter should display less decimal places.
     */
    private boolean percentage;
    
    /**
     * Creates a new instance.
     */
    public NumberTickSelector() {
        this(false);
    }
    
    /**
     * Creates a new instance, with the option to display the tick values as
     * percentages.  The axis follows the normal convention that values in the
     * range 0.0 to 1.0 a represented as 0% to 100%.
     * 
     * @param percentage  format the tick values as percentages. 
     */
    public NumberTickSelector(boolean percentage) {
        this.power = 0;
        this.factor = 1;
        this.percentage = percentage;
    }
    
    /**
     * Selects a standard tick size that is near to the specified reference
     * value.
     * 
     * @param reference  the reference value.
     * 
     * @return The selected tick size. 
     */
    @Override
    public double select(double reference) {
        this.power = (int) Math.ceil(Math.log10(reference));
        this.factor = 1;
        return getCurrentTickSize();
    }

    /**
     * Move the cursor to the next (larger) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the largest available.
     */
    @Override
    public boolean next() {
        if (this.factor == 1) {
            this.factor = 2;
            return true;
        } 
        if (this.factor == 2) {
            this.factor = 5;
            return true;  
        } 
        if (this.factor == 5) {
            this.power++;
            this.factor = 1;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    /**
     * Move the cursor to the previous (smaller) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the smallest available.
     */
    @Override
    public boolean previous() {
        if (this.factor == 1) {
            this.factor = 5;
            this.power--;
            return true;
        } 
        if (this.factor == 2) {
            this.factor = 1;
            return true;  
        } 
        if (this.factor == 5) {
            this.factor = 2;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    @Override
    public double getCurrentTickSize() {
        return this.factor * Math.pow(10.0, this.power);
    }
    
    private DecimalFormat dfNeg4 = new DecimalFormat("0.0000");
    private DecimalFormat dfNeg3 = new DecimalFormat("0.000");
    private DecimalFormat dfNeg2 = new DecimalFormat("0.00");
    private DecimalFormat dfNeg1 = new DecimalFormat("0.0");
    private DecimalFormat df0 = new DecimalFormat("#,##0");
    private DecimalFormat dfNeg4P = new DecimalFormat("0.00%");
    private DecimalFormat dfNeg3P = new DecimalFormat("0.0%");
    private DecimalFormat dfNeg2P = new DecimalFormat("0%");
    private DecimalFormat dfNeg1P = new DecimalFormat("0%");
    private DecimalFormat df0P = new DecimalFormat("#,##0%");

    @Override
    public Format getCurrentTickLabelFormat() {
        if (this.power == -4) {
            return this.percentage ? this.dfNeg4P : this.dfNeg4;
        }
        if (this.power == -3) {
            return this.percentage ? this.dfNeg3P : this.dfNeg3;
        }
        if (this.power == -2) {
            return this.percentage ? this.dfNeg2P : this.dfNeg2;
        }
        if (this.power == -1) {
            return this.percentage ? this.dfNeg1P : this.dfNeg1;
        }
        if (this.power >= 0 && this.power <= 6) {
            return this.percentage ? this.df0P : this.df0;
        }
        return this.percentage ? new DecimalFormat("0.0000E0%") 
                : new DecimalFormat("0.0000E0");
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj ==  this) {
            return true;
        }
        if (!(obj instanceof NumberTickSelector)) {
            return false;
        }
        NumberTickSelector that = (NumberTickSelector) obj;
        if (this.percentage != that.percentage) {
            return false;
        }
        return true;
    }
    
}
