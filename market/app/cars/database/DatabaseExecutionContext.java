package cars.database;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

public class DatabaseExecutionContext extends CustomExecutionContext {
	@Inject
	public DatabaseExecutionContext(ActorSystem actorSystem, String name) {
		super(actorSystem, "database.dispatcher");
	}
}