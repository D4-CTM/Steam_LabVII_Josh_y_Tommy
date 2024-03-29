/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SteamData;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Tommy Lee Pon
 */
public class Steam {
    private Steam_Codes codes;
    private Steam_Games games;
    private Steam_Players players;
    
    public Steam(){
        try{
            codes = new Steam_Codes();
            games = new Steam_Games();
            players = new Steam_Players();
        } catch (IOException e){
            System.out.println("Error fatal!");
        }
    }
    
    public int getNextFree(String type) throws IOException{
        return codes.getNextFree(type);
    }
    
    public void addGame(String title, char SistemaOperativo, int ESRB, double price, String poster) throws IOException{
        games.addGameToSteam(title, SistemaOperativo, ESRB, price, poster);
    }
    
    public void downloadGame(int code, int userCode, char OS) throws IOException{
        if(!games.getTitulo(code).equals("") && games.getSO(code)==OS){
            if(games.getESRB(code)>= players.getEdad(players.buscar(userCode, 0))){
                RandomAccessFile downloadFile = new RandomAccessFile("Steam/downloads/download_" + codes.getNextFree("downs") + "download.stm", "rw");
                downloadFile.writeInt(codes.getCode("downs"));
                Calendar hoy = Calendar.getInstance();
                downloadFile.writeUTF("Fecha de descarga: " + hoy.toString());
                games.addDownload(code);
                players.AddGame(players.buscar(userCode, 0));
            }
        }
    }
    
    public void updatePriceFor(int code, double precio) throws IOException{
        games.updatePriceFor(code, precio);
    }
    
    public void reportForClient(String username) throws IOException{
        RandomAccessFile reporte = new RandomAccessFile("steam/reporte_" + username + ".txt", "rw");
        Calendar birthdate = Calendar.getInstance();
        birthdate.setTime(new Date(players.getBirthDate(username)));
        reporte.writeUTF("Nombre: " + players.getName(username) + 
                "\nNickname: " + players.getUsername(username) + 
                "\nBirthdate: " + birthdate.toString() + 
                "\nTipo: " + players.TypeAccount(username));
    }
    
    public void printGames() throws IOException{
        System.out.println(games.printGames(0));
    } 
}
