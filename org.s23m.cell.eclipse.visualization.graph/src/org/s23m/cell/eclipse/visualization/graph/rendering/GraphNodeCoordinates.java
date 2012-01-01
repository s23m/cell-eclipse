/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Gmodel.
 *
 * The Initial Developer of the Original Code is
 * Sofismo AG (Sofismo).
 * Portions created by the Initial Developer are
 * Copyright (C) 2009-2010 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;

public  class GraphNodeCoordinates {

	private static Map<String, Map<String, Point>> coordMaps = null;

	protected GraphNodeCoordinates() {
    }

	private static synchronized <T extends Renderer> Map<String, Point> getMap(final Class<T> renderer) {
		final String rendererName = renderer.getName();
		if (coordMaps == null) {
			coordMaps = new HashMap<String, Map<String, Point>>();
		}
		if (coordMaps.containsKey(rendererName)) {
			return coordMaps.get(rendererName);
		} else {
			final Map<String, Point> newCoordMap = new HashMap<String, Point>();
			coordMaps.put(rendererName, newCoordMap);
			return newCoordMap;
		}
	}

	public static <T extends Renderer> void addSet(final String artifactUUID, final String setUUID, final Point location, final Class<T> renderer) {
		final String uuid = getMapId(artifactUUID, setUUID);
		if (getMap(renderer).containsKey(uuid)) {
			getMap(renderer).remove(uuid);
		}
		getMap(renderer).put(uuid, location);
	}

	public static <T extends Renderer> Point getCoordinatesOf(final String artifactUUID, final String setUUID, final Class<T> renderer) {
		final String uuid = getMapId(artifactUUID, setUUID);
		if(getMap(renderer).containsKey(uuid)) {
			return getMap(renderer).get(uuid);
		} else {
			return null;
		}
	}

	public static <T extends Renderer> boolean hasCoordinatesOf(final String artifactUUID, final String setUUID, final Class<T> renderer) {
		if (getCoordinatesOf(artifactUUID, setUUID, renderer) != null) {
				return true;
			}
		return false;
	}

	public static <T extends Renderer> void removeCoordinatesOf(final String artifactUUID, final String setUUID, final Class<T> renderer) {
		if (getCoordinatesOf(artifactUUID, setUUID, renderer) != null) {
				final String uuid = getMapId(artifactUUID, setUUID);
				if(getMap(renderer).containsKey(uuid)) {
					getMap(renderer).remove(uuid);
				}
			}
	}

	private static String getMapId(final String artifactUUID, final String setUUID) {
		return artifactUUID+setUUID;
	}

}
