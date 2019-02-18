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


/**
 * A utility class that creates and returns color swatches that can be used
 * in charts.
 */
public class Colors {

    private Colors() {
        // no need to instantiate this class
    }
    
    /**
     * Returns the default colors.
     * 
     * @return The default colors. 
     */
    public static int[] getDefaultColors() {
        return getSAPMultiColor();
    }

    /**
     * Returns a set of colors sourced from 
     * http://www.sapdesignguild.org/goodies/diagram_guidelines/index.html.
     * 
     * @return A color array.
     */
    public static int[] getSAPMultiColor() {
        return new int[] {
            Color.rgb(255, 248, 163),
            Color.rgb(169, 204, 143),
            Color.rgb(178, 200, 217),
            Color.rgb(190, 163, 122),
            Color.rgb(243, 170, 121),
            Color.rgb(181, 181, 169),
            Color.rgb(230, 165, 164),
            Color.rgb(248, 215, 83),
            Color.rgb(92, 151, 70),
            Color.rgb(62, 117, 167),
            Color.rgb(122, 101, 62),
            Color.rgb(225, 102, 42),
            Color.rgb(116, 121, 111),
            Color.rgb(196, 56, 79)
        };
    }
    
    /**
     * Returns an array of four colors.
     * 
     * @return An array of four colors. 
     */
    public static int[] getColors1() {
        return new int[] { Color.rgb(0, 55, 122),  
                Color.rgb(24, 123, 58), Color.RED, Color.YELLOW };
    }
    
    /**
     * Returns an array of four colors.
     * 
     * @return An array of four colors. 
     */
    public static int[] getColors2() {
        return new int[] {
                Color.rgb(0x1A, 0x96, 0x41), Color.rgb(0xA6, 0xD9, 0x6A), 
                Color.rgb(0xFD, 0xAE, 0x61), Color.rgb(0xFF, 0xFF, 0xBF)};
    }
        
    /**
     * Returns an array of six colors 
     * (source: http://blog.design-seeds.com/generating-color/).
     * 
     * @return An array of six colors. 
     */
    public static int[] getDesignSeedsShells() {
        return new int[] {
                Color.rgb(228, 233, 239),
                Color.rgb(184, 197, 219),
                Color.rgb(111, 122, 143),
                Color.rgb(95, 89, 89),
                Color.rgb(206, 167, 145),
                Color.rgb(188, 182, 173)
        };
    }
    
    /**
     * Returns an array of six colors 
     * (source: http://blog.design-seeds.com/generating-color/).
     * 
     * @return An array of six colors. 
     */
    public static int[] getDesignSeedsPepper() {
        return new int[] {
                Color.rgb(255, 219, 142),
                Color.rgb(220, 21, 20),
                Color.rgb(149, 0, 1),
                Color.rgb(82, 102, 41),
                Color.rgb(142, 101, 72),
                Color.rgb(199, 169, 128)
        };
    }
}
