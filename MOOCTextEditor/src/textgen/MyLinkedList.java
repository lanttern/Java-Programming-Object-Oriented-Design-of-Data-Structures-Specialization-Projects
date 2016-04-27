package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException();
		}
		LLNode<E> node = new LLNode<E>(element);
		LLNode<E> lastNode = head;
		//find last node
        for (int i = 0; i < size; i++){
				lastNode = lastNode.next;
			}
        //add new node after last node
		lastNode.next = node;
		node.next = tail;
		node.prev = lastNode;
		tail.prev = node;
		size++;
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> getNode = head;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		//find index
		else {
			for (int j = 0; j <= index; j++){
					getNode = getNode.next;
			}
			//return data at index
			return getNode.data;
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException();
		}
		LLNode<E> newNode = new LLNode<E>(element);
		LLNode<E> nextNode = head;
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		//find index
		else {
			if (index == size){
				this.add(element);
			}
			else{
				
			    for (int j = 0; j <= index; j++){
					nextNode = nextNode.next;
			}
	        //add new node before next node

			nextNode.prev.next = newNode;
			newNode.prev = nextNode.prev;
			newNode.next = nextNode;
			nextNode.prev = newNode;
			size++;
			}
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (size == 0) {
			throw new NullPointerException();
		}
		LLNode<E> nextNode = head;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		//find index
		else {
			for (int j = 0; j <= index; j++){
					nextNode = nextNode.next;
			}
	        //remove the nextNode
			nextNode.prev.next = nextNode.next;
			nextNode.next.prev = nextNode.prev;
			size--;
			}
		return nextNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		this.add(index, element);
		E data = this.remove(index+1);
		return data;
	}   
}
	
class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
