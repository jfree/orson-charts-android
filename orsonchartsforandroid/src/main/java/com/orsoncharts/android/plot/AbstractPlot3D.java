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

package com.orsoncharts.android.plot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.data.Dataset3DChangeEvent;
import com.orsoncharts.android.data.Dataset3DChangeListener;
import com.orsoncharts.android.graphics3d.Dimension3D;

/**
 * A base class that can be used to create classes that implement 
 * {@link Plot3D}.  
 * <br><br>
 * A mechanism is provided for registering change listeners 
 * on the plot.  Whenever some attribute of the plot changes, all the 
 * registered listeners are notified.  The {@link Chart3D} instance that owns
 * the plot will be automatically registered as a listener so that it receives
 * notification whenever the plot (or some other object managed by the plot)
 * changes.
 * <br><br>
 * Typically a plot registers itself as a change listener on its dataset
 * and whenever a dataset change notification is received, the plot will 
 * pass on a {@link Plot3DChangeEvent} to all *its* listeners.  If the plot 
 * has axes, then the same approach is used to listen for changes to the axes.
 */
public abstract class AbstractPlot3D implements Plot3D,
        Dataset3DChangeListener, Serializable {
  
    /** 
     * The plot dimensions in 3D space.  By default, this is auto-adjusted
     * according to the dataset, but the user can override this.
     */
    protected Dimension3D dimensions;
  
    /**
     * A flag that controls whether or not the plot dimensions (in the 3D
     * model) are adjusted automatically.
     */
    protected boolean autoAdjustDimensions;
    
    /** Storage for registered change listeners. */
    private transient List<Plot3DChangeListener> listenerList;

    /**
     * A flag that controls whether or not the plot will notify listeners
     * of changes (defaults to <code>true</code>, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor.
     */
    protected AbstractPlot3D() {
        this.dimensions = new Dimension3D(1.0, 1.0, 1.0);
        this.autoAdjustDimensions = true;
        this.listenerList = new ArrayList<Plot3DChangeListener>();
        this.notify = true;
    }
  
    /**
     * Returns the dimensions of the box in 3D space into which the plot will 
     * be composed.  The dimension can change according to the shape of the 
     * data.
     * 
     * @return The dimensions of the plot (never {@code null}).
     * 
     * @see #isAutoAdjustDimensions() 
     */
    @Override
    public Dimension3D getDimensions() {
        return this.dimensions;
    }

    /**
     * Returns the flag that controls whether or not the plot dimensions are
     * auto-adjusted when the dataset changes.  Certain subclasses will allow
     * this flag to be changed ({@link CategoryPlot3D} and {@link XYZPlot}) 
     * while others will always auto-adjust the dimensions ({@link PiePlot3D}).
     * 
     * @return A boolean. 
     */
    public boolean isAutoAdjustDimensions() {
        return this.autoAdjustDimensions;    
    }

    /**
     * Tests this plot for equality with an arbitrary object.
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
        if (!(obj instanceof AbstractPlot3D)) {
            return false;
        }
        AbstractPlot3D that = (AbstractPlot3D) obj;
        if (!this.dimensions.equals(that.dimensions)) {
            return false;
        }
        return true;
    }
 
    /**
     * Returns a flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean isNotify() {
        return this.notify;
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link Plot3DChangeEvent} notifications.
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            fireChangeEvent();
        }
    }
    
    /**
     * Registers an object for notification of changes to the plot.
     *
     * @param listener  the object to be registered.
     *
     * @see #removeChangeListener(Plot3DChangeListener)
     */
    @Override
    public void addChangeListener(Plot3DChangeListener listener) {
        this.listenerList.add(listener);
    }

    /**
     * Unregisters an object for notification of changes to the plot.
     *
     * @param listener  the object to be unregistered.
     *
     * @see #addChangeListener(Plot3DChangeListener)
     */
    @Override
    public void removeChangeListener(Plot3DChangeListener listener) {
        this.listenerList.remove(listener);
    }

    /**
     * Notifies all registered listeners that the plot has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Plot3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        for (Plot3DChangeListener listener : this.listenerList) {
            listener.plotChanged(event);
        }
    }

    /**
     * Sends a {@link Plot3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Plot3DChangeEvent(this, this));
    }

    /**
     * Receives notification of a dataset change, and passes this event on
     * wrapped in a {@link Plot3DChangeEvent}.
     * 
     * @param event  the dataset change event. 
     */
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        notifyListeners(new Plot3DChangeEvent(event, this));
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        // recreate an empty listener list
        this.listenerList = new ArrayList<Plot3DChangeListener>();
    }
}
