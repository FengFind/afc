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

package org.arakhne.afc.math.geometry.d3.ifx;

import org.junit.Ignore;

import org.arakhne.afc.math.geometry.d3.AbstractVector3DTest;

@SuppressWarnings("all")
@Ignore("temporary")
public class Vector3ifxTest extends AbstractVector3DTest<Vector3ifx, Point3ifx, Vector3ifx> {

	@Override
	public boolean isIntCoordinates() {
		return true;
	}
	
	@Override
	public Vector3ifx createTuple(double x, double y, double z) {
		return new Vector3ifx(x, y, z);
	}
	
	@Override
	public Vector3ifx createVector(double x, double y, double z) {
		return new Vector3ifx(x, y, z);
	}

	@Override
	public Point3ifx createPoint(double x, double y, double z) {
		return new Point3ifx(x, y, z);
	}

}
