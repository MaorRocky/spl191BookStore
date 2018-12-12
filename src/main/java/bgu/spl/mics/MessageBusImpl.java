package bgu.spl.mics;

import java.util.LinkedList;
import java.util.concurrent.*;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl<K, V> implements MessageBus {
    private static class SingletonHolder {
        private static MessageBusImpl instance = new MessageBusImpl();
    }

    /* Link each micro service to his messages queue*/
    private ConcurrentHashMap<MicroService, BlockingQueue<Message>> microServiceToMessagesList;
    /*Link each micro service to the types of events/broadcasts that he is subscribe to*/
    private ConcurrentHashMap<MicroService, LinkedList<Class<? extends Message>>> microServiceToMessageTypes;
    /*we Link to each event type the micro-service which can handle him*/
    private ConcurrentHashMap<Class<? extends Event>, LinkedList<MicroService>> eventTypeToMicroService;
    /*link between broadcast and the micro-services which can handle him*/
    private ConcurrentHashMap<Class<? extends Broadcast>, LinkedList<MicroService>> broadcastTypeToMicroService;
    /*Link between specific events to their future result */
    private ConcurrentHashMap<Event, Future> eventToResolveMap;


    public static MessageBusImpl getInstance() {
        return SingletonHolder.instance;
    }

    private MessageBusImpl() {
        microServiceToMessagesList = new ConcurrentHashMap<>();
        eventTypeToMicroService = new ConcurrentHashMap<>();
        broadcastTypeToMicroService = new ConcurrentHashMap<>();
        microServiceToMessageTypes = new ConcurrentHashMap<>();
        eventToResolveMap = new ConcurrentHashMap<>();
    }


    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        if (microServiceToMessagesList.containsKey(m)) {
            if (!eventTypeToMicroService.containsKey(type)) {
                eventTypeToMicroService.put(type, new LinkedList<>());
            }
            eventTypeToMicroService.get(type).addLast(m);
            if (!microServiceToMessageTypes.containsKey(m)) {
                microServiceToMessageTypes.put(m, new LinkedList<>());
            }
            microServiceToMessageTypes.get(m).addLast(type);
        }
    }


    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        if (microServiceToMessagesList.containsKey(m)) {
            if (!broadcastTypeToMicroService.containsKey(type)) {
                broadcastTypeToMicroService.put(type, new LinkedList<>());
            }
            broadcastTypeToMicroService.get(type).addLast(m);
            if (!microServiceToMessageTypes.containsKey(m)) {
                microServiceToMessageTypes.put(m, new LinkedList<>());
            }
            microServiceToMessageTypes.get(m).addLast(type);
        }
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        synchronized (eventToResolveMap) {
            eventToResolveMap.get(e).resolve(result);
        }
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        synchronized (broadcastTypeToMicroService.get(b.getClass())) {
            if (broadcastTypeToMicroService.containsKey(b.getClass())) {
                for (MicroService service : broadcastTypeToMicroService.get(b.getClass())) {
                    microServiceToMessagesList.get(service).add(b);
                }
            }
        }
    }


    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future<T> future = new Future<>();
        synchronized (eventTypeToMicroService.get(e.getClass())) {
            if (!eventTypeToMicroService.containsKey(e.getClass()) || eventTypeToMicroService.get(e.getClass()).isEmpty()) {
                return null;
            } else {
                synchronized (eventToResolveMap) {
                    MicroService next = eventTypeToMicroService.get(e.getClass()).removeFirst();
                    microServiceToMessagesList.get(next).add(e);
                    eventTypeToMicroService.get(e.getClass()).addLast(next);
                    eventToResolveMap.put(e, future);
                    return future;
                }
            }
        }
    }

    @Override
    public void register(MicroService m) {
        if (!microServiceToMessagesList.containsKey(m)) {
            microServiceToMessagesList.put(m, new LinkedBlockingQueue<>());
        }

    }

    @Override
    public void unregister(MicroService m) {
        if (microServiceToMessageTypes.containsKey(m)) {// if m exist than he has a messageQueue
            /*we will resolve all of his messages in his message queue to null*/

            if (!microServiceToMessagesList.get(m).isEmpty()) {
                for (Message message : microServiceToMessagesList.get(m)) {
                    eventToResolveMap.get(message).resolve(null);
                }
            }

            for (Class<? extends Message> type : microServiceToMessageTypes.get(m)) {
                if (eventTypeToMicroService.containsKey(type)) {
                    synchronized (eventTypeToMicroService.get(type)) {
                        eventTypeToMicroService.get(type).remove(m);
                    }
                }
                if (broadcastTypeToMicroService.containsKey(type)) {
                    synchronized (broadcastTypeToMicroService.get(type)) {
                        broadcastTypeToMicroService.get(type).remove(m);
                    }
                }
            }
            microServiceToMessagesList.remove(m);
            microServiceToMessageTypes.remove(m);
        }
    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {
        return microServiceToMessagesList.get(m).take();
    }


}
