package ar.fi.uba.tecnicas.range;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RangeIterator<T> implements Iterator<T> {
	
	List<T> rangeList;
	Integer cursor=0;
	Integer step;
	
	public RangeIterator(Integer step, List<T> rangeList) {
		this.step=step;
		this.cursor=-step;
		this.rangeList=rangeList;
	}

	@Override
	public boolean hasNext() {
		Integer nextPosition=cursor+step;
		return nextPosition<rangeList.size();
	}

	@Override
	public T next() {
		if(!hasNext()){
			throw new NoSuchElementException();
		}
		this.cursor=cursor+step;
		return rangeList.get(cursor);
	}
	
	

}
