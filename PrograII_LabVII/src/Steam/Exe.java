package Steam;

import SteamData.Steam_Players;
import java.io.IOException;

/**
 *
 * @author josue
 */
public class Exe {
    public static Steam_Players Jugadores;
    
    public static void main(String[] args) throws IOException{
        Jugadores = new Steam_Players();
        new LogIn().setVisible(true);
    }
    
}
