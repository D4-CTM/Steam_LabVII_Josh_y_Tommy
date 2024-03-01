package SteamData;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

public class Steam_Players {
    RandomAccessFile Writer;
    File CarpetaDeJugadores;
    private Steam_Codes counter;
    
    public Steam_Players()throws IOException{
        counter=new Steam_Codes();
        CarpetaDeJugadores = new File("Steam");
        if (!CarpetaDeJugadores.exists()) CarpetaDeJugadores.mkdir();
        
        Writer = new RandomAccessFile("Steam\\Players.stm", "rw");
    }
    
    private void initPlayers() throws IOException{
        Writer.seek(0);
        if (Writer.length() == 0){
            Writer.writeInt(0);
            Writer.writeUTF("Admin");
            Writer.writeUTF("Supersecreto");
            Writer.writeUTF("Erick");
            Writer.writeLong(0);
            Writer.writeInt(0);
            Writer.writeUTF("Icons\\PlayersPFP\\The_Bat.png");
            Writer.writeUTF("Admin");
        }
    }
    /*
    * Formato de jugadores:
    * int code
    * String Username
    * String password
    * String nombre
    * long nacimiento
    * int contador downloads
    * String Url de la imagen
    * String Tipo de Usuario
    */
    public void addPlayer(String Username, String Password, String Name, Date BirthDate, String ImageUrl, String AccountType) throws IOException{
        Writer.seek(Writer.length());
        Writer.writeInt(Math.round(Writer.length()));
        Writer.writeUTF(Username);
        Writer.writeUTF(Password);
        Writer.writeUTF(Name);
        Writer.writeLong(BirthDate.getTime());
        Writer.writeInt(0);
        Writer.writeUTF(ImageUrl);
        Writer.writeUTF(AccountType);
    }
    
    public String getUsername(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            long Pos = Writer.getFilePointer();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.seek(Pos);
                return Writer.readUTF();
            }
        }
        return "";
    }
    
    public String getPassword(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                return Writer.readUTF();
            }
        }
        return "";
    }
    
    public String getName(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                Writer.readUTF();
                return Writer.readUTF();
            }
        }
        return "";
    }
    
    public long getBirthDate(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                Writer.readUTF();
                Writer.readUTF();
                return Writer.readLong();
            }
        }
        return 0;
    }
    
    public void AddGame(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                int Count = Writer.readInt() + 1;
                Writer.seek(Writer.getFilePointer() - 4);
                Writer.writeInt(Count);
            }
        }
    }
    
    public String getImageIcon(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                return Writer.readUTF();
            }
        }
        return "";
    }
    
    public String TypeAccount(String Username) throws IOException{
        Writer.seek(0);
        while (Writer.getFilePointer() < Writer.length()){
            Writer.readInt();
            if (!Writer.readUTF().equals(Username)){
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                Writer.readUTF();
            } else {
                Writer.readUTF();
                Writer.readUTF();
                Writer.readUTF();
                Writer.readLong();
                Writer.readInt();
                Writer.readUTF();
                return Writer.readUTF();
            }
        }
        return "";
    }
    
    /*
    * Formato de jugadores:
    * int code
    * String Username
    * String password
    * String nombre
    * long nacimiento
    * int contador downloads
    * String Url de la imagen
    * String Tipo de Usuario
    */
    
    public String buscar(int code, long cero) throws IOException{
        long index = cero;
        Writer.seek(cero);
        if(Writer.readInt()==code){
            return Writer.readUTF();
        } else if (index < Writer.length()){
            Writer.readUTF();
            Writer.readUTF();
            Writer.readUTF();
            Writer.readLong();
            Writer.readInt();
            Writer.readUTF();
            Writer.readUTF();
            index = Writer.getFilePointer();
            buscar(code, index);
        }
        return "";
    }
    
    public int getEdad(String username) throws IOException{
        Date birthday = new Date(getBirthDate(username));
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        
        return today.get(Calendar.YEAR)-birth.get(Calendar.YEAR);
    }
}