package bgu.spl.mics.application.services;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.Events.*;
import bgu.spl.mics.MicroService;

/**
 * Logistic service in charge of delivering books that have been purchased to customers.
 * Handles {@link DeliveryEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LogisticsService extends MicroService {

	public LogisticsService(String name) {
		super(name);
	}

	@Override
	protected void initialize() {
		this.subscribeEvent(DeliveryEvent.class, delivery -> {
			Future<Future<DeliveryVehicle>> future = sendEvent(new SendDeliveryEvent(delivery.getAddress()));
			if (future != null) {
				Future<DeliveryVehicle> vehicle = future.get();
				if (vehicle != null && vehicle.get() != null) {
					System.out.println("got vehicle " + vehicle.get().getLicense());
					vehicle.get().deliver(delivery.getCustomer().getAddress(), delivery.getCustomer().getDistance());
					sendEvent(new ReturnVehicleEvent(vehicle.get()));
				}
			}
		});

		subscribeBroadcast(TickBroadcast.class, tick -> {
			if (tick.isTermination()) {
				System.out.println(this.getName() + " terminating");
				terminate();
			}
		});
		RunningCounter.getInstance().addRunningThread();
	}
}
