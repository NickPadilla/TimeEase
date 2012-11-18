/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

/**
 * @author navid
 *
 */
public class DependantObservableBoolAnd extends DependantObservable<Boolean> {

	
	protected ISafeObservable<Boolean> delegates[] = null;
	
	/**
	 * @param initialValue
	 */
	public DependantObservableBoolAnd(ISafeObservable<Boolean> ... delegates) {
		super(true);
		this.delegates = delegates;
		init();
	}
	
	
	/* (non-Javadoc)
	 * @see com.goldrush.model.DependantObservable#get()
	 */
	@Override
	public Boolean get() {
		// return true if all of the delegates are true.
		boolean ret = true;
		for(ISafeObservable<Boolean> observable : delegates){
			if(!observable.get()){
				ret = false;
				// the first time all delegates must be accessed so dependancy is established. All other times we can short circuit on first false val.
				if(isDependenciesEstablished()){
					break;
				}
			}
		}
		return ret;
	}

}
