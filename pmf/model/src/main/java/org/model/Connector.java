package org.model;

import gnu.io.*; // RXTX

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.TooManyListenersException;


public class Connector implements SerialPortEventListener {

    //static CommPortIdentifier SerialPortId;
    private CommPortIdentifier curentPortID;

    //SerialPort serialPort;
    private Enumeration<CommPortIdentifier> enumComm;

    //Serial port
    private SerialPort serialPort;

    //Map the port names to CommPortIdentifiers
	private HashMap<String,CommPortIdentifier> portMap = new HashMap();

    //Connection status
    private boolean connected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //input and output streams for sending and receiving data
    private InputStream input = null;
    private OutputStream output = null;

    //String containing Arduino's informations
    private String arduinoInfos = "";

    //Log
    private String textLog = "";

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;


    /**
     * Search the availables ports
     */
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
        
        return portsAvailable;
    }
    
    public void searchPort(){
    	
        this.enumComm = CommPortIdentifier.getPortIdentifiers();
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
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
            writeData(0);

            successful = true;
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
            output.write(SPACE_ASCII);
            output.flush();
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
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                byte singleData = (byte) input.read();

                if (singleData != NEW_LINE_ASCII)
                {
                    this.arduinoInfos += new String(new byte[] {singleData});
                }
                else
                {
                	this.arduinoInfos += "\n";
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

    /**
     * Get the arduino informations recieve
     * @return
     */
    public String getArduinoInfos(){
        return this.arduinoInfos;
    }

	public String getTextLog() {
		return textLog;
	}
	
	private void setTextLog(String text){
		this.textLog = text;
	}
}