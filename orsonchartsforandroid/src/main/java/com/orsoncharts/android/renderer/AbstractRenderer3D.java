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

package com.orsoncharts.android.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * A base class for 3D renderers.
 */
public abstract class AbstractRenderer3D implements Renderer3D {
    
    /** Storage for registered change listeners. */
    private transient List<Renderer3DChangeListener> listenerList;

    /**
     * A flag that controls whether or not the renderer will notify listeners
     * of changes (defaults to <code>true</code>, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor.
     */
    protected AbstractRenderer3D() {
        this.listenerList = new ArrayList<Renderer3DChangeListener>();
        this.notify = true;
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
     * {@link Renderer3DChangeEvent} notifications.
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
     * Registers an object for notification of changes to the renderer.
     *
     * @param listener  the object to be registered.
     *
     * @see #removeChangeListener(Renderer3DChangeListener)
     */
    @Override
    public void addChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.add(listener);
    }

    /**
     * Unregisters an object for notification of changes to the renderer.
     *
     * @param listener  the object to be unregistered.
     *
     * @see #addChangeListener(Renderer3DChangeListener) 
     */
    @Override
    public void removeChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.remove(listener);
    }

    /**
     * Notifies all registered listeners that the plot has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Renderer3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        for (Renderer3DChangeListener listener : this.listenerList) {
            listener.rendererChanged(event);
        }
    }

    /**
     * Sends a {@link Renderer3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Renderer3DChangeEvent(this));
    }

    /**
     * Tests this renderer for equality with an arbitrary object.  The 
     * change listeners are NOT considered in the test, but the 
     * <code>notify</code> flag is taken into account.
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
        if (!(obj instanceof AbstractRenderer3D)) {
            return false;
        }
        AbstractRenderer3D that = (AbstractRenderer3D) obj;
        if (this.notify != that.notify) {
            return false;
        }
        return true;
    }
}
