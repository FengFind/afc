/* 
 * $Id$
 * 
 * Copyright (C) 2010-2012 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */

/**
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
package org.arakhne.afc.math.geometry.d3.continuous;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class Segment3fTest extends AbstractShape3fTestCase<Segment3f> {

	
	@Override
	protected Segment3f createShape() {
		return new Segment3f(Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random());
	}

	@Test
	@Override
	public void testClone() {
		Segment3f b = this.r.clone();

		assertNotSame(b, this.r);
		assertEpsilonEquals(this.r.getX1(), b.getX1());
		assertEpsilonEquals(this.r.getY1(), b.getY1());
		assertEpsilonEquals(this.r.getZ1(), b.getZ1());
		assertEpsilonEquals(this.r.getX2(), b.getX2());
		assertEpsilonEquals(this.r.getY2(), b.getY2());
		assertEpsilonEquals(this.r.getZ2(), b.getZ2());
		
		
		b.set(this.r.getX1()+1f, this.r.getY1()+1f, this.r.getZ1()+1f,
				this.r.getX2()+1f, this.r.getY2()+1f, this.r.getZ2()+1f);

		assertNotEpsilonEquals(this.r.getX1(), b.getX1());
		assertNotEpsilonEquals(this.r.getY1(), b.getY1());
		assertNotEpsilonEquals(this.r.getZ1(), b.getZ1());
		assertNotEpsilonEquals(this.r.getX2(), b.getX2());
		assertNotEpsilonEquals(this.r.getY2(), b.getY2());
		assertNotEpsilonEquals(this.r.getZ2(), b.getZ2());
	}

	@Test
	@Override
	public void distancePoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void containsPoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void testEquals() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void testHashCode() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void toBoundingBox() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void toBoundingBoxAlignedBox3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void distanceSquaredPoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void distanceL1Point3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void distanceLinfPoint3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void translateVector3D() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void translateDoubleDoubleDouble() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void containsDoubleDoubleDouble() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsAlignedBox3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsSphere3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsSegment3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsTriangle3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsCapsule3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsOrientedBox3f() {
		throw new UnsupportedOperationException();
	}

	@Test
	@Override
	public void intersectsAbstractPlane3D() {
		throw new UnsupportedOperationException();
	}

	
}
