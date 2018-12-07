package bgu.spl.mics;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl<K, V> implements MessageBus {
	private static class SingeltonHolder {
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private ConcurrentHashMap<MicroService, LinkedList<Message>> microMap;
	private ConcurrentHashMap<MicroService, LinkedList<Class<? extends Message>>> microEventTypes;
	private ConcurrentHashMap<Class<? extends Message>, LinkedList<MicroService>> eventMap;
	private ConcurrentHashMap<Class<? extends Message>, LinkedList<MicroService>> broadcastMap;
	private ConcurrentHashMap<Class<? extends Message>, Integer> nextMicroIndexs;
	private ConcurrentHashMap<Event, Future> resolveMap;



	public static MessageBusImpl getInstance() {
		return SingeltonHolder.instance;
	}

	private MessageBusImpl(){
		microMap = new ConcurrentHashMap<>();
		eventMap = new ConcurrentHashMap<>();
		broadcastMap = new ConcurrentHashMap<>();
		nextMicroIndexs = new ConcurrentHashMap<>();
		microEventTypes = new ConcurrentHashMap<>();
		resolveMap = new ConcurrentHashMap<>();
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub
		if (microMap.containsKey(m)) {
			synchronized (eventMap) {
				eventMap.get(type).addLast(m);
				microEventTypes.get(m).addLast(type);
			}
		}
	}



	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub
		if (microMap.containsKey(m)) {
			synchronized (broadcastMap) {
				broadcastMap.get(type).addLast(m);
				microEventTypes.get(m).addLast(type);
			}
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		resolveMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub
		if (broadcastMap.get(b.getClass()) != null) {
			for (MicroService service: broadcastMap.get(b.getClass())) {
				microMap.get(service).addLast(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		Future<T> future = new Future<>();
		if (!eventMap.containsKey(e.getClass()) || eventMap.get(e).size() == 0) {
			return null;
		}
		else {
			int index = nextMicroIndexs.get(e.getClass());
			nextMicroIndexs.replace(e.getClass(),(nextMicroIndexs.get(e) + 1)%eventMap.get(e).size());
			microMap.get(eventMap.get(e.getClass()).get(index)).addLast(e);
			resolveMap.put(e, future);
			return future;
		}
	}

	@Override
	public void register(MicroService m) {
		if (!microMap.containsKey(m)) {
			microMap.put(m, new LinkedList<>());
		}

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub
		if (microEventTypes.get(m) != null) {
			for (Class<? extends Message> type: microEventTypes.get(m)) {
				eventMap.get(type).remove(m);
				broadcastMap.get(type).remove(m);
			}
		}
		microMap.remove(m);
		microEventTypes.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
			while (microMap.get(m).isEmpty()) {
				try {
					wait();
				}
				catch (InterruptedException e) {}
			}
			notifyAll();
			return microMap.get(m).removeFirst();
	}



	

}
