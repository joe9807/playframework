package controllers;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

public class ControllerExecutionContext extends CustomExecutionContext {
	@Inject
	public ControllerExecutionContext(ActorSystem actorSystem, String name) {
		super(actorSystem, "controller.dispatcher");
	}
}