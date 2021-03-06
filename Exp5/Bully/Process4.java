
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Process4 implements BullyInterface {
	boolean foundgreater = false;
	static boolean electionInProgress = false;
	private static String thisID = "4";
	private static String coordinator;
	static BullyInterface stub2;

	public Process4() {

	}

	public static void main(String[] args) {
		Process4 obj = new Process4();

		try {

			stub2 = (BullyInterface) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind(thisID, stub2);

			System.err.println("Process" + thisID + " is  ready");
			stub2.startElection(thisID);
		} catch (RemoteException e) {
			System.out.println("Couldnt bind Process to registry\n");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("Process already bound to registry \n");
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new ShutDown());
		repeat();

	}

	@Override
	public String startElection(String ProcessId) throws RemoteException {
		electionInProgress = true;
		foundgreater = false;
		
		if (ProcessId.equals(thisID)) {
			System.out.println("You started the elections");

			Registry reg = LocateRegistry.getRegistry();
			for (String ProcessName : reg.list()) {
				if (!ProcessName.equals(thisID) && Integer.parseInt(ProcessName) > 4) {
					System.out.println(reg.list().length);
					BullyInterface stub;
					try {
						stub = (BullyInterface) reg.lookup(ProcessName);
						System.out.println("Sending election challenge to " + ProcessName);
						stub.startElection(ProcessId);
						foundgreater = true;

					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				}
			}
			if (!foundgreater) {
				iWon(thisID);
			}

			return null;
		} else {
			System.out.println("Received election request from " + ProcessId);
			sendOk(thisID, ProcessId);
			return null;
		}
	}

	@Override
	public String sendOk(String where, String to) throws RemoteException {
		if (!thisID.equals(to)) {
			try {
				Registry reg = LocateRegistry.getRegistry();
				BullyInterface stub = (BullyInterface) reg.lookup(to);
				System.out.println("Sending OK to " + to);
				stub.sendOk(where, to);

				startElection(thisID);
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(where + " Replied with Ok..");
		}
		return null;
	}

	@Override
	public String iWon(String Process) throws RemoteException {
		coordinator = Process;
		electionInProgress = false;
		if (Process.equals(thisID)) {
			System.out.println("You have won the election.");
			Registry reg = LocateRegistry.getRegistry();
			for (String ProcessName : reg.list()) {
				if (!ProcessName.equals(thisID)) {
					BullyInterface stub;
					try {
						stub = (BullyInterface) reg.lookup(ProcessName);
						stub.iWon(Process);

					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println("Process " + Process + " is the new Coodinator\n");
		} else {
			System.out.println("Process " + Process + " has won the election.");
			System.out.println("Process " + Process + " is the new Coodinator\n");
		}
		return null;
	}

	static class TimerCheck extends TimerTask {

		@Override
		public void run() {
			if (!thisID.equals(coordinator) && !electionInProgress) {
				try {
					Registry reg = LocateRegistry.getRegistry();
					BullyInterface stub;
					stub = (BullyInterface) reg.lookup(coordinator);
					stub.isalive();

				} catch (RemoteException e) {
					System.out.println("Error : remote exception");
				} catch (NotBoundException e) {
					coordinatorCrashed();
				}
			}
			repeat();

		}
	}

	public static void repeat() {
		Random rand = new Random();
		int randomNum = rand.nextInt((6 - 1) + 1) + 1;
		Timer timer = new Timer();
		timer.schedule(new TimerCheck(), randomNum * 1000);
	}

	private static void coordinatorCrashed() {
		System.out.println("Coordinator has crushed. Iniating new election");
		try {
			stub2.startElection(thisID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean isalive() throws RemoteException {
		return true;
	}

	static class ShutDown extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				System.out.println("Terminating Process");
				LocateRegistry.getRegistry().unbind(thisID);
			} catch (AccessException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}

	}
}
