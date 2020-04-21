package DB;

import java.net.Socket;

import Server.ServerChat;

public interface DAOInterface {
	
	int Select(String id, String pwd,String notice, Socket withClient);
	Boolean Edit(Object DTO);
	Boolean Delete(Object DTO);
	Boolean Insert(Object obj, String notice, Socket withClient, Socket withClient2);
	

}

