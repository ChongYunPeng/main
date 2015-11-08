//@@author A0131716M
package doordonote.storage;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Stack;

/**
 * @author A0131716M
 *
 */
public class CareTaker {
	private static final int MAX_UNDO_SIZE = 10;
	private Deque<Memento> mementoDeque = new LinkedBlockingDeque<Memento>(MAX_UNDO_SIZE);
	private Stack<Memento> redoStack;

	protected void add(Memento state){
		if(mementoDeque.size()<MAX_UNDO_SIZE){
			mementoDeque.addLast(state);
		} else{
			mementoDeque.removeFirst();
			mementoDeque.addLast(state);
		}
	}

	protected Memento get(){
		if(mementoDeque.size()>0){
			return mementoDeque.peekLast();
		} else{
			return null;
		}
	}
	
	protected Memento removeLast(){
		if(mementoDeque.size()>0){
			return mementoDeque.pollLast();
		} else{
			return null;
		}
	}

	protected Memento restore(){
		if(redoStack.size()>0){		
			return redoStack.pop();
		} else{
			return null;
		}
	}
	
	protected void resetUndo(){
		mementoDeque = new LinkedBlockingDeque<Memento>(MAX_UNDO_SIZE);
	}

	protected void initRedoStack(Memento memento){
		redoStack = new Stack<Memento>();
	}

	protected void toRedoStack(Memento memento){
		redoStack.push(memento);
	}

}