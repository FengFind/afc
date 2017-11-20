/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2018 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.gis.road.ui.drawers;

import org.arakhne.afc.gis.mapelement.GISElementContainer;
import org.arakhne.afc.gis.mapelement.MapPolyline;
import org.arakhne.afc.gis.road.RoadPolyline;
import org.arakhne.afc.gis.ui.drawers.GisContainerDrawer;
import org.arakhne.afc.nodefx.Drawer;
import org.arakhne.afc.nodefx.Drawers;
import org.arakhne.afc.nodefx.LevelOfDetails;
import org.arakhne.afc.nodefx.ZoomableGraphicsContext;

/** Drawer of a map road polyline.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 15.0
 */
public class RoadNetworkDrawer extends GisContainerDrawer<RoadPolyline> {

	private Drawer<MapPolyline> mappolylineDrawer;

	/** Constructor.
	 */
	public RoadNetworkDrawer() {
		this(Drawers.getDrawerFor(RoadPolyline.class));
	}

	/** Constructor.
	 *
	 * @param drawer the element drawer.
	 */
	public RoadNetworkDrawer(Drawer<RoadPolyline> drawer) {
		super(drawer);
	}

	@Override
	public void draw(ZoomableGraphicsContext gc, GISElementContainer<RoadPolyline> element) {
		final LevelOfDetails lod = gc.getLevelOfDetails();
		if (lod == LevelOfDetails.LOW) {
			// Too small to draw the road details => uses the standard map polyline drawer.
			if (this.mappolylineDrawer == null) {
				this.mappolylineDrawer = Drawers.getDrawerFor(MapPolyline.class);
			}
			draw(gc, element, this.mappolylineDrawer);
		} else {
			gc.save();
			gc.setState(0);
			super.draw(gc, element);
			gc.restore();
			gc.save();
			gc.setState(1);
			super.draw(gc, element);
			gc.restore();
		}
	}

}