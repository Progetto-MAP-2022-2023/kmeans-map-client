import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import Exception.ArgsException;
import Exception.ServerException;

import keyboardinput.Keyboard;



public class MainTest {

    /**
     * @param args
     */
    private ObjectOutputStream out;
    private ObjectInputStream in ; // stream con richieste del client


    public MainTest(String ip, int port) throws IOException{
    	InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
    }
    
    private static MainTest assignArguments(String[] parameters) throws ArgsException, IOException {
    	if (parameters.length < 2)
    	{
    		throw new ArgsException("Inserire Indirizzo IP e Porta come argomenti");
    	}
    	
    	String ip = parameters[0];
        int port = Integer.parseInt(parameters[1]);
        
		return new MainTest(ip,port);
    }

    private int menu(){
        int answer = 3;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta:");
            try{
                answer=Keyboard.readInt();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        while(answer<=0 || answer>2);
        return answer;

    }

    private String learningFromFile() throws SocketException, ServerException,IOException,ClassNotFoundException{
        out.writeObject(3);

        System.out.print("Nome tabella:");
        String tabName=Keyboard.readString();
        out.writeObject(tabName);
        System.out.print("Numero cluster:");
        int k=Keyboard.readInt();
        out.writeObject(k);
        String result = (String)in.readObject();
        if(result.equals("OK"))
            return (String)in.readObject();
        else throw new ServerException(result);

    }
    private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(0);
        System.out.print("Nome tabella:");
        String tabName=Keyboard.readString();
        out.writeObject(tabName);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);

    }
    private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(1);
        System.out.print("Numero di cluster:");
        int k=Keyboard.readInt();
        out.writeObject(k);
        String result = (String)in.readObject();
        if(result.equals("OK")){
            //System.out.println("Clustering output: "+in.readObject());
            return (String)in.readObject();
        }
        else throw new ServerException(result);


    }

    private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(2);


        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);

    }
    
    public static void main(String[] args) {
    	MainTest main=null;
        try{
        	main = assignArguments(args);
        }
        catch (ArgsException e) {
			System.out.println("Errore: " + e.getMessage());
			return;
		}
        catch (NumberFormatException e) {
        	System.out.println("Errore: La porta logica non è corretta");
        	return;
        }
        catch (IOException e){
            System.out.println(e);
            return;
        }

        do{
            int menuAnswer=main.menu();
            switch(menuAnswer)
            {
                case 1:
                    try {
                        String kmeans=main.learningFromFile();
                        System.out.println("\n\n" + kmeans);
                    }
                    catch (SocketException e) {
                        System.out.println(e);
                        return;
                    }
                    catch (FileNotFoundException e) {
                        System.out.println(e);
                        return ;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    }
                    catch (ServerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2: // learning from db

                    while(true){
                        try{
                            main.storeTableFromDb();
                            break; //esce fuori dal while
                        }

                        catch (SocketException e) {
                            System.out.println(e);
                            return;
                        }
                        catch (FileNotFoundException e) {
                            System.out.println(e);
                            return;

                        } catch (IOException e) {
                            System.out.println(e);
                            return;
                        } catch (ClassNotFoundException e) {
                            System.out.println(e);
                            return;
                        }
                        catch (ServerException e) {
                            System.out.println(e.getMessage());
                        }
                    } //end while [viene fuori dal while con un db (in alternativa il programma termina)

                    char answer='y';//itera per learning al variare di k
                    do{
                        try
                        {
                            String clusterSet=main.learningFromDbTable();
                            System.out.println("Clustering output: " + clusterSet);

                            main.storeClusterInFile();

                        }
                        catch (SocketException e) {
                            System.out.println(e);
                            return;
                        }
                        catch (FileNotFoundException e) {
                            System.out.println(e);
                            return;
                        }
                        catch (ClassNotFoundException e) {
                            System.out.println(e);
                            return;
                        }catch (IOException e) {
                            System.out.println(e);
                            return;
                        }
                        catch (ServerException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
                        try{
                            answer=Keyboard.readChar();
                        }catch(IOException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    while(answer=='y');
                    break; //fine case 2
                default:
                    System.out.println("Opzione non valida!");
            }

            System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
            try{
                char risposta = Keyboard.readChar();
                if(risposta !='y')
                    break;
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        while(true);
    }
}



