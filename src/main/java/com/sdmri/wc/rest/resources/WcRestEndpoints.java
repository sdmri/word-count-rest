package com.sdmri.wc.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdmri.wc.services.WcService;

/**
 * All rest methods exposed over http
 * 
 * @author shiven.dimri
 * 
 */
@Component
@Path("/api")
public class WcRestEndpoints {

	@Autowired
	WcService wcService;

	@Path("/wc")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response fetchSingleWordCount(@QueryParam("query") String word) {
		int returnedCount = 0;
		try {
			returnedCount = wcService.getWordCountFromStore(word);
		} catch (IllegalArgumentException e) {
			responseForIncorrectParameters(e.getMessage());
		} catch (Exception e) {
			return responseForServerError(e.getMessage());
		}
		return Response.ok().entity("{\"count\":" + returnedCount + "}")
				.type(MediaType.APPLICATION_JSON).build();
	}

	private Response responseForServerError(String message) {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity("{\"error\":\"" + message + "\"}")
				.type(MediaType.APPLICATION_JSON).build();
	}

	private Response responseForIncorrectParameters(String message) {
		return Response.status(Status.BAD_REQUEST)
				.entity("{\"error\":\"" + message + "\"}")
				.type(MediaType.APPLICATION_JSON).build();
	}

}
