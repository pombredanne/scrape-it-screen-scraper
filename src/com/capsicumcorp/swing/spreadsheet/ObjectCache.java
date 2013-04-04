/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JSpread.
 * 
 * JSpread is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * JSpread is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.capsicumcorp.swing.spreadsheet;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Caches object instances. Do not modify instances that are passed into or
 * returned; doing so will corrupt the cache.
 * 
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class ObjectCache {
    private WeakHashMap cache = new WeakHashMap();
    
    public Object get(Object obj) {
        WeakReference objRef = (WeakReference) cache.get(obj);
        Object cacheValue = null;
        if (objRef == null) {
            // Add object to cache
            cacheValue = obj;
            cache.put(obj, new WeakReference(cacheValue));
        } else {
            cacheValue = objRef.get();
        }
        return cacheValue;        
    }
}
