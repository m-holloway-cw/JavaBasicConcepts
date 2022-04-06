/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Raxa
 */
public class APIParse {

    public static void main(String args[]) {
       /* String msg1 = "Mwuhuhuhuh! I'm invincible! The evil within? More like the evil without WEAKNESSES! HAH HAH HAH! #TEW";
        String msg = "#TEW";
        String msg3 = "Test #TEW test";
        if (msg.contains("#")) {
            try{
            int beginIndex = msg.indexOf("#") + 1;
            int endIndex = msg.indexOf(" ", beginIndex);
            
            if (endIndex < 0) {
                endIndex = msg.length();
            } else {
                endIndex = msg.indexOf(" ", msg.indexOf("#") + 1);
            }
            System.out.println(beginIndex + " bI, eI " + endIndex);
            String game = msg.substring(beginIndex, endIndex);
            System.out.println("GAME FROM SEARCH: " + game);       
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
        */
      
        
       String t =  "<td><i><a href=\"/wiki/Smile_(%D0%A3%D0%BB%D1%8B%D0%B1%D0%B0%D0%B9%D1%81%D1%8F)\" title=\"Smile (Улыбайся)\">Smile</a></i><br />(<i>Улыбайся</i>) </td><td> IOWA </td><td> 2013 </td><td> <b>Solo</b> </td><td> <div class=\"center\"><figure class=\"article-thumb tnone show-info-icon\" style=\"width: 180px\"> 	<a href=\"https://vignette.wikia.nocookie.net/justdance/images/5/5d/Ulibayssia.jpg/revision/latest?cb=20151127115917\" 	class=\"image image-thumbnail\" 	 	 	><img src=\"data:image/gif;base64,R0lGODlhAQABAIABAAAAAP///yH5BAEAAAEALAAAAAABAAEAQAICTAEAOw%3D%3D\" 	 alt=\"Ulibayssia\"  	class=\"thumbimage lzy lzyPlcHld \" 	 	data-image-key=\"Ulibayssia.jpg\" 	data-image-name=\"Ulibayssia.jpg\" 	 data-src=\"https://vignette.wikia.nocookie.net/justdance/images/5/5d/Ulibayssia.jpg/revision/latest/scale-to-width-down/180?cb=20151127115917\"  	 width=\"180\"  	 height=\"180\"  	 	 	 onload=\"if(typeof ImgLzy===&#39;object&#39;){ImgLzy.load(this)}\"  	><noscript><img src=\"https://vignette.wikia.nocookie.net/justdance/images/5/5d/Ulibayssia.jpg/revision/latest/scale-to-width-down/180?cb=20151127115917\" 	 alt=\"Ulibayssia\"  	class=\"thumbimage \" 	 	data-image-key=\"Ulibayssia.jpg\" 	data-image-name=\"Ulibayssia.jpg\" 	 	 width=\"180\"  	 height=\"180\"  	 	 	 	></noscript></a>  	<figcaption> 		 			<a href=\"/wiki/File:Ulibayssia.jpg\" class=\"sprite info-icon\"></a> 		 		 		 	</figcaption> </figure></div></td><td style=\"background-color:#fed42a; color:black\"> <b>Exclusive Track</b> </td><td> Available at launch</td></tr>";
        String title;
        String artist;
        List<String> check = new java.util.ArrayList();
        int c = 0;
        if (t.contains("title=")) {
                    int nameStart = t.indexOf("title=") + 7;
                    int nameEnd = t.indexOf("\"", nameStart);
                    title = t.substring(nameStart, nameEnd);
                    int artistStart = t.indexOf("<td", nameEnd);
                    int artistEnd = t.indexOf("</td>", artistStart);
                    int testI = t.indexOf("IOWA");
                    System.out.println(nameEnd + " " + artistStart + " " + testI);
                    if (title.contains("â€™")) {
                        title = title.replace("â€™", "\'");
                    } else if (title.contains("&amp;")) {
                        title = title.replace("&amp;", "&");
                    } else if (title.contains("â€˜N\'")) {
                        title = title.replace("â€˜N\'", "&");
                    }
                    if (!check.contains(title) && !title.contains("Just Dance")) {
                        check.add(title);
                        c++;
                        if (artistEnd < 1 || artistStart < 1) {
                            artist = "Artist not found";
                        } else {
                            artist = t.substring(artistStart + 5, artistEnd - 1);
                        }
                        System.out.println(c + ". " + title + " - " + artist);
                    }

                }
    }
}
