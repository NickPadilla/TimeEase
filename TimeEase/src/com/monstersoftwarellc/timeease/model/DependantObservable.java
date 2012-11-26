/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;

/**
 * Dependent observables to automatically update if any of the observables called during get change.
 * NOTE: There is one caveat all observables depended on must be accessed on the first call to get. 
 * 		 Any observable not called on the first call will not be monitored for value change events.
 * @author navid
 *
 */
public abstract class DependantObservable<T> extends AbstractObservableValue implements ISafeObservable<T> {

	private Object lastValue = null;
	
	private boolean dependenciesEstablished = false;
	
	public abstract T get();
	
	
	public DependantObservable() {
		super();
		init();
	}
	
	/**
	 * Allows subclasses to initialize manually after construction.
	 * TODO: find better way violates open closed principle
	 * @param manualInit if true init must be called manually by child class at end of constructor. 
	 */
	protected DependantObservable(boolean manualInit){
		if(!manualInit){
			init();
		}
	}

	protected void init(){
		lastValue = getValue();
	}
	
	@Override
	public Object getValueType() {
		return (lastValue != null ? lastValue.getClass() : null);
	}
	
	@Override
	public Object doGetValue() {
		//TODO: make this auto adjust to changes in observable dependencies
		// for now we only init once for performance reasons since wasnt working the other way anyways.
		if(!dependenciesEstablished){
			final AtomicReference<Object> ret = new AtomicReference<Object>();
			
			ObservableTracker.runAndMonitor(new Runnable() {	
				@Override
				public void run() {
					ret.set(get());	
				}
			}, new IChangeListener() {
				@Override
				public void handleChange(ChangeEvent event) {
					Object value = get();
					if (lastValue != value) {
						fireValueChange(Diffs.createValueDiff(lastValue, lastValue = value));
				    }
				}
			}, new IStaleListener() {			
				@Override
				public void handleStale(StaleEvent staleEvent) {
					fireStale();
				}
			});
			dependenciesEstablished = true;
			return ret.get();
		}else{
			return get();
		}
	}

	/**
	 * @return true if all dependencies for this observable have been established. false if this observable still does not know what it is parent on.
	 */
	public boolean isDependenciesEstablished(){
		return dependenciesEstablished;
	}

}
