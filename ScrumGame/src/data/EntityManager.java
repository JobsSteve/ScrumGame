package data;

import java.util.HashMap;
import java.util.LinkedList;
import com.badlogic.gdx.math.*;

public class EntityManager {
	HashMap<Faction, LinkedList<Entity>> factionLists = new HashMap<Faction, LinkedList<Entity>>( );  
	LinkedList<Entity> entityList = new LinkedList<Entity>( );
	
	public EntityManager( ){
		factionLists.put(Faction.Player, new LinkedList<Entity>( ));
		factionLists.put(Faction.Villager, new LinkedList<Entity>( ));
		factionLists.put(Faction.Monster, new LinkedList<Entity>( ));				
	}
	
	public void addEntity(Entity toAdd) {
		entityList.add(toAdd);
		factionLists.get(toAdd.getFaction( )).add(toAdd);
	}
	public void removeEntity(Entity toAdd) {
		entityList.remove(toAdd);
		factionLists.get(toAdd.getFaction( )).remove(toAdd);
	}
	public LinkedList<Entity> getFactionMembers(Faction faction) {
		return factionLists.get(faction);
	}
	public LinkedList<Entity> getEntities() {
		return entityList;
	}

	public void update(float deltaTime) {
		for(Entity entity : entityList) {
			entity.update(deltaTime);
		}
	}
	
	public float distance(Vector2 a, Vector2 b) {
		return (float)Math.sqrt( Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2) );
	}
	
	public Entity getClosest(Entity e, Faction f) {
		Entity closest = null;
		LinkedList<Entity> mobs = getFactionMembers(f);
		
		Vector2 pos;
		Vector2 myPos = e.getPosition();
		float dis = 9999f;
		for(Entity entity : mobs) {
			pos = entity.getPosition();
			float myDis = distance(myPos, pos);
			if (myDis < dis) {
				closest = entity;
				dis = myDis;
			}
		}
		
		return closest;
	}

}
