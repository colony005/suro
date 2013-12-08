package com.netflix.suro;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.netflix.suro.routing.Filter;
import com.netflix.suro.sink.Sink;
import com.netflix.suro.sink.notice.Notice;
import com.netflix.suro.sink.remotefile.RemotePrefixFormatter;
import org.apache.log4j.Logger;

/**
 * Guice based suro plugin with convenience methods for adding pluggable components
 * using Guice's MapBinder.
 * 
 * @author elandau
 *
 */
public abstract class SuroPlugin extends AbstractModule {
    private static final Logger LOG = Logger.getLogger(SuroPlugin.class);
    
    /**
     * Add a sink implementation to Suro.  typeName is the expected value of the
     * 'type' field of a JSON configuration.
     * 
     * @param typeName
     * @param sinkClass
     */
    public <T extends Sink> void addSinkType(String typeName, Class<T> sinkClass) {
        LOG.info("Adding sinkType: " + typeName + " -> " + sinkClass.getCanonicalName());
        
        Multibinder<TypeHolder> bindings
            = Multibinder.newSetBinder(binder(), TypeHolder.class);
        bindings.addBinding().toInstance(new TypeHolder(typeName, sinkClass));
    }

    public <T extends RemotePrefixFormatter> void addRemotePrefixFormatterType(String typeName, Class<T> remotePrefixFormatterClass) {
        LOG.info("Adding remotePrefixFormatterType: " + typeName + " -> " + remotePrefixFormatterClass.getCanonicalName());

        Multibinder<TypeHolder> bindings
                = Multibinder.newSetBinder(binder(), TypeHolder.class);
        bindings.addBinding().toInstance(new TypeHolder(typeName, remotePrefixFormatterClass));
    }

    public <T extends Notice> void addNoticeType(String typeName, Class<T> noticeClass) {
        LOG.info("Adding notice: " + typeName + "->" + noticeClass.getCanonicalName());

        Multibinder<TypeHolder> bindings
                = Multibinder.newSetBinder(binder(), TypeHolder.class);
        bindings.addBinding().toInstance(new TypeHolder(typeName, noticeClass));
    }

    public <T extends Filter> void addFilterType(String typeName, Class<T> filterClass) {
        LOG.info("Adding filterType: " + typeName + "->" + filterClass.getCanonicalName());

        Multibinder<TypeHolder> bindings
                = Multibinder.newSetBinder(binder(), TypeHolder.class);
        bindings.addBinding().toInstance(new TypeHolder(typeName, filterClass));
    }
}
