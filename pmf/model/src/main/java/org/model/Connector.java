package org.model;

import gnu.io.*; // RXTX

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.TooManyListenersException;


public class Connector implements SerialPortEventListener {

	private Model model;
	
	private byte[] datas = new byte[4];
	
    //static CommPortIdentifier SerialPortId;
    private CommPortIdentifier curentPortID;

    //SerialPort serialPort;
    private Enumeration<CommPortIdentifier> enumComm;

    //Serial port
    private SerialPort serialPort;

    //Map the port names to CommPortIdentifiers
	private HashMap<String,CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();

    //Connection status
    private boolean connected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //input and output streams for sending and receiving data
    private BufferedReader input = null;
    private OutputStream output = null;

    //String containing Arduino's informations
    private String arduinoInfos = "";

    //Log
    private String textLog = "";

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;

    public Connector(Model model){
    	this.model = model;
    }

    /**
     * Search the availables ports
     */
    @SuppressWarnings("unchecked")
	public ArrayList<String> searchForPort(){

    	ArrayList<String> portsAvailable = new ArrayList<String>();
    	
    	this.enumComm = CommPortIdentifier.getPortIdentifiers();
        while (enumComm.hasMoreElements()) {
            curentPortID = (CommPortIdentifier) enumComm.nextElement();
            if(curentPortID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portMap.put(curentPortID.getName(), curentPortID);
                portsAvailable.add(curentPortID.getName());
            }
        }
        if(portsAvailable.isEmpty()){
        	portsAvailable.add("null");
        }
        
        return portsAvailable;
    }
    
    @SuppressWarnings("unchecked")
	public void searchPort(){
    	
        this.enumComm = CommPortIdentifier.getPortIdentifiers();
        Date date = new Date();
        this.setTextLog(this.getTextLog() + date.toString()+": Searching availables ports\n");
        while (enumComm.hasMoreElements()) {
            curentPortID = (CommPortIdentifier) enumComm.nextElement();
            if(curentPortID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portMap.put(curentPortID.getName(), curentPortID);
            }
        }
    }

    /**
     * Connection
     * @param port
     *  port com identifier
     */
    public void connect(String port){

        CommPort commPort = null;
        try
        {
            this.curentPortID = (CommPortIdentifier) this.portMap.get(port);
            //the method below returns an object of type CommPort
            commPort = this.curentPortID.open("TigerControlPanel", TIMEOUT);
            //the CommPort object can be casted to a SerialPort object
            this.serialPort = (SerialPort)commPort;

            setConnected(true);
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": Connection successful\n");
        }
        catch (PortInUseException e)
        {
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": Connection timeout\n");
        }
    }

    /**
     * Initialize the IO stream
     * @return success
     *  boolean
     */
    public boolean initIOStream()
    {
        //return value for whether opening the streams is successful or not
        boolean successful = false;

        try {
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();
            writeData(0);

            successful = true;
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": I/O Streams openning successful\n");
            return successful;
        }
        catch (IOException e) {
             System.out.println("I/O Streams failed to open. (" + e.toString() + ")");
            return successful;
        }
    }

    /**
     * Initialize listener
     */
    public void initListener()
    {
        try
        {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": Init Listener\n");
        }
        catch (TooManyListenersException e)
        {
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+"Too many listeners. (" + e.toString() +")\n");
        }
    }

    /**
     * Disconnect the serial port
     */
    public void disconnect()
    {
        //close the serial port
        try
        {
            writeData(0);

            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": Disconnection successful\n");
            setConnected(false);
        }
        catch (Exception e)
        {
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+"Failed to close " + serialPort.getName()
                    + "(" + e.toString() + ")\n");
        }
    }

    /**
     * Write data in the serial port buffer
     * @param control
     *  data to send
     */
    public void writeData(int control)
    {
        try
        {
            output.write(control);
            output.flush();
            //will be read as a byte so it is a space key
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+": Datas sent\n");
        }
        catch (Exception e)
        {
            Date date = new Date();
            this.setTextLog(this.getTextLog() + date.toString()+"Failed to write data. (" + e.toString() + ")\n");
        }
    }

    /**
     * get the data recieved
     * @param event
     *  Event
     */
    public synchronized void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
            	String datas = new String();
                if(input.ready()){
                	datas = input.readLine();
                	this.model.readDatas(datas);
                }

            }
            catch (Exception e)
            {
                Date date = new Date();
                this.setTextLog(this.getTextLog() + date.toString()+"Failed to read data. (" + e.toString() + ")\n");
            }
        }
    }

    /**
     * Get connection status
     * @return connected
     *  boolean
     */
    public boolean getConnected(){
        return this.connected;
    }

    /**
     * Change connection status
     * @param value
     *  value to set
     */
    public void setConnected(boolean value){
        this.connected = value;
    }

	public String getTextLog() {
		return textLog;
	}
	
	private void setTextLog(String text){
		this.textLog = text;
	}
}