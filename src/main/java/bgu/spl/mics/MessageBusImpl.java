package bgu.spl.mics;


import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
    private static class SingletonHolder {
        private static MessageBusImpl instance = new MessageBusImpl();
    }

    /*@microMap this HashMap holds a links between each microService to his message queue*/
    private ConcurrentHashMap<MicroService, LinkedList<Message>> microMap;
    /*
    @microEventTypes for each micro service we will link the type of events or broadcasts which the micro service wants to receive*/
    private ConcurrentHashMap<MicroService, LinkedList<Class<? extends Message>>> microEventTypes;
    /*
    @eventMap for each event type we will link which micro services need to receive it.*/
    private ConcurrentHashMap<Class<? extends Message>, LinkedList<MicroService>> eventMap;
    /*@broadcastMap for each broadcast type we will link which micro services need to receive it*/
    private ConcurrentHashMap<Class<? extends Message>, LinkedList<MicroService>> broadcastMap;
    private ConcurrentHashMap<Class<? extends Message>, Integer> nextMicroIndexs;
    private ConcurrentHashMap<Event, Future> resolveMap;


    public static MessageBusImpl getInstance() {
        return SingletonHolder.instance;
    }

    private MessageBusImpl() {
        microMap = new ConcurrentHashMap<>();
        eventMap = new ConcurrentHashMap<>();
        broadcastMap = new ConcurrentHashMap<>();
        nextMicroIndexs = new ConcurrentHashMap<>();
        microEventTypes = new ConcurrentHashMap<>();
        resolveMap = new ConcurrentHashMap<>();
    }


    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        if (microMap.containsKey(m)) {
            synchronized (eventMap) {
                LinkedList<MicroService> toAdd = new LinkedList<>(eventMap.get(type));
                toAdd.add(m);
                eventMap.replace(type, toAdd);
                LinkedList<Class<? extends Message>> toAdd2 = new LinkedList<>(microEventTypes.get(m));
                toAdd2.add(type);
                microEventTypes.replace(m, toAdd2);
            }
        }
    }


    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        if (microMap.containsKey(m)) {
            synchronized (broadcastMap) {
                LinkedList<MicroService> toAdd = new LinkedList<>(broadcastMap.get(type));
                toAdd.add(m);
                broadcastMap.replace(type, toAdd);
                LinkedList<Class<? extends Message>> toAdd2 = new LinkedList<>(microEventTypes.get(m));
                toAdd2.add(type);
                microEventTypes.replace(m, toAdd2);
            }
        }
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        resolveMap.get(e).resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        for (MicroService service : broadcastMap.get(b.getClass())) {
            microMap.get(service).add(b);
        }
    }


    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future<T> future = new Future<>();
        if (!eventMap.containsKey(e.getClass()) || eventMap.get(e).size() == 0) {
            return null;
        } else {
            int index = nextMicroIndexs.get(e.getClass());
            nextMicroIndexs.replace(e.getClass(), (nextMicroIndexs.get(e) + 1) % eventMap.get(e).size());
            microMap.get(eventMap.get(e.getClass()).get(index)).add(e);
            LinkedList<Message> toAdd = new LinkedList<>(microMap.get(eventMap.get(e.getClass()).get(index)));
            toAdd.add(e);
            microMap.replace(eventMap.get(e.getClass()).get(index), toAdd);
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
        for (Class<? extends Message> type : microEventTypes.get(m)) {
            LinkedList<MicroService> toAdd = new LinkedList<>(eventMap.get(type));
            toAdd.remove(m);
            eventMap.replace(type, toAdd);
            eventMap.get(type).remove(m);
            LinkedList<MicroService> toAdd2 = new LinkedList<>(broadcastMap.get(type));
            broadcastMap.replace(type, toAdd2);

        }
        microMap.remove(m);
        microEventTypes.remove(m);
    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {
        //while (microMap.get(m).isEmpty()) {
        //try {
        //	wait();
        //}
        //catch (InterruptedException e) {
        //}
        //return microMap.get(m).removeFirst();

        //}
        while (microMap.get(m).isEmpty()) {
            throw new InterruptedException();
        }


        return null;
    }


}
