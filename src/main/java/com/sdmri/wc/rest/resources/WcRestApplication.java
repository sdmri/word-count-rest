package com.sdmri.wc.rest.resources;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Register all rest resources in here
 * 
 * @author shiven.dimri
 *
 */
public class WcRestApplication  extends ResourceConfig {
	
	public WcRestApplication(){
		packages("com.sdmri.wc.rest.resources");
	}
}
