/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.monstersoftwarellc.timeease.dao.IPropertyDAO;
import com.monstersoftwarellc.timeease.model.impl.Property;
import com.monstersoftwarellc.timeease.property.annotations.PropertyChoice;
import com.monstersoftwarellc.timeease.property.annotations.PropertyDefault;
import com.monstersoftwarellc.timeease.property.annotations.PropertyHidden;
import com.monstersoftwarellc.timeease.property.annotations.PropertyLabel;
import com.monstersoftwarellc.timeease.property.annotations.PropertyListType;
import com.monstersoftwarellc.timeease.property.annotations.PropertySequence;
import com.monstersoftwarellc.timeease.property.annotations.PropertyShared;
import com.monstersoftwarellc.timeease.property.annotations.PropertyUiCustomize;
import com.monstersoftwarellc.timeease.service.ServiceLocator;
import com.google.common.collect.Ordering;

/**
 * Handler for all property access
 */

class PropertyProxyInvocationHandler implements InvocationHandler, IPropertyProxy {
	
	private static final IPropertyDAO propertyDAO = ServiceLocator.locateCurrent(IPropertyDAO.class);
	
	private static final ISpelService spelService = ServiceLocator.locateCurrent(ISpelService.class);

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private Class<?> propertyClass;
	
	private Map<String, IPropertyMetadata> propertyMetaMap = new HashMap<String, IPropertyMetadata>();
	
	private List<IPropertyMetadata> sortedMetadata = null;

	private static Logger LOG = Logger.getLogger(PropertyService.class);

	private static final Map<Class<?>, Object> DEFAULTS;

	static {
		Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
		put(map, boolean.class, false);
		put(map, char.class, '\0');
		put(map, byte.class, (byte) 0);
		put(map, short.class, (short) 0);
		put(map, int.class, 0);
		put(map, long.class, 0L);
		put(map, float.class, 0f);
		put(map, double.class, 0d);
		DEFAULTS = Collections.unmodifiableMap(map);
	}

	private static <T> void put(Map<Class<?>, Object> map, Class<T> type, T value) {
		map.put(type, value);
	}
	
