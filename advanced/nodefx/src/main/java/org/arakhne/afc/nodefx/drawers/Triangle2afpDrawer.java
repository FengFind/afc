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

package org.arakhne.afc.nodefx.drawers;

import org.arakhne.afc.math.geometry.d2.afp.Triangle2afp;
import org.arakhne.afc.nodefx.Drawer;
import org.arakhne.afc.nodefx.ZoomableGraphicsContext;

/** Drawer of a 2D triangle.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 15.0
 */
public class Triangle2afpDrawer implements Drawer<Triangle2afp<?, ?, ?, ?, ?, ?>> {

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Triangle2afp<?, ?, ?, ?, ?, ?>> getPrimitiveType() {
		return (Class<? extends Triangle2afp<?, ?, ?, ?, ?, ?>>) Triangle2afp.class;
	}

	@Override
	public void draw(ZoomableGraphicsContext gc, Triangle2afp<?, ?, ?, ?, ?, ?> element) {
		gc.beginPath();
		gc.moveTo(element.getX1(), element.getY1());
		gc.lineTo(element.getX2(), element.getY2());
		gc.lineTo(element.getX3(), element.getY3());
		gc.fill();
		gc.stroke();
	}

}
