/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster.config;

import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import rooster.internal.db.ConnSource;
 

/**
 *
 * @author zg
 */
public class SystemLoad {
    public static String properFile =  "properties/proxool.xml";//"properties/update/proxool.xml";
	public static String logPath = "properties/log.properties";//"properties/update/log.properties";
	//String properFile = "E://yaxinwork//workSoftware//apache-tomcat-6.0.32//apache-tomcat-6.0.32//HLM_SOURCE//properties//proxool.xml";
	public static void init(){
		try {
			PropertyConfigurator.configure(logPath); 
			ConnSource.inti(properFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
