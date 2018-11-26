import java.util.NoSuchElementException;

/**
 * A generic cache based memory using a double linked list
 * 
 * @author Dominik Huffield
 *
 * @param <T> generic type
 */
public class Cache<T> {
	private DoubleNode<T> head, tail;
	private int capacity, occupants;
	private double hits, misses, references;
	
	
	/**
	 * Constructor for cache object using double linked list
	 * @param capacity integer value for size of cache
	 */
	public Cache(int capacity) {
		head = null;
		tail = null;
		occupants = 0;
		hits = 0;
		references = 0;
		misses = 0;
		this.capacity = capacity;
	}
	
	/**
	 * Searches list for given object, if found uses write to add object to top of list, 
	 * used to count hits in cache for accuracy of memory.
	 * @param target object being sought 
	 * @return if object is not found then null else the sought object is returned
	 */
	public T getObject(T target) {
		references++;
		if(search(target) == null) {
			misses++;
			return null;
		}
		write(target);
		hits++;
		return (target);
	}

	 /**
	  * Searches through the list for target object
	 * @param target object being sought
	 * @return if object is not found then null else the sought object is returned
	 */
	private DoubleNode<T> search(T target) {
	        DoubleNode<T> current = head;
	        
	        while (current != null) {
	            if (current.getElement().equals(target)) {
	                return current;
	            }

	            current = current.getNext();
	        }
	        return null;
	    }

	/**
	 * Adds object to front of list
	 * @param object item being added
	 */
	public void addObject(T object) {
		if(capacity == occupants) {
			removeLast();
		}
		DoubleNode<T> newNode = new DoubleNode<T>(object);
		if(tail == null) { //when list is empty
			head = tail = newNode;
		} else {
			newNode.setNext(head);
			newNode.setPrevious(null);
			head.setPrevious(newNode);
			head = newNode;
		}
		occupants++;
		
	}
	/**
	 * Removes object from rear of list
	 */
	public void removeLast() {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
			if(occupants == 1) {
				head = null;
				tail = null;
			} else {
				tail.getPrevious().setNext(null);
	            tail = tail.getPrevious();
			}
			occupants--;
			
			
		
	}
	/**
	 * Locates matching object and removes from the list
	 * @param object to be removed
	 */
	public void removeObject(T object) {
		DoubleNode<T> node = search(object);
		
		if (node == null) {
            throw new NoSuchElementException();
        }

        if (head == node) {
            head = node.getNext();
        } else {
            node.getPrevious().setNext(node.getNext());
        }

        if (tail == node) {
            tail = node.getPrevious();
        } else {
            node.getNext().setPrevious(node.getPrevious());
        }
		
        occupants--;
	}
	/**
	 * When object is in a cache this will remove 
	 * said object from the list and add it to the 
	 * front as to avoid double storing of data.
	 * @param object 
	 */
	public void write(T object) {
		if (search(object) == null) {
            throw new NoSuchElementException();
        }
		removeObject(object);
		addObject(object);
		
	}
	
	/**
	 * Clears the cache stored via double linked list
	 */
	public void clearCache() {
		head = null;
		tail = null;
		occupants = 0;
	}
	
    /**
     * @return hit quantity
     */
    public double getHits() {
    	return hits;
    }
    
    /**
     * @return miss quantity
     */
    private double getMisses() {
    	return misses;
    }
    
    /**
     * @return the amount of objects from file
     */
    public double getRefers() {
    	return references;
    }
    
    /**
     * verifies that the amount of hits and misses 
     * equate to the total number of objects 
     * @return boolean based on validity 
     */
    private boolean checkCount() {
    	
    	return(getHits() + getMisses() == getRefers());
    }
    
    /**
     * @return percentage hit from entire file
     */
    public double getHitRate() {
    	if (checkCount()) {
    		return (getHits() / getRefers());
    	} else {
    		return -1;
    	}
    }
    /**
     * @return capacity of cache
     */
    public int capacity() {
    	return capacity;
    }
	/**
	 * @return validity for emptiness of cache
	 */
	public boolean isEmpty() {
        return (head == null);
    }
}
