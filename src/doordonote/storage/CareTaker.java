package doordonote.storage;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Stack;

/**
 * @@author A0131716M
 *
 */

public class CareTaker {
	private static final int MAX_UNDO_SIZE = 10;
	private Deque<Memento> mementoDeque = new LinkedBlockingDeque<Memento>(MAX_UNDO_SIZE);
	private Stack<Memento> redoStack;

	public void add(Memento state){
		if(mementoDeque.size()<MAX_UNDO_SIZE){
			mementoDeque.addLast(state);
		} else{
			mementoDeque.removeFirst();
			mementoDeque.addLast(state);
		}
	}

	public Memento get(){
		if(mementoDeque.size()>0){
			return mementoDeque.peekLast();
		} else{
			return null;
		}
	}
	
	public Memento removeLast(){
		if(mementoDeque.size()>0){
			return mementoDeque.pollLast();
		} else{
			return null;
		}
	}

	public Memento restore(){
		if(redoStack.size()>0){		
			return redoStack.pop();
		} else{
			return null;
		}
	}


	public void initRedoStack(Memento memento){
		redoStack = new Stack<Memento>();
	}

	public void toRedoStack(Memento memento){
		redoStack.push(memento);
	}

}