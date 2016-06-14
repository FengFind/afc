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

package org.arakhne.afc.math.geometry.d3.afp;

import org.arakhne.afc.math.geometry.d3.Tuple3D;
import org.arakhne.afc.math.geometry.d3.UnmodifiableVector3D;
import org.arakhne.afc.math.geometry.d3.Vector3D;

/** A vector that is used for internal computations.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class InnerComputationVector3afp implements Vector3D<InnerComputationVector3afp, InnerComputationPoint3afp> {

	private static final long serialVersionUID = 8578192819251519051L;

	private double x;

	private double y;

	private double z;

	/** Construct vector.
	 */
	public InnerComputationVector3afp() {
		//
	}

	/** Construct vector.
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param z z coordinate.
	 */
	public InnerComputationVector3afp(double x, double y, double z) {
	    this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public InnerComputationGeomFactory3afp getGeomFactory() {
		return InnerComputationGeomFactory3afp.SINGLETON;
	}

	@Override
	public String toString() {
		return Tuple3D.toString(this.x, this.y, this.z);
	}

	@Override
	public InnerComputationVector3afp clone() {
	    try {
            return (InnerComputationVector3afp) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public int ix() {
		return (int) Math.round(this.x);
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public int iy() {
		return (int) Math.round(this.y);
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getZ() {
		return this.z;
	}

	@Override
	public int iz() {
		return (int) Math.round(this.z);
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public UnmodifiableVector3D<InnerComputationVector3afp, InnerComputationPoint3afp> toUnmodifiable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public InnerComputationVector3afp toUnitVector() {
		final double length = getLength();
		if (length == 0) {
			return new InnerComputationVector3afp();
		}
		return new InnerComputationVector3afp(getX() / length, getY() / length, getZ() / length);
	}

}
