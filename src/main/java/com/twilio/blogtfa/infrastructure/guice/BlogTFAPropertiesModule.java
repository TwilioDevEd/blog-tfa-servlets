package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

class BlogTFAPropertiesModule extends AbstractModule {
    @Override
    protected void configure() {
        Names.bindProperties(binder(), new BlogTFAProperties());
    }
}
