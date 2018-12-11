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

	public LogisticsService() {
		super("LogisticService");
	}

	@Override
	protected void initialize() {
		this.subscribeEvent(DeliveryEvent.class, delivery -> {
			Future<DeliveryVehicle> future = sendEvent(new SendDelivery(delivery.getAddress()));
			DeliveryVehicle vehicle = future.get();
			vehicle.deliver(delivery.getCustomer().getAddress(), delivery.getCustomer().getDistance());
		});
		
	}

}
