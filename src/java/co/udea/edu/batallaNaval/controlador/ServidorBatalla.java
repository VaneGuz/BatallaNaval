/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.udea.edu.batallaNaval.controlador;

import co.udea.edu.batallaNaval.modelo.Tablero;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Vanesa
 */
@ServerEndpoint("/servidorBatalla")
public class ServidorBatalla {

    private Session player;
    private Tablero tablero;
    private final int limite = 20;
    private String gameOver = "<h1>GAME OVER</h1> <h1 id = \"sub\">Partidas</h1>";
    private final String prueba = "<h2>pruebaa</h2>";
    private int intentos;
    private int puntaje = 0;
    private String total;
    private List<Session> clientes= Collections.synchronizedList(new LinkedList<Session>());

    @OnMessage
    public String onMessage(String message) throws IOException {
        String[] a = message.split(" ");
        if (!message.equals("nuevo")) {
            try {
                int x = Integer.parseInt(a[0]), y = Integer.parseInt(a[1]);
                if (intentos < limite) {
                    puntaje = tablero.disparo(x, y) + puntaje;
                    intentos++;

                } else {

                    gameOver = gameOver + "<h2> Puntaje: " + Integer.toString(puntaje) + "</h2>";
                    player.getBasicRemote().sendText(gameOver);
                    return null;
                }

            } catch (NumberFormatException e) {

            }
        } else {
            tablero = new Tablero();
            puntaje=0;
            intentos=0;
        }
        player.getBasicRemote().sendText(generarTabla());
        System.out.println("PL!!"+ clientes.size());

        return null;
    }

    ///Se ejeucuta una vez se abre la conexión
    @OnOpen
    public void onOpen(Session peer
    ) {
        System.out.println("ESTOY EN ONOPEN");
        player = peer;
        intentos = 0;
        clientes.add(peer);
    }

    public String generarTabla() {
        StringBuilder tabla = new StringBuilder();
        int[][] t = tablero.getBoard();
        
        tabla.append(" <h2 id=\"frase\"> Encuentra el barco del enemigo y<br/> <strong>¡Atácalo! </strong></h2>\n");
        tabla.append(" <table> \n");
        tabla.append(" <table> \n");
        for (int i = 0; i < 10; i++) {
            tabla.append("<tr> \n");
            for (int j = 0; j < 10; j++) {
                tabla.append("<td cx=\"");
                tabla.append(i);
                tabla.append("\" cy=\"");
                tabla.append(j);
                tabla.append("\">");
                //tabla.append(t[i][j]);
                switch (t[i][j]) {
                    case 0:
                    case 1:
                        tabla.append("<img width=\"25\" height=\"25\"  src=\"imagenes/gray.jpg\">");
                        break;
                    case 2:
                        tabla.append("<img width=\"25\" height=\"25\" src=\"imagenes/water.gif\">");
                        break;
                    case 3:
                        tabla.append("<img width=\"25\" height=\"25\" src=\"imagenes/madera.jpg\">");
                        break;
                }
                tabla.append("</td> \n");
            }

            tabla.append("</tr> \n");
        }

        tabla.append(" </table>");

        return tabla.toString();

    }
     @OnClose
  public void onClose (Session session) {
    // Remove session from the connected sessions set
    clientes.remove(session);
  }

}
