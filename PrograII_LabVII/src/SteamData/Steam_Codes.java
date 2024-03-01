/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SteamData;

import java.io.*;

/**
 *
 * @author Tommy Lee Pon
 */
public class Steam_Codes {
    private RandomAccessFile rcodes;
    public Steam_Codes(){
        File padre = new File("Steam");
        try{
            if(!padre.exists()){
                padre.mkdir();
            }
            rcodes = new RandomAccessFile(padre.getPath() + "/codes.stm", "rw");
            inicializar();
        } catch (IOException e){
            System.out.println("ERROR FATAL!");
        }
    }
    
    //Funciones privadas
    
    /*
    FORMATO
    Nombre del archivo: codes
    int codigo juego
    int codigo usuario
    int contador de descargas
    */
    private void inicializar() throws IOException{
        if(rcodes.length()==0){
            for(int i = 0; i<3 ; i++){
                rcodes.writeInt(1);
            }
        }
    }
    
    //Funciones publicas
    
    public int getCode(String type) throws IOException{
        int code = getNextFree(type);
        rcodes.writeInt(code+1);
        return code;
    }
    
    public int getNextFree(String type) throws IOException{
        int index=0;
        switch(type){
            case "game" -> {
                index=0;
            }
            case "user" -> {
                index=4;
            }
            case "downs" -> {
                index=8;
            }
        }
        rcodes.seek(index);
        int count = rcodes.readInt();
        rcodes.seek(index);
        return count;
    }
    
}
