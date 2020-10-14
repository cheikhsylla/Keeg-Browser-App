/**
 * ConfParser.java
 */
package kegg.recuperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.vutbr.web.css.CSSProperty.Content;

/**
 * @author antoi
 *
 */
public class ConfParser {
	
//    /**
//     * Renvoie la liste des reactions.
//     **/
//	public static List<String> getReactionList(String species, String gene_id) {
//		List<String> reactionList = new ArrayList<String>();
////		String r = "^rect.+((R\\d{5}).+){1,}";
//		
//		if (CONTENT.startsWith("rect")) {
//			
//		}
//		Pattern reaction = Pattern.compile(" \\bR\\d{5}\\b");  // " R00000"
//		Pattern coordTopLeft = Pattern.compile("\\b\\(\\d+,\\d+\\)\\b"); // "(000,000)"
//		
//		Matcher match = reaction.matcher(CONTENT);
//		
//		String[] myStrings = Pattern.split(texteASpliter);
//		for(String temp: myStrings){
//		    System.out.println(temp);
//		    if (Pattern.matches(r, temp) == true) {
//		    	// TODO Do something
//		    	// reaction + " @ " + species + id;
//		    }
//		}
//		System.out.println("Number of split strings: "+myStrings.length);
//
//		return reactionList;
//	}
//	
//	/**
//     * Renvoie la liste des genes.
//     **/
//	public static List<String> getGeneList(String species, String gene_id) {
//		List<String> geneList = new ArrayList<String>();
//		String g = "^rect.+\\b[a-z]\\d{4}\\b";
//		Pattern gene = Pattern.compile(g);
//		
//		Matcher 
//		return geneList;
//	}
	
	public static String readLine(File file) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String line = "";
	}
	
//   public static List<String> getReactions(String species, String gene_id) {
//       //cherche toutes les voies impliquant le gene
//       List<String> pathways = Fichiers.getPathwayFiles(species, gene_id); 
//       List<String> ret = new ArrayList<String>();
//       for (String path : pathways) { 
//           // pour chaque voie on cherche les fichiers conf
//           File org_file = FileDescrip.get_org_conf(specie, path);
//           File map_file = FileDescrip.get_map_conf(path);
//           // on récupère les reactions à partir des fichiers conf
//           List<String> reactions = get_reaction(org_file, map_file, gene_id);
//           // on ajoute les reactions aux résultats
//           for (String reaction : reactions) {
//           ret.add(reaction + " @ " + specie + path);
//           }
//       }
//       return ret;
//   }
}
