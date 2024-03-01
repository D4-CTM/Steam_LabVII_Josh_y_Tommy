/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package steam_labp2;

import javax.swing.ImageIcon;
import java.io.*;

/**
 *
 * @author Tommy Lee Pon
 */
public class Steam_Games {
    private Steam_Codes counter;
    private RandomAccessFile rgames;
    
    public Steam_Games(){
        File padre = new File("steam");
        try{
            if(!padre.exists()){
                padre.mkdir();
            } 
            rgames = new RandomAccessFile(padre.getPath() + "/games.stm", "rw");
        } catch (IOException e){
            System.out.println("Error fatal!");
        }
    }
    
    //Funciones privadas
    
    //Recursiva
    private long getByteIndex(String parametro, int code, long cero) throws IOException{
        rgames.seek(cero);
        int skipNum = 0;
        switch (parametro) {
            case "titulo" -> {
                skipNum = 0;
            }
            case "so" -> {
                int utf = rgames.readUTF().length() * 2 + 2;
                skipNum = utf;
            }
            case "esrb" -> {
                int utf = rgames.readUTF().length() * 2 + 2;
                skipNum = utf + 2;
            }
            case "precio" -> {
                int utf = rgames.readUTF().length() * 2 + 2;
                skipNum = utf + 6;
            }
            case "downs" -> {
                int utf = rgames.readUTF().length() * 2 + 2;
                skipNum = utf + 14;
            }
            case "poster_path" -> {
                int utf = rgames.readUTF().length() * 2 + 2;
                skipNum = utf + 18;
            }
            
        }
        long index = cero;
        rgames.seek(cero);
        if(rgames.readInt()==code){
            return rgames.getFilePointer() + skipNum;
        } else if (index < rgames.length()){
            rgames.readUTF();
            rgames.skipBytes(18);
            rgames.readUTF();
            index = rgames.getFilePointer();
            getByteIndex(parametro, code, index);
        }
        return rgames.length();
    }
    
    //Funciones publicas
    
    
    /*
    FORMATO 
    Nombre del archivo = games.stm
    int codigo
    String titulo
    char sistema operativo
    int edadMinima/ESRB
    double precio
    int descargas
    String pathPoster
    */
    public void addGameToSteam(String title, char SistemaOperativo, int ESRB, double price, String poster) throws IOException{
        rgames.seek(rgames.length());
        rgames.writeInt(counter.getCode("game"));
        rgames.writeUTF(title);
        rgames.writeChar(SistemaOperativo);
        rgames.writeInt(ESRB);
        rgames.writeDouble(price);
        rgames.writeInt(0);
        rgames.writeUTF(poster);
    }
    
    public String printGames(long cero) throws IOException{
        rgames.seek(cero);
        if(cero<rgames.length()){
            int codigo = rgames.readInt();
            String nombre = rgames.readUTF();
            char SO = rgames.readChar();
            int ESRB = rgames.readInt();
            double precio = rgames.readDouble();
            int descargas = rgames.readInt();
            cero = rgames.getFilePointer();
            return codigo + "\t" + nombre + "\t" + SO + "\t" + ESRB + "\t" + precio + "\t" + descargas + "\n" + printGames(cero);
        }
        return "";
    }
    
    //getters
    public String getTitulo(int code) throws IOException{
        rgames.seek(getByteIndex("titulo", code, 0));
        return rgames.readUTF();
    }
    public char getSO(int code) throws IOException{
        rgames.seek(getByteIndex("so", code, 0));
        return rgames.readChar();
    }
    public int getESRB(int code) throws IOException{
        rgames.seek(getByteIndex("esrb", code, 0));
        return rgames.readInt();
    }
    public double getPrecio(int code) throws IOException{
        rgames.seek(getByteIndex("precio", code, 0));
        return rgames.readDouble();
    }
    public int getDescargas(int code) throws IOException{
        rgames.seek(getByteIndex("downs", code, 0));
        return rgames.readInt();
    }
    public ImageIcon getPoster(int code) throws IOException{
        rgames.seek(getByteIndex("poster_path", code, 0));
        return new ImageIcon(rgames.readUTF());
    }
    
    public void addDownload(int code) throws IOException{
        rgames.seek(getByteIndex("downs", code, 0));
        int downs = rgames.readInt();
        rgames.seek(getByteIndex("downs", code, 0));
        rgames.writeInt(downs+1);
    }
}