	/**
	 * @param propertyClass
	 * @param propertyService TODO
	 */
	public PropertyProxyInvocationHandler(Class<?> propertyClass) {
		super();
		this.propertyClass = propertyClass;
		
		// build metedata and validate method naming conventions.
		Method[] methods = propertyClass.getMethods();
		for(Method method : methods){
			String name = method.getName();
			boolean getter = false;
			boolean setter = false;
			// validate interface methods
			if(!(getter = isGetter(name)) && !(setter = isSetter(name))){
				throw new IllegalArgumentException("Clazz methods must follow standard Java Bean naming conventions.");
			}
			String methodName = method.getName();
			String className = propertyClass.getName();
			String propertyName = getPropertyName(methodName);
			String longName = className + "." + propertyName;
			
			PropertyMetadata metadata = (PropertyMetadata)propertyMetaMap.get(propertyName);
			if(metadata == null){
				metadata = new PropertyMetadata();
				metadata.setName(propertyName);
				metadata.setLongName(longName);
				propertyMetaMap.put(propertyName, metadata);
			}
			
			// set any annotation based configuration
			if(method.isAnnotationPresent(PropertyShared.class)){
				PropertyShared annotation = method.getAnnotation(PropertyShared.class);
				if(metadata.shared != null){
					LOG.warn("PropertyShared is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setShared(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertyDefault.class)){
				PropertyDefault annotation = method.getAnnotation(PropertyDefault.class);
				if(metadata.getDefaultExpression() != null){
					LOG.warn("PropertyDefault is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setDefaultExpression(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertyChoice.class)){
				PropertyChoice annotation = method.getAnnotation(PropertyChoice.class);
				if(metadata.getChoiceExpression() != null){
					LOG.warn("PropertyChoice is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setChoiceExpression(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertyLabel.class)){
				PropertyLabel annotation = method.getAnnotation(PropertyLabel.class);
				if(metadata.label != null){
					LOG.warn("PropertyLabel is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setLabel(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertySequence.class)){
				PropertySequence annotation = method.getAnnotation(PropertySequence.class);
				if(metadata.getSequence() != null){
					LOG.warn("PropertySequence is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setSequence(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertyUiCustomize.class)){
				PropertyUiCustomize annotation = method.getAnnotation(PropertyUiCustomize.class);
				if(metadata.getUiCustomizations() != null && metadata.getUiCustomizations().length >0){
					LOG.warn("PropertyUiCustomize is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setUiCustomizations(annotation.value());
			}
			
			if(method.isAnnotationPresent(PropertyHidden.class)){
				if(metadata.hidden != null){
					LOG.warn("PropertyHidden is set on both the getter and setter for " + longName);
				}
				metadata.setHidden(true);
			}
			
			if(method.isAnnotationPresent(PropertyListType.class)){
				PropertyListType annotation = method.getAnnotation(PropertyListType.class);
				if(metadata.getType() != null){
					LOG.warn("PropertyListType is set on both the getter and setter for " + longName + ". Will be set to " + annotation.value());
				}
				metadata.setListType(annotation.value());
			}
			
			if(getter){
				if(metadata.getType() == null){
					metadata.setType(method.getReturnType());
				}
				metadata.setHasGetter(true);
			}else if(setter){
				metadata.setHasSetter(true);
			}	
		}
		
		for(IPropertyMetadata temp : propertyMetaMap.values()){
			PropertyMetadata meta = (PropertyMetadata) temp;
			if(!meta.isHasGetter() || !meta.isHasSetter()){
				throw new IllegalArgumentException("All property values must have a matching getter and setter. Missing for " +meta.getLongName());
			}
		}
		
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		Class<?> invokedInterface = method.getDeclaringClass();
		Object ret = null;
		
		// make sure interface and object invocations are handled properly. 
		if(invokedInterface == Object.class){
			Method oMEthod = Object.class.getMethod(method.getName(), method.getParameterTypes());
			ret = oMEthod.invoke(this, args);
		}else if(invokedInterface.isAssignableFrom(propertyClass)){	
			String methodName = method.getName();
			String propertyName = getPropertyName(methodName);

			if(isSetter(methodName)){
				setPropertyValue(getPropertyMeta(propertyName), args[0]);
			}else{
				ret = getPropertyValue(getPropertyMeta(propertyName));
			}
		}else if(invokedInterface.isAssignableFrom(IPropertyProxy.class)){
			// delegate IPropertyProxy methods to self.
			Method piMethod = IPropertyProxy.class.getMethod(method.getName(), method.getParameterTypes());
			ret = piMethod.invoke(this, args);
		}else{
		
			// delegate IPropertyChangeSupport calls to internal PropertyChangeSupport class.
			// all proxies created by this service automatically implement IPropertyChangeSupport
			Method pcsMethod = PropertyChangeSupport.class.getMethod(method.getName(), method.getParameterTypes());
			ret = pcsMethod.invoke(propertyChangeSupport, args);
		}

		return ret;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadataInspector#getPropertyMetas()
	 */
	@Override
	public List<IPropertyMetadata> getPropertyMetas() {
		// sort lazily if requested
		if(sortedMetadata == null){
			Ordering<IPropertyMetadata> orderingMeta = new Ordering<IPropertyMetadata>() {
				
				@Override
				public int compare(IPropertyMetadata arg0, IPropertyMetadata arg1) {
					int ret = 0;
					if(arg0.getSequence() != null && arg1.getSequence() != null){
						ret = arg0.getSequence().compareTo(arg1.getSequence());
					}else if(arg0.getSequence() != null){
						ret = -1;
					}else{
						ret = arg0.getLabel().compareTo(arg1.getLabel());
					}
					return ret;
				}
			};
			sortedMetadata = orderingMeta.sortedCopy(propertyMetaMap.values());
		}
		return sortedMetadata;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadataInspector#getPropertyMeta(java.lang.String)
	 */
	@Override
	public IPropertyMetadata getPropertyMeta(String name) {
		return propertyMetaMap.get(name);
	}
	

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadataInspector#getProperty(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(IPropertyMetadata meta) {
		Object ret = null;
		PropertyMetadata metadata = (PropertyMetadata)meta;
		Property property = findProperty(metadata);
		ret = property.getValue();
		// get default value if set
		if(ret == null && metadata.getDefaultExpression() != null){
			ret = spelService.getValue(metadata.getDefaultExpression());
		}
		
		// for primitive types we cannot return null
		if(ret == null && metadata.getType().isPrimitive()){
			ret = defaultValue(metadata.getType());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadataInspector#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(IPropertyMetadata meta, Object value) {
		Property property = findProperty((PropertyMetadata)meta);
		Object oldValue = property.getValue();
		
		property.setValue(value);
		propertyChangeSupport.firePropertyChange(meta.getName(), oldValue, value);
		
		propertyDAO.merge(property);
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyProxy#getChoice(com.goldrush.property.IPropertyMetadata)
	 */
	@Override
	public Object getChoice(IPropertyMetadata meta) {
		Object ret = null;
		if(meta.hasChoice()){
			ret = spelService.getValue(((PropertyMetadata)meta).getChoiceExpression());
		}
		return ret;
	}
	
	private Property findProperty(PropertyMetadata meta){
		Property property = propertyDAO.findByName(meta.getLongName());
		
		if(property == null){
			property = new Property();
			property.setPropertyName(meta.getLongName());
			// add the property since it doesn't exist in the db
			propertyDAO.persist(property);
			property = null;
			property = propertyDAO.findByName(meta.getLongName());
		}
		
		property.setPropertyShared(meta.isShared());
		
		return property;
	}
	
	/**
	 * Returns the default value of {@code type} as defined by JLS --- {@code 0} for numbers, {@code
	 * false} for {@code boolean} and {@code '\0'} for {@code char}. For non-primitive types and
	 * {@code void}, null is returned.
	 */
	@SuppressWarnings("unchecked")
	private <T> T defaultValue(Class<T> type) {
		return (T) DEFAULTS.get(type);
	}

	private String getPropertyName(String methodName){
		String name = methodName;
		if(name.startsWith("get")){
			name = name.replaceFirst("get", "");
		}else if(name.startsWith("set")){
			name = name.replaceFirst("set", "");
		}else if(name.startsWith("is")){
			name = name.replaceFirst("is", "");
		}	
		return StringUtils.uncapitalize(name);
	}

	private boolean isSetter(String methodName){
		boolean ret = false;
		if(methodName.startsWith("set")){
			ret = true;
		}
		return ret;
	}

	private boolean isGetter(String methodName){
		boolean ret = false;
		if(methodName.startsWith("is") || methodName.startsWith("get")){
			ret = true;
		}
		return ret;
	}
}