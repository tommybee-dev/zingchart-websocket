/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package websocket.dychart;

import java.awt.Color;
import java.io.EOFException;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/sychart")
public class DyChartAnnotation {

//    public static final int PLAYFIELD_WIDTH = 640;
//    public static final int PLAYFIELD_HEIGHT = 480;
//    public static final int GRID_SIZE = 10;

    private static final AtomicInteger chartIds = new AtomicInteger(0);
    private static final Random random = new Random();


    private final int id;
    private Chart chart;

/*
    public static String getRandomHexColor() {
        float hue = random.nextFloat();
        // sat between 0.1 and 0.3
        float saturation = (random.nextInt(2000) + 1000) / 10000f;
        float luminance = 0.9f;
        Color color = Color.getHSBColor(hue, saturation, luminance);
        return '#' + Integer.toHexString(
                (color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }


    public static Location getRandomLocation() {
        int x = roundByGridSize(random.nextInt(PLAYFIELD_WIDTH));
        int y = roundByGridSize(random.nextInt(PLAYFIELD_HEIGHT));
        return new Location(x, y);
    }


    private static int roundByGridSize(int value) {
        value = value + (GRID_SIZE / 2);
        value = value / GRID_SIZE;
        value = value * GRID_SIZE;
        return value;
    }
*/
    public DyChartAnnotation() {
        this.id = chartIds.getAndIncrement();
    }


    @OnOpen
    public void onOpen(Session session) {
        this.chart = new Chart(id, session);
        DyChartTimer.addChart(chart);
        
        //StringBuilder sb = new StringBuilder();
        //for (Iterator<Chart> iterator = DyChartTimer.getSnakes().iterator();
        //        iterator.hasNext();) {
        //    Chart chart = iterator.next();
        //    sb.append(String.format("{\"id\": %d, \"color\": \"%s\"}",
        //            Integer.valueOf(chart.getId()), chart.getHexColor()));
       //     if (iterator.hasNext()) {
        //        sb.append(',');
        //    }
        //}
       
        
        //DyChartTimer.broadcast(String.format("{\"type\": \"join\",\"data\":[%s]}",
        //        sb.toString()));
        //String jsonString = "{\"scale-x\":1511944933674, \"plot0\" :[1511944933674, 70]}";
        // DyChartTimer.broadcast(jsonString);
        
        //String jsonString = "{\"scale-x\":%s, \"plot0\" :[%s, %s]}";
        //String unixTime = DataGenUtil.getUnixTimeStr();
        
        //DyChartTimer.broadcast(String.format(jsonString, unixTime, unixTime, DataGenUtil.getRandomNumberStr()));
        DyChartTimer.broadcast(DataGenUtil.getRandomMessage());
    }


    @OnMessage
    public void onTextMessage(String message) {
    	System.out.println("...onTextMessage....["+message+"]");
    	
//        if ("west".equals(message)) {
//            chart.setDirection(Direction.WEST);
//        } else if ("north".equals(message)) {
//            chart.setDirection(Direction.NORTH);
//        } else if ("east".equals(message)) {
//            chart.setDirection(Direction.EAST);
//        } else if ("south".equals(message)) {
//            chart.setDirection(Direction.SOUTH);
//        }
    }


    @OnClose
    public void onClose() {
    	System.out.println("...onClose....[]");
        DyChartTimer.removeChart(chart);
        //DyChartTimer.broadcast(String.format("{\"type\": \"leave\", \"id\": %d}",
        //       Integer.valueOf(id)));
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        // Most likely cause is a user closing their browser. Check to see if
        // the root cause is EOF and if it is ignore it.
        // Protect against infinite loops.
        int count = 0;
        Throwable root = t;
        while (root.getCause() != null && count < 20) {
            root = root.getCause();
            count ++;
        }
        if (root instanceof EOFException) {
            // Assume this is triggered by the user closing their browser and
            // ignore it.
        } else {
            throw t;
        }
    }
}
