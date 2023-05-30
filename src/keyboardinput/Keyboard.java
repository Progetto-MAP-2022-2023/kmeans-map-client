package keyboardinput;

import java.io.*;

public class Keyboard {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static int readInt() throws IOException, NumberFormatException{
        try{
            return Integer.parseInt(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a int value.");
        }
    }

    public static int readInt(String message) throws IOException, NumberFormatException{
        System.out.print(message + ": ");
        try{
            return Integer.parseInt(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a int value.");
        }
    }

    public static double readDouble() throws IOException, NumberFormatException{
        try{
            return Double.parseDouble(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a double value.");
        }
    }

    public static double readDouble(String message) throws IOException, NumberFormatException{
        System.out.print(message + ": ");
        try{
            return Double.parseDouble(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a double value.");
        }
    }

    public static float readFloat() throws IOException, NumberFormatException{
        try{
            return Float.parseFloat(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a float value.");
        }
    }

    public static float readFloat(String message) throws IOException, NumberFormatException{
        System.out.print(message + ": ");
        try{
            return Float.parseFloat(in.readLine().replace(",","."));
        }catch(IOException e){
            throw new IOException();
        }catch (NumberFormatException e){
            throw new NumberFormatException("The value entered does not correspond to a float value.");
        }
    }

    public static char readChar() throws IOException, NumberFormatException{
        try{
            return (in.readLine().replace(",",".")).charAt(0);
        }catch(IOException e){
            throw new IOException();
        }
    }

    public static char readChar(String message) throws IOException, NumberFormatException{
        System.out.print(message + ": ");
        try{
            return (in.readLine().replace(",",".")).charAt(0);
        }catch(IOException e){
            throw new IOException();
        }
    }

    public static String readString() throws IOException{
        try{
            return in.readLine();
        }catch(IOException e){
            throw new IOException();
        }
    }

    public static String readString(String message) throws IOException{
        System.out.print(message + ": ");
        try{
            return in.readLine();
        }catch(IOException e){
            throw new IOException();
        }
    }

}
