/**
 * 
 */
package com.monstersoftwarellc.timeease.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author nicholas
 *
 */
@SuppressWarnings("unchecked")
public class RepositoryImplFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I>{

	@SuppressWarnings("rawtypes")
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

		return new RepositoryImplFactory(entityManager);
	}

	private static class RepositoryImplFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private EntityManager entityManager;

		public RepositoryImplFactory(EntityManager entityManager) {
			super(entityManager);

			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {

			return new RepositoryImpl<T>((Class<T>) metadata.getDomainType(), entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

			// The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory
			//to check for QueryDslJpaRepository's which is out of scope.
			return IRepository.class;
		}
	}
}
