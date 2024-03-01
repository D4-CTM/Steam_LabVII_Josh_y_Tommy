package Steam;

import SteamData.Player;
import java.io.IOException;

/**
 *
 * @author josue
 */
public class Exe {
    public static Player Jugadores;
    
    public static void main(String[] args) throws IOException{
        Jugadores = new Player();
        new LogIn().setVisible(true);
    }
    
}
