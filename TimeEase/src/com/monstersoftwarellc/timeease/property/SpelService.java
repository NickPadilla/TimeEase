/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import java.util.Map;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.osgi.framework.Bundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;


/**
 * Provides a facade for the SPEL stuff.
 * @author navid
 *
 */
@SuppressWarnings("restriction")
@Service
public class SpelService implements BeanFactoryAware, ISpelService{

	private BeanFactory beanFactory=null;
	
	private ExpressionParser parser = new SpelExpressionParser();
	
	
	/* (non-Javadoc)
	 * @see com.goldrush.property.ISpelService#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String expression){
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setTypeLocator(new BundleTypeLocator());
		context.setBeanResolver(new BeanFactoryResolver(beanFactory));
		return parser.parseExpression(expression).getValue(context);
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.ISpelService#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String expression,Object rootObject){
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setTypeLocator(new BundleTypeLocator());
		context.setBeanResolver(new BeanFactoryResolver(beanFactory));
		context.setRootObject(rootObject);
		return parser.parseExpression(expression).getValue(context);
	}
	
	/* (non-Javadoc)
	 * @see com.goldrush.property.ISpelService#getValue(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getValue(String expression, Class<T> expectedResultType){
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setTypeLocator(new BundleTypeLocator());
		context.setBeanResolver(new BeanFactoryResolver(beanFactory));
		return parser.parseExpression(expression).getValue(context,expectedResultType);
	}
	
	/* (non-Javadoc)
	 * @see com.goldrush.property.ISpelService#getValue(java.lang.String, java.util.Map)
	 */
	public Object getValue(String expression,Map<String,Object> variables){
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setTypeLocator(new BundleTypeLocator());
		context.setBeanResolver(new BeanFactoryResolver(beanFactory));
		context.setVariables(variables);
		
		return parser.parseExpression(expression).getValue(context);
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	
	/**
	 * @author navid
	 *
	 */
	private final class BundleTypeLocator implements TypeLocator {
		@Override
		public Class<?> findType(String typename) throws EvaluationException {
			 Bundle[] bundles = InternalPlatform.getDefault().getBundleContext().getBundles();
			 Class<?> ret = null;
			 for(Bundle bundle : bundles){
				 try {
					ret = bundle.loadClass(typename);
					break;
				} catch (ClassNotFoundException e) {
					// keep trying
				}
			 }
			 if(ret == null){
				 throw new EvaluationException("Cannot find " + typename + " in any bundle.");
			 }
			return ret;
		}
	}
	
}
