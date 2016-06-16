/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
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

package org.arakhne.afc.math.geometry.d3.d;

import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.math.geometry.d3.Vector3D;
import org.arakhne.afc.math.geometry.d3.afp.AbstractGeomFactory3afpTest;
import org.arakhne.afc.math.geometry.d3.afp.GeomFactory3afp;

@SuppressWarnings("all")
public class GeomFactory3dTest extends AbstractGeomFactory3afpTest {

	@Override
	protected GeomFactory3afp<?, ?, ?, ?> createFactory() {
		return new GeomFactory3d();
	}
	
	@Override
	protected Point3D createPoint(double x, double y, double z) {
		return new Point3d(x, y, z);
	}

	@Override
	protected Vector3D createVector(double x, double y, double z) {
		return new Vector3d(x, y, z);
	}
	
}
