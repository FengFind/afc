/* 
 * $Id$
 * 
 * Copyright (C) 2010-2011 Janus Core Developers
 * Copyright (C) 2012 Stéphane GALLAND
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.arakhne.afc.math.physics;

import java.util.concurrent.TimeUnit;

import org.arakhne.afc.vmutil.locale.Locale;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A <tt>SpeedUnit</tt> represents speed at a given unit of
 * granularity.
 *
 * @see MeasureUnitUtil
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public enum SpeedUnit {

	/** millimeters/second.
	 */
	MILLIMETERS_PER_SECOND,
	/** meters/second.
	 */
	METERS_PER_SECOND,
	/** kilometer/hour.
	 */
	KILOMETERS_PER_HOUR;

	/** Replies the time unit that corresponds to this speed unit.
	 * 
	 * @return the time unit.
	 */
	@Pure
	public TimeUnit toTimeUnit() {
		switch(this) {
		case KILOMETERS_PER_HOUR:
			return TimeUnit.HOURS;
		case METERS_PER_SECOND:
		case MILLIMETERS_PER_SECOND:
			return TimeUnit.SECONDS;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/** Replies the space unit that corresponds to this speed unit.
	 * 
	 * @return the space unit.
	 */
	@Pure
	public SpaceUnit toSpaceUnit() {
		switch(this) {
		case KILOMETERS_PER_HOUR:
			return SpaceUnit.KILOMETER;
		case METERS_PER_SECOND:
			return SpaceUnit.METER;
		case MILLIMETERS_PER_SECOND:
			return SpaceUnit.MILLIMETER;
		default:
			throw new IllegalArgumentException();
		}
	}

	/** Replies the speed unit just lower than the
	 * current speed unit, or the speed unit itself if
	 * it is the smallest.
	 * @return the speed unit just lower than the
	 * current speed unit, or the speed unit itself if
	 * it is the smallest.
	 */
	@Pure
	public SpeedUnit lower() {
		int o = ordinal();
		if (o<=0) return this;
		return values()[o-1];
	}

	/** Replies the speed unit just upper than the
	 * current speed unit, or the speed unit itself if
	 * it is the uppest.
	 * @return the speed unit just upper than the
	 * current speed unit, or the speed unit itself if
	 * it is the uppest.
	 */
	@Pure
	public SpeedUnit upper() {
		int o = ordinal();
		SpeedUnit[] units = values();
		if (o>=units.length-1) return this;
		return units[o+1];
	}

	/** Replies the localized symbol for this space unit.
	 * 
	 * @return the localized symbol for this space unit.
	 */
	@Pure
	public String getSymbol() {
		return Locale.getString(name());
	}

}