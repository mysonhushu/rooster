package rooster.internal.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

public class ConnSource
{
  public static String PROXOOL = "proxool";
  
  public static String proxoolDriver = "org.logicalcobwebs.proxool.ProxoolDriver";
  
  public static void inti(String properFile) 
  {
    InputStream is = null;
    BufferedReader in = null;
    BufferedInputStream bis = null;
    InputStreamReader isr = null;
    try
    {
      File proxool = new File(properFile);
      is = new FileInputStream(proxool);
      bis = new BufferedInputStream(is);
      isr = new InputStreamReader(bis);
      in = new BufferedReader(isr);
      JAXPConfigurator.configure(in, false);
      Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
    }catch (Exception e){
//      throw e;
    	e.printStackTrace();
    }
    finally
    {
      if (is != null) {
        try
        {
          is.close();
        }catch (Exception e){
//          throw e;
        	e.printStackTrace();
        } finally {
          is = null;
        }
      }
      if (bis != null) {
        try
        {
          bis.close();
        } catch (Exception e){
//          throw e;
        	e.printStackTrace();
        } finally
        {
          bis = null;
        }
      }
      if (isr != null) {
        try{
          isr.close();
        }catch (Exception e){
//          throw e;
        	e.printStackTrace();
        }finally
        {
          isr = null;
        }
      }
      if (in != null) {
        try
        {
          in.close();
        }catch (Exception e){
//          throw e;
        	e.printStackTrace();
        }finally
        {
          in = null;
        }
      }
    }
  }
  
  
  public static Connection getConnection()
  {
    try{
      return DriverManager.getConnection("proxool.PCLDB");
    }catch (Exception e){
      System.out.println("[PCLDB]Connection failed ! " + e.getMessage());
    }
    return null;
  }
  
    public static Connection getConnection(String connType)
  {
    if("production".equals(connType))
    {
        try{
            return DriverManager.getConnection("proxool.PCLDB");
        }catch (Exception e){
            System.out.println("[PCLDB]Connection failed ! " + e.getMessage());
        } 
    }else if("test".equals(connType))
    {
       try{
            return DriverManager.getConnection("proxool.TestDB");
        }catch (Exception e){
            System.out.println("[TestDB]Connection failed ! " + e.getMessage());
        }  
    }
    return null;
  }
  
  public static void close(Connection conn, PreparedStatement ps, ResultSet rs)
  {
    if (rs != null)
    {
      try
      {
        rs.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      rs = null;
    }
    if (ps != null)
    {
      try
      {
        ps.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      ps = null;
    }
    if (conn != null)
    {
      try
      {
        conn.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      conn = null;
    }
  }
  
  public static void close(Connection conn, PreparedStatement ps)
  {
    if (ps != null)
    {
      try
      {
        ps.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      ps = null;
    }
    if (conn != null)
    {
      try
      {
        conn.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      conn = null;
    }
  }
  
  public static void close(Connection conn, CallableStatement cstmt)
  {
    if (cstmt != null)
    {
      try
      {
        cstmt.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      cstmt = null;
    }
    if (conn != null)
    {
      try
      {
        conn.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      conn = null;
    }
  }
  
  public static void close(Connection conn, Statement ps, ResultSet rs)
  {
	  try {
			if(rs !=null)
			{
				rs.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(ps != null)
				{
					ps.close();
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}finally{
				try {
					if(conn != null)
					{
						conn.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		}
  }
  
  public static void close(PreparedStatement ps)
  {
    if (ps != null)
    {
      try
      {
        ps.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      ps = null;
    }
  }
  
  public static void close(Connection conn)
  {
    if (conn != null)
    {
      try
      {
        conn.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      conn = null;
    }
  }
}
