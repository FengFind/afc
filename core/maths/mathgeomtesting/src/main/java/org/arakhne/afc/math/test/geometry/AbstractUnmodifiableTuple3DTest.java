/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2020 The original authors, and other authors.
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

package org.arakhne.afc.math.test.geometry;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import org.arakhne.afc.math.geometry.d3.Tuple3D;
import org.arakhne.afc.math.geometry.d3.d.Tuple3d;

@SuppressWarnings("all")
public abstract class AbstractUnmodifiableTuple3DTest<TT extends Tuple3D> extends AbstractTuple3DTest<TT> {
	
	@Override
	@Test
	public final void absolute() {
		assertThrows(UnsupportedOperationException.class, () -> getT().absolute());
	}

	@Override
	@Test
	public final void absoluteT() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Tuple3D c = new Tuple3d();
			getT().absolute(c);
		});
	}

	@Test
	public final void addIntInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().add(6, 7, 0));
	}

	@Test
	public final void addDoubleDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().add(6.5, 7.5, 0));
	}

	@Override
	@Test
	public final void addXInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().addX(6));
	}

	@Test
	public final void addXDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().addX(6.5));
	}

	@Override
	@Test
	public final void addYInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().addY(6));
	}

	@Test
	public final void addYDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().addY(6.5));
	}

	@Override
	@Test
	public final void negateT() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Tuple3D c = new Tuple3d();
			getT().negate(c);
		});
	}

	@Override
	@Test
	public final void negate() {
		assertThrows(UnsupportedOperationException.class, () -> getT().negate());
	}

	@Override
	@Test
	public final void scaleIntT() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Tuple3D c = new Tuple3d(2, -1, 0);
			getT().scale(4, c);
		});
	}

	@Test
	public final void scaleDoubleT() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Tuple3D c = new Tuple3d(2, -1, 0);
			getT().scale(4.5, c);
		});
	}

	@Override
	@Test
	public final void scaleInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().scale(4));
	}

	@Test
	public final void scaleDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().scale(4.5));
	}

	@Override
	@Test
	public final void setTuple3D() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Tuple3D c = new Tuple3d(-45, 78, 0);
			getT().set(c);
		});
	}

	@Test
	public final void setIntInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().set(-45, 78, 0));
	}

	@Test
	public final void setDoubleDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().set(-45.5, 78.5, 0));
	}

	@Override
	@Test
	public final void setIntArray() {
		assertThrows(UnsupportedOperationException.class, () -> getT().set(new int[]{-45, 78, 0}));
	}

	@Test
	public final void setDoubleArray() {
		assertThrows(UnsupportedOperationException.class, () -> getT().set(new double[]{-45.5, 78.5, 0}));
	}

	@Override
	@Test
	public final void setXInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().setX(45));
	}

	@Test
	public final void setXDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().setX(45.5));
	}

	@Override
	@Test
	public final void setYInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().setY(45));
	}

	@Test
	public final void setYDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().setY(45.5));
	}

	@Test
	public final void subIntInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().sub(45, 78, 0));
	}

	@Override
	@Test
	public final void subXInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().subX(45));
	}

	@Override
	@Test
	public final void subYInt() {
		assertThrows(UnsupportedOperationException.class, () -> getT().subY(78));
	}

	@Test
	public final void subDoubleDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().sub(45.5, 78.5, 0));
	}

	@Test
	public final void subXDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().subX(45.5));
	}

	@Test
	public final void subYDouble() {
		assertThrows(UnsupportedOperationException.class, () -> getT().subY(78.5));
	}

	@Override
	@Test
	public final void addDoubleDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().add(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void addDoubleDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().add(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void addXDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().addX(12.3);
		});
	}

	@Override
	@Test
	public final void addXDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().addX(12.3);
		});
	}

	@Override
	@Test
	public final void addYDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().addY(12.3);
		});
	}

	@Override
	@Test
	public final void addYDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().addY(12.3);
		});
	}

	@Override
	@Test
	public final void scaleDoubleT_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().scale(12.3, createTuple(1,2, 0));
		});
	}

	@Override
	@Test
	public final void scaleDoubleT_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().scale(12.3, createTuple(1,2, 0));
		});
	}

	@Override
	@Test
	public final void scaleDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().scale(12.3);
		});
	}

	@Override
	@Test
	public final void scaleDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().scale(12.3);
		});
	}

	@Override
	@Test
	public final void setDoubleDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().set(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void setDoubleDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().set(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void setDoubleArray_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().set(new double[] {12.3, 4.56, 0});
		});
	}

	@Override
	@Test
	public final void setDoubleArray_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().set(new double[] {12.3, 4.56, 0});
		});
	}

	@Override
	@Test
	public final void setXDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().setX(12.3);
		});
	}

	@Override
	@Test
	public final void setXDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().setX(12.3);
		});
	}

	@Override
	@Test
	public final void setYDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().setY(12.3);
		});
	}

	@Override
	@Test
	public final void setYDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().setY(12.3);
		});
	}

	@Override
	@Test
	public final void subDoubleDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().sub(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void subDoubleDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().sub(12.3, 4.56, 0);
		});
	}

	@Override
	@Test
	public final void subXDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().subX(12.3);
		});
	}

	@Override
	@Test
	public final void subXDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().subX(12.3);
		});
	}

	@Override
	@Test
	public final void subYDouble_iffp() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeFalse(isIntCoordinates());
			getT().subY(12.3);
		});
	}

	@Override
	@Test
	public final void subYDouble_ifi() {
		assertThrows(UnsupportedOperationException.class, () -> {
			assumeTrue(isIntCoordinates());
			getT().subY(12.3);
		});
	}

}
