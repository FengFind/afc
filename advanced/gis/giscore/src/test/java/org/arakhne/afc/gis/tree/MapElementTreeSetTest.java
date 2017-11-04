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

package org.arakhne.afc.gis.tree;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.arakhne.afc.gis.AbstractGisTest;
import org.arakhne.afc.gis.TestGISReader;
import org.arakhne.afc.gis.mapelement.MapElement;
import org.arakhne.afc.gis.mapelement.MapPolyline;
import org.arakhne.afc.gis.primitive.GISPrimitive;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Rectangle2d;
import org.arakhne.afc.vmutil.Resources;

/** Unit test for MapElementTreeSet.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 14.0
 */
@SuppressWarnings("all")
public class MapElementTreeSetTest extends AbstractGisTest {

	private static final String SHP_RESOURCE = MapElementTreeSetTest.class.getPackage().getName().replaceAll("\\.", "/") //$NON-NLS-1$ //$NON-NLS-2$
			+ "/test.shp"; //$NON-NLS-1$

	private ArrayList<MapPolyline> reference = null;
	private Rectangle2d bounds = null;

	@Before
	public void setUp() throws Exception {
		this.reference = new ArrayList<>();

		InputStream is = Resources.getResourceAsStream(SHP_RESOURCE);
		assertNotNull(is);

		TestGISReader reader = new TestGISReader(is);

		MapElement element;

		Rectangle2d abounds = new Rectangle2d();

		while ((element=reader.read())!=null) {
			if (element instanceof MapPolyline) {
				MapPolyline p = (MapPolyline)element;
				this.reference.add(p);
				abounds.setUnion(p.getBoundingBox().toBoundingBox());
			}
		}

		reader.close();

		if (!abounds.isEmpty()) this.bounds = abounds;
		else this.bounds = null;
	}

	@After
	public void tearDown() throws Exception {
		this.reference.clear();
		this.reference = null;
		this.bounds = null;
	}

	@Test
	public void testGetNearest() {
    	System.out.println("Preparing the benchmark..."); //$NON-NLS-1$
		MapElementTreeSet<MapPolyline> test;
		if (this.bounds!=null)
			test = new MapElementTreeSet<>(this.bounds);
		else
			test = new MapElementTreeSet<>();
        test.addAll(this.reference);
        assertEpsilonEquals(this.reference.size(), test.size());

    	System.out.println("Run test..."); //$NON-NLS-1$

    	Random rnd = new Random();
    	MapPolyline nearestData;
    	double minDistance, distance, x, y;
    	Point2d p;
    	final ArrayList<MapPolyline> nearest = new ArrayList<>();

    	x = rnd.nextDouble()*this.bounds.getWidth()+this.bounds.getMinX();
    	y = rnd.nextDouble()*this.bounds.getHeight()+this.bounds.getMinY();
    	p = new Point2d(x,y);

    	minDistance = Double.MAX_VALUE;
    	nearest.clear();
    	for (MapPolyline line : this.reference) {
    		distance = line.getDistance(p);
    		if (isEpsilonEquals(minDistance, distance)) {
    			nearest.add(line);
    		}
    		else if (distance<minDistance) {
    			minDistance = distance;
    			nearest.clear();
    			nearest.add(line);
    		}
    	}

    	nearestData = test.getNearest(x, y);
    	assertNotNull(nearestData);

    	//TODO: assertEpsilonEquals(minDistance, nearestData.distance(p));
    	assertTrue(nearest.contains(nearestData));
	}

	@Test
	public void testAddE() {
		StandardGISTreeSet<GISPrimitive> test = new StandardGISTreeSet<>(this.bounds);
        assertTrue(test.addAll(this.reference));
        assertEpsilonEquals(this.reference.size(), test.size());

        String msg;
        Random rnd = new Random();
        int testCount = rnd.nextInt(5)+1;

        for(int i=0; i<testCount; ++i) {
        	msg = "test "+(i+1)+"/"+testCount; //$NON-NLS-1$ //$NON-NLS-2$
        	System.out.print(msg+"..."); //$NON-NLS-1$

	        // Add an element
        	double x = this.bounds.getMinX() + rnd.nextDouble() * this.bounds.getWidth();
        	double y = this.bounds.getMinY() + rnd.nextDouble() * this.bounds.getHeight();
	        MapPolyline newElement = new MapPolyline();
	        newElement.addPoint(x, y);
        	x = this.bounds.getMinX() + rnd.nextDouble() * this.bounds.getWidth();
        	y = this.bounds.getMinY() + rnd.nextDouble() * this.bounds.getHeight();
	        newElement.addPoint(x, y);
	        assertTrue(this.reference.add(newElement));
	        assertTrue(msg,test.add(newElement));
	        assertEquals(msg,this.reference.size(), test.size());
	        assertTrue(msg,test.slowContains(newElement));
	    	assertEpsilonEquals(msg,this.reference.toArray(),test.toArray());
        	System.out.println("done"); //$NON-NLS-1$
        }
	}

}