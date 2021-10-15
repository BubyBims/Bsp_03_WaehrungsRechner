package at.htlkaindorf.Bsp_03_WaehrungsRechner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(name = "waehrungs-Rechner", value = "/waehrungs-Rechner")
public class WaehrungsRechner extends HttpServlet {
    public WaehrungsRechner() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        double betrag;
        String bwaehrung = request.getParameter("bwaehrung");
        String zwaehrung = request.getParameter("zwaehrung");
        System.out.println(zwaehrung +"-----"+ bwaehrung);
        double kursEURUSD = 1.16;
        double kursUSDEUR = 0.86;
        double sum = 0;
        String fehler = "";

        try {
            betrag = Double.parseDouble(request.getParameter("betrag"));
            if(bwaehrung.equals("Währung wählen") || zwaehrung.equals("Währung wählen")) {
                fehler += "Sie müssen eine Währung wählen\n";
            }

            if(bwaehrung.equals("EURO")) {
                if(!(zwaehrung.equals("EURO"))) {
                    sum = betrag * kursEURUSD;
                } else {
                    fehler += "Selbe Währung gewählt\n";
                }
                bwaehrung = String.format("%.2f %s", betrag, bwaehrung.substring(0,3));
                System.out.println(zwaehrung);
                zwaehrung = String.format("%.2f %s", sum, zwaehrung.substring(0,2) + zwaehrung.charAt(4));
                fehler = String.format("Kurs: 1 EUR = 1,16 USD");
            } else {
                if(!(zwaehrung.equals("US-DOLLAR"))) {
                    sum = betrag * kursUSDEUR;
                } else {
                    fehler += "Selbe Währung gewählt\n";
                }
                bwaehrung = String.format("%.2f %s", betrag, bwaehrung.substring(0,2) + bwaehrung.charAt(4));
                zwaehrung = String.format("%.2f %s", sum, zwaehrung.substring(0,3));
                fehler = String.format("Kurs: 1 USD = 0,86 EUR");
            }
        } catch (Exception ex) {
            fehler += "Falsche eingabe\n";
        }

        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("waehrungsForm.html").include(request, response);
        if(fehler.startsWith("Kurs:")) {
            out.println("<link rel = \"stylesheet\" href=\"styleCSS.css\">");
            out.println("</hr><table style = \"background: deepskyblue\"><tr><td><text><b>Ihr Ergebnis</b></text></td></tr></br>");
            out.println("<tr><td><text>" + bwaehrung + "</text></td></tr>");
            out.println("<tr><td><text>=</text></td></tr>");
            out.println("<tr><td><text>" + zwaehrung + "</text></td></tr>");
            out.println("<tr><td><text>" + fehler + "</text></td></tr></table>");
        }
        else {
            out.println("<link rel = \"stylesheet\" href=\"styleCSS.css\">");
            out.println("<h4>" + fehler + "</h4>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void destroy() {
    }
}