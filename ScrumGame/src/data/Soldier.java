package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.math.Vector2;

public class Soldier extends Entity {
	
	public Soldier(Vector2 position, Facing facing) {
		super(position, facing, Faction.Player);
		animations = new AnimatedSprite(MainGame.getTextureRepo().getSpriteSheet(SheetType.Soldier));
	
		unitType = EntityType.Soldier;
		init();
	}
	
	@Override
	protected void takeAction() {
		switch(state) {
		case Idle:
			state = AIState.Roam;
		case Roam:
			actionInterval = GameData.getActionInterval(unitType);
			if (!validTarget())
				target = manager.getClosest(this, Faction.Monster);
			
			if (target != null) {
				if (target.getState() == AIState.Dead || target.getState() == AIState.Disabled) {
					target = null;
					break;
				}
				
				targetRange = manager.distance(target.getPosition(), position);
				if ( targetRange <= visionRange )
					state = AIState.Hunt;
			}
			
			if(state == AIState.Roam)
				roam();
			break;
		case Hunt:
			actionInterval = GameData.getAggroInterval(unitType);
			if (!validTarget()) {
				state = AIState.Roam;
				break;
			}
			moveTo(target, 1);
			targetRange = manager.distance(target.getPosition(), position);
			
			if (targetRange <= attackRange)
				state = AIState.Attack;
			
			if (targetRange > visionRange + 3) {
				state = AIState.Roam;
			}
			
			break;
		case Attack:
			if (!attack(target)) {
				state = AIState.Hunt;
			}
			
			if (!validTarget())
			{
				target = null;
				state = AIState.Roam;
			}
			break;
		case Dead:
			//handled in parent class
			break;
		default:
			//this shouldn't happen, so just kill the unit
			state = AIState.Dead;
		}
	}
	
	protected  void attackedByEntity(Entity e) {
		//null
	}
}
