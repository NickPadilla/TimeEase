/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

/**
 * @author navid
 *
 */
public class DependantObservableBoolNegation extends DependantObservable<Boolean> {

	Observable<Boolean> delegate = null;
	
	/**
	 * @param initialValue
	 */
	public DependantObservableBoolNegation(Observable<Boolean> delegate) {
		super(true);
		this.delegate = delegate;
		init();
	}

	/* (non-Javadoc)
	 * @see com.goldrush.model.Observable#get()
	 */
	@Override
	public Boolean get() {
		return ! delegate.get();
	}
	
	

}
