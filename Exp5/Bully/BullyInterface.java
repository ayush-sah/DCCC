
import java.rmi.*;

public interface BullyInterface extends Remote {

	public String startElection(String nodeId) throws RemoteException;

	public String sendOk(String where, String to) throws RemoteException;

	public String iWon(String node) throws RemoteException;

	public boolean isalive() throws RemoteException;
}
