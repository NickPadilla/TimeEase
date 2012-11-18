/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import org.eclipse.core.databinding.observable.value.WritableValue;

/**
 * @author navid
 *
 */
public class Observable<T> extends WritableValue implements ISafeObservable<T>{
	
	public Observable(T initialValue) {	 
		super(initialValue,(initialValue != null ? initialValue.getClass() : null));
	}
	
	public void set(T value){
		setValue(value);
	}
	
	@SuppressWarnings("unchecked")
	public T get(){
		return (T) getValue();
	}

}
