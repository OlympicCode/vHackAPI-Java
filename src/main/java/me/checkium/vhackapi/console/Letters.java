package me.checkium.vhackapi.console;

import java.math.BigInteger;
import java.util.HashMap;

//TODO: move to a suitable package -> eg. OCR
public class Letters {

    private static HashMap<BigInteger, Character> table;

    private static Letters instance;

    private Letters() {}

    public static Letters getInstance() {
        if(Letters.instance == null)
        {
            Letters.instance = new Letters();
            initializeTable();
        }
        return Letters.instance;
    }

    private static void initializeTable() {
        table = new HashMap<>();
        table.put((new BigInteger("138882308926605356273569103872")), 'b');
        table.put((new BigInteger("79228163665150427101350723584")), 'c');
        table.put((new BigInteger("80160259584121600695424712704")), 'd');
        table.put((new BigInteger("79228163628474025430208086016")), 'e');
        table.put((new BigInteger("88574609755369126824638414848")), 'f');
        table.put((new BigInteger("79228164834502758305282048894")), 'g');
        table.put((new BigInteger("138882308926605356272980262912")), 'h');
        table.put((new BigInteger("86684818004401202618987053056")), 'i');
        table.put((new BigInteger("81092326386798553849915688572")), 'j');
        table.put((new BigInteger("109055235572362849156480565248")), 'k');
        table.put((new BigInteger("96588451065184023330955198464")), 'l');
        table.put((new BigInteger("79228165887414256860221079552")), 'm');
        table.put((new BigInteger("79228166589176383304397684736")), 'n');
        table.put((new BigInteger("79228163628473959472445521920")), 'o');
        table.put((new BigInteger("79228166589176383304986575040")), 'p');
        table.put((new BigInteger("79228163610099272992790610691")), 'q');
        table.put((new BigInteger("79228166617755272836240375808")), 'r');
        table.put((new BigInteger("79228164852659503468501663744")), 's');
        table.put((new BigInteger("79286422279248852407983931392")), 't');
        table.put((new BigInteger("79228166125485765668778999808")), 'u');
        table.put((new BigInteger("79228166125492547562131292160")), 'w');
        table.put((new BigInteger("79228166118746221705078964224")), 'x');
        table.put((new BigInteger("79228166125485765668779033470")), 'y');
        table.put((new BigInteger("79228164838989840740951916544")), 'z');

        table.put((new BigInteger("86728823591756243744717996032")), 'A');
        table.put((new BigInteger("157458676833660938652659482624")), 'B');
        table.put((new BigInteger("98536831751724949324173279232")), 'C');
        table.put((new BigInteger("157458676774212579146438803456")), 'D');
        table.put((new BigInteger("158070379020604335802487275520")), 'E');
        table.put((new BigInteger("158379864030425680871207993344")), 'F');
        table.put((new BigInteger("98536827029360440086489858048")), 'G');
        table.put((new BigInteger("139814404441260521983823773696")), 'H');
        table.put((new BigInteger("88519994809135780404085129216")), 'J');
        table.put((new BigInteger("118252401752678178141694197760")), 'I');
        table.put((new BigInteger("139818074106331162837887680512")), 'K');
        table.put((new BigInteger("138882308407357485899275173888")), 'L');
        table.put((new BigInteger("139858209552890160595930906624")), 'M');
        table.put((new BigInteger("139853317623935778623905267712")), 'N');
        table.put((new BigInteger("86728823591756177372312240128")), 'O');
        table.put((new BigInteger("158074020020646975708147482624")), 'P');
        table.put((new BigInteger("97921498009831347988704591872")), 'Q');
        table.put((new BigInteger("158074020020662751526803144704")), 'R');
        table.put((new BigInteger("118459924531798346654457659392")), 'S');
        table.put((new BigInteger("158175968019631692007184269312")), 'T');
        table.put((new BigInteger("139814404436937066339978969088")), 'U');
        table.put((new BigInteger("79228166125459485660819226624")), 'V');
        table.put((new BigInteger("139814404438673230644480507904")), 'W');
        table.put((new BigInteger("139813962754173539958442688512")), 'Y');
        table.put((new BigInteger("139813962754173579878748848128")), 'X');
        table.put((new BigInteger("157844637121106498363852718080")), 'Z');

        table.put((new BigInteger("97921498009831321548885852160")), '0');
        table.put((new BigInteger("86724069724311004751297642496")), '1');
        table.put((new BigInteger("97921494454385875554809085952")), '2');
        table.put((new BigInteger("117843685324194040540873621504")), '3');
        table.put((new BigInteger("81102140209191565922604023808")), '4');
        table.put((new BigInteger("158070379535474496230586580992")), '5');
        table.put((new BigInteger("97921493233935897933245054976")), '6');
        table.put((new BigInteger("158150481074817586777085181952")), '7');
        table.put((new BigInteger("97921496284530170325943189504")), '8');
        table.put((new BigInteger("97921498003163530644575485952")), '9');

        table.put((new BigInteger("83590080654423668100862705664")), '{');
        table.put((new BigInteger("113919611614311278351370682368")), '}');
        table.put((new BigInteger("98474489125036455632123592704")), '[');
        table.put((new BigInteger("117618867732573307059834716160")), ']');
        table.put((new BigInteger("79228162514336113712605167616")), '-');
        table.put((new BigInteger("79228162514264337593544015616")), '_');


    }

    public char getCharFor(BigInteger hash) {
        Character character = table.get(hash);
        return character == null ? ' ' : character;
    }

}
