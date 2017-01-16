/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.uuf.httpconnector.msf4j;

import org.wso2.carbon.uuf.api.Server;
import org.wso2.msf4j.Microservice;
import org.wso2.msf4j.Request;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * UUF Connector for MSF4J.
 */
public class UUFMicroservice implements Microservice {

    private Server uufServer;
    private static final String PATH_ROOT = "";
    private static final String PATH_ALL = ".*";

    public UUFMicroservice(Server uufServer) {
        this.uufServer = uufServer;
    }

    @GET
    @Path(PATH_ALL)
    public Response get(@Context Request request) {
        return getImpl(request);
    }

    @GET
    @Path(PATH_ROOT)
    public Response getImpl(@Context Request request) {
        MicroserviceHttpRequest httpRequest = new MicroserviceHttpRequest(request);
        MicroserviceHttpResponse httpResponse = new MicroserviceHttpResponse();
        uufServer.serve(httpRequest, httpResponse);
        return httpResponse.build();
    }

    @POST
    @Path(PATH_ALL)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postJsonAll(@Context Request request, Object params) {
        return postJsonRoot(request, params);
    }

    @POST
    @Path(PATH_ROOT)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postJsonRoot(@Context Request request, Object params) {
        return postImpl(request, null, params);
    }

    @POST
    @Path(PATH_ALL)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response postFormAll(@Context Request request, @Context MultivaluedMap multivaluedMap) {
        return postFormRoot(request, multivaluedMap);
    }

    @POST
    @Path(PATH_ROOT)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response postFormRoot(@Context Request request, @Context MultivaluedMap multivaluedMap) {
        return postImpl(request, multivaluedMap, null);
    }

    private Response postImpl(Request request, MultivaluedMap<String, ?> multivaluedMap, Object params) {
        MicroserviceHttpRequest httpRequest = new MicroserviceHttpRequest(request, multivaluedMap, params);
        MicroserviceHttpResponse httpResponse = new MicroserviceHttpResponse();
        uufServer.serve(httpRequest, httpResponse);
        return httpResponse.build();
    }
}
