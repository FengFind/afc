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

package org.arakhne.afc.io.shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 14.0
 */
@SuppressWarnings("all")
public class ESRIPointTest extends AbstractIoShapeTest {

	private double x, y, z, m;
	private ESRIPoint pts;
	
	@Before
	public void setUp() throws Exception {
		this.x = getRandom().nextDouble();
		this.y = getRandom().nextDouble();
		this.z = getRandom().nextDouble();
		this.m = getRandom().nextDouble();
		this.pts = new ESRIPoint(this.x, this.y, this.z, this.m);
	}

	@After
	public void tearDown() throws Exception {
		this.pts = null;
	}

	@Test
	public void testGetM() {
		assertEpsilonEquals(this.m, this.pts.getM());
	}

	@Test
	public void testSetM() {
		assertEpsilonEquals(this.m, this.pts.getM());
		double nm = getRandom().nextDouble();
		this.pts.setM(nm);
		assertEpsilonEquals(nm, this.pts.getM());
	}

}