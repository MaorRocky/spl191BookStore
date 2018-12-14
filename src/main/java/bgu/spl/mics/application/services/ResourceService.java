package bgu.spl.mics.application.services;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.Events.ReturnVehicleEvent;
import bgu.spl.mics.application.Events.SendDeliveryEvent;
import bgu.spl.mics.application.Events.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourcesHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService{
	private ResourcesHolder resources;


	public ResourceService(String name) {
		super(name);
		resources = ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		subscribeEvent(SendDeliveryEvent.class, delivery -> {
			Future<DeliveryVehicle> future = resources.acquireVehicle();
			DeliveryVehicle vehicle = future.get();
			complete(delivery, vehicle);
		});

		subscribeEvent(ReturnVehicleEvent.class, returnVehicle -> {
			resources.releaseVehicle(returnVehicle.getVehicle());
		});


		subscribeBroadcast(TickBroadcast.class, tick -> {
			if (tick.isTermination()) {
				resources.releaseVehicle(null);
				System.out.println(this.getName() + " terminating");
				terminate();
			}
		});

		RunningCounter.getInstance().addRunningThread();


	}

}
