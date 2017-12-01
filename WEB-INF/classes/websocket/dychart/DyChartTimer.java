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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Sets up the timer for the multi-player snake game WebSocket example.
 */
public class DyChartTimer {

    private static final Log log =
            LogFactory.getLog(DyChartTimer.class);

    private static Timer chartTimer = null;

    private static final long TICK_DELAY = 1 * 1000;

    private static final ConcurrentHashMap<Integer, Chart> charts =
            new ConcurrentHashMap<>();

    protected static synchronized void addChart(Chart chart) {
        if (charts.size() == 0) {
        	System.out.println("start timer...");
            startTimer();
        }
        charts.put(Integer.valueOf(chart.getId()), chart);
    }

    protected static Collection<Chart> getCharts() {
        return Collections.unmodifiableCollection(charts.values());
    }


    protected static synchronized void removeChart(Chart chart) {
        charts.remove(Integer.valueOf(chart.getId()));
        if (charts.size() == 0) {
        	System.out.println("stop timer...");
            stopTimer();
        }
    }
	

    protected static void tick() {
        //StringBuilder sb = new StringBuilder();
        //for (Iterator<Chart> iterator = DyChartTimer.getCharts().iterator();
        //        iterator.hasNext();) {
        //    Chart chart = iterator.next();
        //    chart.update(DyChartTimer.getCharts());
       //     sb.append(chart.getLocationsJson());
       //     if (iterator.hasNext()) {
       //         sb.append(',');
        //    }
       // }
        //String jsonString = "{\"scale-x\":1511944933674, \"plot0\" :[1511944933674, 70]}";
        //String jsonString = "{\"scale-x\":%s, \"plot0\" :[%s, %s]}";
        //String unixTime = DataGenUtil.getUnixTimeStr();
        
        //broadcast(String.format(jsonString, unixTime, unixTime, DataGenUtil.getRandomNumberStr()));
        
        //broadcast(String.format("{\"type\": \"update\", \"data\" : [%s]}", sb.toString()));
        broadcast(String.format("{\"type\": \"update\", \"data\" : [%s]}", ""));
    }
    
    

    protected static void broadcast(String message) {
        for (Chart chart : DyChartTimer.getCharts()) {   	
            try {
                //chart.sendMessage(message);
                chart.sendMessage(DataGenUtil.getRandomMessage());
            } catch (IllegalStateException ise) {
                // An ISE can occur if an attempt is made to write to a
                // WebSocket connection after it has been closed. The
                // alternative to catching this exception is to synchronise
                // the writes to the clients along with the addSnake() and
                // removeSnake() methods that are already synchronised.
            }
        }
    }


    public static void startTimer() {
        chartTimer = new Timer(DyChartTimer.class.getSimpleName() + " Timer");
        chartTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (RuntimeException e) {
                    log.error("Caught to prevent timer from shutting down", e);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }


    public static void stopTimer() {
        if (chartTimer != null) {
            chartTimer.cancel();
        }
    }
}
