package be.vinci.pae.utils;

import be.vinci.pae.domain.NewsFactory;
import be.vinci.pae.domain.NewsFactoryImpl;
import be.vinci.pae.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserFactoryImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
		bind(DataServiceUserCollectionImpl.class).to(DataServiceUserCollection.class).in(Singleton.class);
		bind(NewsFactoryImpl.class).to(NewsFactory.class).in(Singleton.class);
		bind(DataServiceNewsCollectionImpl.class).to(DataServiceNewsCollection.class).in(Singleton.class);
	}
}
