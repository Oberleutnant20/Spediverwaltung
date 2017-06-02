package de.uniqueplayers.speditionsverwaltung.funktionen.casts;

/**
 *
 * @author Oberleutnant20
 */
public class Cast {
    
    //Bsp
    public String passwort(String password){
		String ret = null;
		String passwd = password;
		String sPasswd_ohne_Zeichen = passwd.replaceAll("[&()=|<>]", "#");
		String[] sAbfolge = new String[] { "&", "(", ")", "=", "|","<", ">" };
		String sString_ohne_Zahlen_Buchstaben = passwd;
		String raute = "#";
		String leer = "";
		sString_ohne_Zahlen_Buchstaben = sString_ohne_Zahlen_Buchstaben.replaceAll("[a-zA-Z0-9]", leer);
		String[] sZeichen = new String[sString_ohne_Zahlen_Buchstaben.length()];
		int i = 0;
		for (char c : sString_ohne_Zahlen_Buchstaben.toCharArray()) {
			sZeichen[i] = "" + c;
			i++;
		}
		String[] sWorte = sPasswd_ohne_Zeichen.split(raute);
		for (String string : sAbfolge) {
			while (contains(sZeichen, string)) {
				int iStelle = getstelle(sZeichen, string);
				int iStellezwei = getStellezwei(iStelle, sWorte);
				sWorte[iStellezwei] = sWorte[iStelle]+castSonderzeichen(string)+sWorte[iStellezwei];
				sWorte[iStelle]= null;
				sZeichen = removefirst(sZeichen, string);
			}
		}
		for (int r = 0; r < sWorte.length; r++) {
			if (sWorte[r] != null) {
				ret = sWorte[r];
			}
		}
		return ret;
	}

/******************************************************************************************************************
***                                                                                                             ***
***                                            Hilfs Methoden                                                   *** 
***                                                                                                             ***
******************************************************************************************************************/

    /**
     * Prüft ob ein bestimmtes Zeichen aus einem Array mit einem gegebenen
     * Zeichen passt
     *
     * @param zeichen Array mit den vorhandenen Zeichen
     * @param string1 Zeichen, welches gesucht Wird
     * @return gibt zurück, ob das Zeichen enthalten ist
     */
    private static boolean contains(String[] zeichen, String string1) {
        for (String string : zeichen) {
            if (string != null) {
                if (string.equals(string1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Entfernt das Zeichen, welches bereits ersetzt wurde.
     *
     * @param zeichenlokal die vorhandenen Zeichen
     * @param suchstring das Zeichen, welches ersetzt wurde
     * @return gibt die Liste der vorhandenen Zeichen zurück
     */
    private String[] removefirst(String[] zeichenlokal, String suchstring) {
        for (int i = 0; i < zeichenlokal.length; i++) {
            if (zeichenlokal[i] != null) {
                if (zeichenlokal[i].equals(suchstring)) {
                    zeichenlokal[i] = null;
                    return zeichenlokal;
                }
            }
        }
        return zeichenlokal;
    }

    /**
     * Castet Sonderzeichen für Passwörter.<br>
     * Mögliche zu Castende Buchstaben sind:
     * <br>
     * 1. & <br>
     * 2. ( <br>
     * 3. ) <br>
     * 4. = <br>
     * 5. | <br>
     * 6. <><br>
     *
     * @param str - Angabe des zu castenden Buchstabens
     * @return gibt den gecasteten Buchstaben zurück
     */
    private String castSonderzeichen(String str) {
        String ret = null;
        if (str.equals("&")) {
            ret = "\\&";
        } else if (str.equals("(")) {
            ret = "\\(";
        } else if (str.equals(")")) {
            ret = "\\)";
        } else if (str.equals("=")) {
            ret = "\\=";
        } else if (str.equals("|")) {
            ret = "\\|";
        } else if (str.equals("<")) {
            ret = "\\<";
        } else if (str.equals(">")) {
            ret = "\\>";
        }
        return ret;
    }

    private int getStellezwei(int iStelle, String[] sZahlen) {
        int iN = 1;
        for (int u = 0; u < sZahlen.length; u++) {
            if (sZahlen[iStelle + iN] != null) {
                return iN + iStelle;
            } else {
                iN++;
            }
        }
        return 0;
    }

    private int getstelle(String[] sZeichen, String string) {
        int zaehler = 0;
        for (int k = 0; k < sZeichen.length; k++) {
            if (sZeichen[zaehler] != null && sZeichen[zaehler].contains(string)) {
                return zaehler;
            }
            zaehler++;
        }
        return zaehler;
    }
}
