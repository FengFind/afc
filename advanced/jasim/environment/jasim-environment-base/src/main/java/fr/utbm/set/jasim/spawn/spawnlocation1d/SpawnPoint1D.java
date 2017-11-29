/*
 * $Id$
 * 
 * Copyright (c) 2006-09, Multiagent Team - Systems and Transportation Laboratory (SeT)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Systems and Transportation Laboratory ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 * 
 * http://www.multiagent.fr/
 */
package fr.utbm.set.jasim.spawn.spawnlocation1d;

import java.util.UUID;

import fr.utbm.set.geom.object.Direction1D;
import fr.utbm.set.geom.object.Point1D;
import fr.utbm.set.jasim.spawn.AbstractSpawnLocation;

/**
 * Spawn entities at a point of the environment.
 * <p>
 * The agents will be spawn at the reference position.
 * 
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SpawnPoint1D extends AbstractSpawnLocation<Point1D,Direction1D,Direction1D> {
	
	/**
	 * @param identifier is the identifier of the spawning location.
	 * @param name is the name of the spawning location.
	 * @param position is the position on the road.
	 * @param direction is the allowed spawning direction(s).
	 * @param startDate is the date at which the spawn point is starting to spawn entities.
	 * @param endDate is the date at which the spawn point is finishing to spawn entities.
	 */
	public SpawnPoint1D(UUID identifier, String name, Point1D position, Direction1D direction, double startDate, double endDate) {
		super(identifier, name, position, direction, startDate, endDate);
	}
	
	/** Replies the spawning position of an agent.
	 * 
	 * @param currentIdx is the current index of the spawned agent.
	 * @param spawnedEntities is the count of already spawned entities from the current spawning list.
	 * @param spawnableEntities is the count of entities from the current spawning list.
	 * @return a random position
	 */
	@Override
	protected Point1D getPosition(long currentIdx, long spawnedEntities, long spawnableEntities) {
		return getReferencePoint();
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	protected final Direction1D randomDirection() {
		Direction1D ref = getReferenceDirection();
		switch(ref) {
		case SEGMENT_DIRECTION:
		case REVERTED_DIRECTION:
			return ref;
		case BOTH_DIRECTIONS:
			return this.rnd.nextBoolean() ? Direction1D.SEGMENT_DIRECTION : Direction1D.REVERTED_DIRECTION;
		default:
		}
		throw new IllegalArgumentException("invalid direction constraint"); //$NON-NLS-1$
	}

}