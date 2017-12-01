# zingchart-websocket

I'll show you how to send live data to ZingChart using the web socket library used by Apache Tomcat.

¢º Selecting a chart
First of all, choose some complicated charts from the site as follows.
https://www.zingchart.com/gallery/chart/#!time-series-line-chart

You can add the source code you created to the current source code. 
The template of the JavaScript source code is as follows.

	var ws = new WebSocket('ws://65.50.232.201:8888/', 'zingchart');
	
	//Tell our internal server what to send.
	ws.onopen = function(){
		//console.log("########send##########");
		
		ws.send('zingchart.feed');
	    ws.send('zingchart.push');
	    ws.send('zingchart.getdata');
	}
	
	//Setup an event to call a ZingChart API Method to update our chart.
	ws.onmessage = function (e) {
		console.log("===== \n " + JSON.stringify(e.data));
		console.log("===== \n ");
		
		//var data = JSON.parse(e.data);
		var data = JSON.parse(e.data);
	    var newValue = data['plot0'][1];
	    var time = data['plot0'][0];
	    
	    //console.log("====> " + time);
		//console.log("====> " + newValue);
		
	  zingchart.exec('myChart', 'appendseriesvalues', {
	      plotindex : 0,
	      values : [[time,newValue-20]]
	  });
	  
	  var newValue2 = data['plot1'][1];
	  var time2 = data['plot1'][0];
	  
	  zingchart.exec('myChart', 'appendseriesvalues', {
	      plotindex : 1,
	      values : [[time2,newValue2+10]]
	  });
	  
	  var newValue3 = data['plot2'][1];
	  var time3 = data['plot2'][0];
	  
	  zingchart.exec('myChart', 'appendseriesvalues', {
	      plotindex : 2,
	      values : [[time3,newValue3+150]]
	  });
	  
	  var newValue4 = data['plot3'][1];
	  var time4 = data['plot3'][0];
	  zingchart.exec('myChart', 'appendseriesvalues', {
	      plotindex : 3,
	      values : [[time4,newValue4+200]]
	  });
	};
	
	ws.onclose = function(event) { 
		console.log('Client notified socket has closed',event); 
	}; 

The special feature of the above source code is that it is a special property called plotindex. 
It starts with 0 and maps the data to each data attribute.
In the original data must be like this:

	series: [{
	  values: [],
	  text: 'Sensor FC-456',
	  legendItem: {
	    backgroundColor: '#29A2CC',
	    borderRadius: 2
	  }
	}, {
	  values: [],
	  text: 'Sensor AB-265',
	  legendItem: {
	    backgroundColor: '#D31E1E',
	    borderRadius: 2
	  }
	}, {
	  values: [],
	  text: 'Sensor DC-445',
	  legendItem: {
	    backgroundColor: '#7CA82B',
	    borderRadius: 2
	  }
	}, {
	  values: [],
	  text: 'Sensor ER-985',
	  legendItem: {
	    backgroundColor: '#EF8535',
	    borderRadius: 2
	  }
	}]

Therefore, the data value provided by the existing ZingChart is put into each of four values.

The result will be ;


¢º Using Apache Tomcat Web Socket
Then use the web socket provided by Apache Tomcat to re-create the chart. 
Just make url of the web socket of apache tomcat is:

	var ws = new WebSocket('ws://localhost:8080/examples/websocket/sychart');

See the source code of this site.


To begin with, we first created the ChartAnnotaion class.
Here is the onOpen method of this class:

	@OnOpen
	public void onOpen(Session session) {
	    this.chart = new Chart(id, session);
	    DyChartTimer.addChart(chart);
	    
	    DyChartTimer.broadcast(DataGenUtil.getRandomMessage());
	}

The contents of the code adds a Chart class to the DyChartTimer class, which is a timer class, 
and calls the broadcast method in the same class. 

	protected static synchronized void addChart(Chart chart) {
	    if (charts.size() == 0) {
	        startTimer();
	    }
	    charts.put(Integer.valueOf(chart.getId()), chart);
	}


Starts the timer in the first state that currently contains nothing and inserts this class.
And the broadcast method sends it to the client.

	protected static void broadcast(String message) {
	    for (Chart chart : DyChartTimer.getCharts()) {	
	        try {
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

Finally, once this timer is started, you'll see what happens if you look at the startTimer in the class 
and it keeps calling the tick method. At this point, this method will call the broadcast method again.

	protected static void tick() {
	    broadcast(String.format("{\"type\": \"update\", \"data\" : [%s]}", ""));
	}

Originally, you need to send a message of an argument of the broadcast method, but now it sends an arbitrary value, 
so the message delivered as an argument does not have any effect. 
It is getRandomMessage method that generate the real ramdom message for clients:

	public static String getRandomMessage()
	{
		String jsonString = "{\"scale-x\":%s, \"plot0\" :[%s, %s], \"plot1\" :[%s, %s], \"plot2\" :[%s, %s], \"plot3\" :[%s, %s]}";
		String unixTime = DataGenUtil.getUnixTimeStr3();
	
		//broadcast(String.format(jsonString, unixTime, unixTime, DataGenUtil.getRandomNumberStr()));
		
		return String.format(jsonString, unixTime, 
			unixTime, DataGenUtil.getRandomNumberStr(), //plot0
			unixTime, DataGenUtil.getRandomNumberStr(), //plot1
			unixTime, DataGenUtil.getRandomNumberStr(), //plot2
			unixTime, DataGenUtil.getRandomNumberStr()  //plot3
			);
	}

It just sends out the randomly generated content. The result is
¢º Start the server

¢º Web socket example


The end result is no different from the results showned earlier.