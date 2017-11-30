/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package websocket.chart;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

public class ChartEndpoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    	System.err.println("###### on open #########");
        RemoteEndpoint.Basic remoteEndpointBasic = session.getBasicRemote();
        session.addMessageHandler(new ChartMessageHandlerText(remoteEndpointBasic));
        session.addMessageHandler(new ChartMessageHandlerBinary(remoteEndpointBasic));
    }

    private static class ChartMessageHandlerText
            implements MessageHandler.Partial<String> {

        private final RemoteEndpoint.Basic remoteEndpointBasic;

        private ChartMessageHandlerText(RemoteEndpoint.Basic remoteEndpointBasic) {
            this.remoteEndpointBasic = remoteEndpointBasic;
        }

        @Override
        public void onMessage(String message, boolean last) {
        	System.err.println("###### onMessage[ChartMessageHandlerText] #########");
            try {
                if (remoteEndpointBasic != null) {
                	String jsonString = "{\"scale-x\":1511944933674, \"plot0\" :[1511944933674, 70]}";
                	remoteEndpointBasic.sendText(jsonString, last);
                	jsonString = "{\"scale-x\":1511944933684, \"plot0\" :[1511944933674, 60]}";
                	remoteEndpointBasic.sendText(jsonString, last);
                	
                	jsonString = "{\"scale-x\":1511947597849,\"plot0\":[1511947597849,54]}";
                	remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947598349,\"plot0\":[1511947598349,11]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947598851,\"plot0\":[1511947598851,44]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947599353,\"plot0\":[1511947599353,106]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947599854,\"plot0\":[1511947599854,61]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947600355,\"plot0\":[1511947600355,50]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947600856,\"plot0\":[1511947600856,18]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947601357,\"plot0\":[1511947601357,13]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947601857,\"plot0\":[1511947601857,10]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947602359,\"plot0\":[1511947602359,87]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947602861,\"plot0\":[1511947602861,56]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947603362,\"plot0\":[1511947603362,17]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947603863,\"plot0\":[1511947603863,34]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947604364,\"plot0\":[1511947604364,83]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947604865,\"plot0\":[1511947604865,84]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947605366,\"plot0\":[1511947605366,77]}";
					remoteEndpointBasic.sendText(jsonString, last);
					jsonString = "{\"scale-x\":1511947605867,\"plot0\":[1511947605867,19]}";
					remoteEndpointBasic.sendText(jsonString, last);
                	
                	System.err.println("###### "+message+ " isLast? " + last + "#########");
                }
                else
                {
                	System.err.println("###### onMessage[ChartMessageHandlerText] remoteEndpointBasic is NULL #########");
                }
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static class ChartMessageHandlerBinary
            implements MessageHandler.Partial<ByteBuffer> {

        private final RemoteEndpoint.Basic remoteEndpointBasic;

        private ChartMessageHandlerBinary(RemoteEndpoint.Basic remoteEndpointBasic) {
            this.remoteEndpointBasic = remoteEndpointBasic;
        }

        @Override
        public void onMessage(ByteBuffer message, boolean last) {
        	System.err.println("###### onMessage[ChartMessageHandlerBinary] #########");
            try {
                if (remoteEndpointBasic != null) {
                    remoteEndpointBasic.sendBinary(message, last);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
