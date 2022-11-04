package processor.memorysystem;
import configuration.*;
import generic.*;
import generic.Event.EventType;
import processor.Clock;

public class Cache implements Element {
    CacheLine[] cacheLines;
    int BlockSize;
    int numberOfLines;
    int latency;

    public Cache(int associativity, int numberOfLines, int BlockSize, int latency) {
        this.BlockSize = BlockSize;
        this.numberOfLines = numberOfLines;
        this.latency = latency;
        cacheLines = new CacheLine[associativity];
        for (int i = 0; i < numberOfLines; i++) {
            cacheLines[i] = new CacheLine(associativity);
        }
    }

    public void cacheRead(int address, Element requestingElement) {
        int tag = address / numberOfLines;
        int index = address % numberOfLines;

        int way = cacheLines[index].getWay(tag);

        if (way != -1) {
            // Add response event to the event queue
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + latency, this, requestingElement, cacheLines[index].getData(way)));
        } else {
            handleCacheMiss(address);
        }
    }

    public void cacheWrite(int address, int data, Element requestingElement) {
        int tag = address / numberOfLines;
        int index = address % numberOfLines;

        int way = cacheLines[index].getWay(tag);

        if (way != -1) {
            cacheLines[index].setData(way, data);
			
			// Add response event to the event queue
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + latency, this, requestingElement, 0));
        } else {
            handleCacheMiss(address);
        }
    }

    public void handleCacheMiss(int address) {
        // TODO
        // 1. Read the block from main memory
        // 2. Update the cache line
        // 3. Update the TSLA

        int tag = address / numberOfLines;

        int index = address % numberOfLines;

        Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, requestingElement, address));

        


    }

    @Override
    public void handleEvent(Event e) {
        // Handle Cache Read
		if (e.getEventType() == EventType.MemoryRead)
		{
			// Get the memory request
			MemoryReadEvent event = (MemoryReadEvent) e;
			
			cacheRead(event.getAddressToReadFrom(), event.getRequestingElement());
		}

        // Handle Cache Write
        if (e.getEventType() == EventType.MemoryWrite)
        {
            // Get the memory request
            MemoryWriteEvent event = (MemoryWriteEvent) e;
            
            cacheWrite(event.getAddressToWriteTo(), event.getValue(), event.getRequestingElement());
        }
    }
}
