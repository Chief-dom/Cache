/**
 * Simple node class for double linked list 
 * @author CS221 
 *
 * @param <T> generic type of elements stored in a node
 */

public class DoubleNode<T>
{
	private DoubleNode<T> next;				// reference to next node
	private DoubleNode<T> previous;		// reference to previous node
	private T element;			// reference to object stored in node 
	
	/**
	 * Constructor - with given element 
	 * @param element - object of type T
	 */
	public DoubleNode(T element)
	{
		setElement(element);
		setNext(null);
		setPrevious(null);
	}

	/**
	 * Returns reference to next node
	 * @return - ref to DLLNode<T> object 
	 */
	public DoubleNode<T> getNext()
	{
		return next;
	}

	/**
	 * Assign reference to next node 
	 * @param next - ref to Node<T> object 
	 */
	public void setNext(DoubleNode<T> next)
	{
		this.next = next;
	}

	/**
	 * Returns reference to node stored in node 
	 * @return - ref to object of type T 
	 */
	public T getElement()
	{
		return element;
	}

	/**
	 * Sets reference to element stored at node
	 * @param element - ref to object of type T
	 */
	public void setElement(T element)
	{
		this.element = element;
	}

	/**
	 * Returns reference to previous node
	 * @return - ref to DLLNode<T> object 
	 */
	public DoubleNode<T> getPrevious()
	{
		return previous;
	}

	/**
	 * Assign reference to previous node 
	 * @param previous - ref to Node<T> object 
	 */	
	public void setPrevious(DoubleNode<T> previous)
	{
		this.previous = previous;
	}

}
