/**
 * Fichiers.java
 */
package kegg.recuperation;

import java.lang.String;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import java.awt.Point;
import java.io.BufferedReader;
// Gestion de fichiers
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Classe contenant toutes les methodes "outils" permettant le telechargement, 
 * la lecture et l'ecriture de fichiers.
 * 
 * @author Antoine Cossa & Cheick Sylla
 *
 */
public class Fichiers {
    
    /**
     * Separateur de fichier du systeme : '/' pour UNIX ou "\\" pour Windows 
     */
    protected static final String sep = File.separator;
    /**
     * Dossier 'data'
     */
    private final static String DATA = "data";
    
    private static File dir;
    
    /**
     * Chemin absolue du dossier Kegg
     */
    private static String rootDir;
    
    /**
     * Chemin absolue du dossier 'data'
     */
    private static String path;
    
	/**
	 * Chemin absolue du fichier image.
	 */
    private static File image;
    /**
     * Chemin absolue du fichier texte contenant les informations du gene.
     */
    private static File infoGeneText;
    /**
     * Chemin absolue du fichier texte contenant les informations du gene.
     */
    private static File infoReactionText;
    /**
     * Chemin absolue du fichier .conf contenant les reactions.
     */
    private static File infoReaction;
    /**
     * Chemin absolue du fichier .conf contenant toutes les reactions.
     */
    private static File infoMap;
    
    // RECTANGLES ATTRIBUTES ==================================================
    /**
     * Longueur des rectangles (x);
     */
    private static final int RECT_LENGTH = 46;
    /**
     * Largeur des rectangles (y);
     */
    private static final int RECT_WIDTH = 17;
    /**
     * Identifiant de la reaction selectionnee.
     */
    private static String selectedReaction = "";
    /**
     * Coordonnees du coin haut gauche du rectangle
     */
    private static Point selectedRectangle = new Point(0, 0);
    /**
     * Coordonnees du coin haut gauche du rectangle
     */
	private static String selectedRectangleString = "";
    /**
     * Expression reguliere virgule.
     */
    private final static Pattern coma = Pattern.compile(",");
    /*========================================================================*/
    /**
     * Initialisation du stockage des donnees du programme 
     * @throws IOException
     */
    public static void setWorkingDirectory() throws IOException {
        
        rootDir = Paths.get(".").toAbsolutePath().normalize().toString();
        //~ Path currentDir = Paths.get(".").toAbsolutePath().normalize();
        System.out.println("Current absolute path is: " + rootDir);
        
        path = rootDir + sep + DATA;
        File dir = new File(path);
        System.out.println("data path: " + path);								// TEST
        if (!dir.exists()) { // Creer le dossier s'il n'existe pas deja
        	dir.mkdirs();
        }
    }
    /*========================================================================*/
    
    /**
     * Determine si le fichier existe deja en local ou s'il faut le telecharger.
     * @param species
     * @param id
     */
    public static void getFile(String species, String id) {
    	
    }
    
    /**
     * Recupere l'image associee a la voie metabolique de l'espece et 
     * l'identifiant passes en parametres. La telecharge si elle n'est pas deja 
     * presente en local.
     * @param species Nom de l'espece.
     * @param map_id Identifiant de la voie metabolique.
     * @return retourne l'image correspondant aux arguments.
     */
    public static File getImage(String species, String map_id) {
    	dir = new File(path + sep + species);
    	if (!dir.exists() || dir.isFile()) { // Creer le dossier s'il n'existe pas deja
        	dir.mkdirs();
        }
    	String org = species + map_id;
    	File imageName = new File(org + ".png");
		image = new File(dir + sep + imageName);
		
    	if (image.exists() && image.isFile()) {
    		return image;
    	} else {
    		String imageUrl = "http://rest.kegg.jp/get/" + org + "/image";
    		return saveFile(imageUrl, image);
    	}
    }
    
    /**
     * Recupere l'image associee a la reaction passee en parametre. La 
     * telecharge si elle n'est pas deja presente en local.
     * @param reaction Identifiant de la reaction.
     * @return retourne l'image correspondant aux arguments.
     */
    public static File getReactionImage(String reaction) {
    	File imageName = new File(reaction + ".png");
		image = new File(path + sep + imageName);
		
    	if (image.exists() && image.isFile()) {
    		return image;
    	} else {
    		String imageUrl = "http://rest.kegg.jp/get/rn:" + reaction + "/image";
    		return saveFile(imageUrl, image);
    	}
    }
    
    /**
     * Recupere le fichier texte contenant les informations du gene dont 
     * l'espece et l'identifiant sont passes en parametres. Le renvoie s'il 
     * existe deja ou le telecharge sinon. 
     * @param species Nom de l'espece.
     * @param id Identifiant du gene.
     * @return Retourne le fichier contenant les informations du gene.
     */
    public static File getGeneText(String species, String id) {
    	dir = new File(path + sep + species);
    	if (!dir.exists() || dir.isFile()) { // Creer le dossier s'il n'existe pas deja
        	dir.mkdirs();
        }
    	
    	String org = species + id;
	    File infoGeneName = new File (org + ".txt");
	    infoGeneText = new File(dir + sep + infoGeneName);
		
    	if (infoGeneText.exists() && infoGeneText.isFile()) {
    		return infoGeneText;
    	} else {
    		String infoGeneUrl = "http://rest.kegg.jp/get/" + species + ":" +id;
    		return saveFile(infoGeneUrl, infoGeneText);
    	}    
    }
    
    /**
     * Recupere le fichier texte contenant les informations de la reaction dont 
     * l'identifiant est passe en parametre. Le renvoie s'il existe deja ou le 
     * telecharge sinon. 
     * @param reactionId Identifiant de la reaction.
     * @return Retourne le fichier contenant les informations de la reaction.
     */
    public static File getReactionText(String reactionId) {
//    	Fichiers stockes dans le dossier "data"
	    File infoReactionName = new File (reactionId + ".txt");
	    infoReactionText = new File(path + sep + infoReactionName);
		
    	if (infoReactionText.exists() && infoReactionText.isFile()) {
    		return infoReactionText;
    	} else {
    		String infoPathwayTextUrl = "http://rest.kegg.jp/get/rn:" + reactionId;
    		return saveFile(infoPathwayTextUrl, infoReactionText);
    	}    
    }
    
    /**
     * Recupere le fichier .conf de la voie metabolique dont l'espece et 
     * l'identifiant sont passes en parametres. Telecharge le fichier s'il n'est
     * pas deja present en local.
     * @param species Espece a laquelle la voie metabolique appartient.
     * @param id Identifiant de la voie metabolique de l'espece.
     * @return Retourne le fichier .conf de la voie metabolique correspondante.
     */
    public static File getSpeConf(String species, String map_id) {
    	dir = new File(path + sep + species);
    	if (!dir.exists() || dir.isFile()) { // Creer le dossier s'il n'existe pas deja
        	dir.mkdirs();
        }
    	String org = species + map_id;
    	File confName = new File(org + ".conf");
		infoReaction = new File(dir + sep + confName);
		
    	if (infoReaction.exists() && infoReaction.isFile()) {
    		return infoReaction;
    	} else {
    		String confUrl = "http://rest.kegg.jp/get/" + org + "/conf";
    		return saveFile(confUrl, infoReaction);
    	}
    }
    
    /**
     * Recupere le fichier .conf de la voie metabolique generique  dont
     * l'identifiant est passe en parametre. Telecharge le fichier s'il n'est
     * pas deja present en local.
     * @param species Espece a laquelle la voie metabolique appartient.
     * @param id Identifiant de la voie metabolique de l'espece.
     * @return Retourne le fichier .conf de la voie metabolique correspondante.
     */
    public static File getMapConf(String id) {
    	// Stocke le fichier directement dans le dossier "data".
    	File confMapName = new File("map" + id + ".conf");
		infoMap = new File(path + sep + confMapName);
		
    	if (infoMap.exists() && infoMap.isFile()) {
    		return infoMap;
    	} else {
    		String mapConfUrl = "http://rest.kegg.jp/get/map" + id + "/conf";
    		return saveFile(mapConfUrl, infoMap);
    	}
    }
    
    
    /**
     * Recupere les fichiers correspondant a l'espece et l'identifiant passes en
     * parametres. Les telecharges s'ils ne sont pas deja presents en local.
     * @param species Espece a laquelle la voie metabolique appartient.
     * @param id Identifiant de la voie metabolique de l'espece.
     * @return image Retourne l'image de la voie metabolique.
     * @throws IOException
     */
    public static File getPathwayFiles(String species, String id) throws IOException {
    	dir = new File(path + sep + species);
    	if (!dir.exists() || dir.isFile()) { // Creer le dossier s'il n'existe pas deja
        	dir.mkdirs();
        }
    	
    	File file = new File(dir + id); //TODO Toujours Faux... A modifier
    	if (!file.exists() || !file.isFile()) {
//    		String org = species + id;
//    	    File imageName = new File(org + ".png");
//    	    File infoPathwayName = new File(org + ".conf");
//    	    File infoMapName = new File("map" + id + ".conf");
    	    
//    	    String imageUrl = "http://rest.kegg.jp/get/" + org + "/image";
//    	    String confUrl = "http://rest.kegg.jp/get/" + org + "/conf";
//    	    String mapConfUrl = "http://rest.kegg.jp/get/map" + id + "/conf";
    	    
//    	    image = new File(dir + sep + imageName);
//    	    infoReaction = new File(dir + sep + infoPathwayName);
//    	    infoMap = new File(dir + sep + infoMapName);
    	    
    	    System.out.println("image path: " + image);							// TEST
    	    System.out.println("txt path: " + infoReaction);					// TEST
    	    
//    	    saveFile(imageUrl, image);
//    	    saveFile(confUrl, infoReaction);
//    	    saveFile(mapConfUrl, infoMap);
    	    
    	    image = getImage(species, id);
    	    getSpeConf(species, id);
    	    getMapConf(id);
    	    
    	}
    	return image;
    }
    
    /**
     * @param species Identifiant de l'espece.
     * @param id Identifiant du gene.
     * @return
     * @throws IOException
     */
    public static File getGenomeFiles(String species, String id) throws IOException {
    	return getGeneText(species, id);
    }
    

    
    
    
    
    
    /**
     * Telecharge un fichier dont le chemin absolue est specifie en parametre.
     * @param urlCible URL du fichier a telecharger.
     * @param destinationFile Chemin absolue du fichier de destination.
     * @return Retourne l'adresse du fichier telecharge
     */
    public static File saveFile(String urlCible, File destinationFile) {
        try {
			URL url = new URL(urlCible);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);
			
			byte[] b = new byte[4096];
			int length;
			
			while ((length = is.read(b)) != -1) {
			    os.write(b, 0, length);
			}
			is.close();
			os.close();
			
		} catch (MalformedURLException mue) {
			System.err.println("SaveFile MalformedURLException: " + mue.getMessage());
		} catch (IOException ioe) {
			System.err.println("SaveFile IOException: " + ioe.getMessage());
		}
        return destinationFile;
    }
    
    
    /*====================== PARTIE LECTURE DE FICHIERS ======================*/
    
    /**
     * Recupere la liste des voies metaboliques a partir du fichier texte du 
     * gene recherche.
     * @param species Identifiant de l'espece.
     * @param gene_id Identifiant du gene.
     * @return Retourne la liste des voies metaboliques.
     */
    public static List<String> getPathwayList(String species, String gene_id) {
    	List<String> pathway_list = new ArrayList<String>();
    	File gene_text = getGeneText(species, gene_id);
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(gene_text));
			String line;
			boolean isPathway = false;
			while ((line = b.readLine()) != null) {
				if (line.startsWith("PATHWAY")) {
					isPathway = true;
				} else if (line.startsWith("MODULE") || line.startsWith("BRITE")) {
					break;
				}
				if (isPathway) {
					Pattern pathway = Pattern.compile("\\b"+ species +"\\d{5}\\b");
					Matcher match = pathway.matcher(line);
					if (match.find()) {
						pathway_list.add(match.group());
						System.out.println(match.group()); 						// TEST
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		}catch (IOException ioe) {
			System.err.println("SaveFile IOException: " + ioe.getMessage());
		}
		
		// telecharge les fichiers conf de la liste
		getPathwayListConf(pathway_list);
		
    	return pathway_list;
    }
    
    /**
     * Telecharge les fichiers conf de la liste s'ils n'existent pas deja en 
     * local.
     * @param pathway_list Liste des voies metaboliques.
     */
    public static void getPathwayListConf(List<String> pathway_list) {
    	Pattern sp = Pattern.compile("[a-z]{3,4}");
    	Pattern id = Pattern.compile("\\d{5}");
    	for (String element : pathway_list) {
//    		System.out.println("element: " + element);							// TEST
    		Matcher match_sp = sp.matcher(element);								
    		Matcher match_id = id.matcher(element);								
    		if (match_sp.find() && match_id.find()) {
//	    		System.out.println("sp :" + match_sp);							// TEST
//	    		System.out.println("id :" + match_id);							// TEST
//	    		System.out.println("sp :" + match_sp.group(0));					// TEST
//	    		System.out.println("id :" + match_id.group(0));					// TEST
	    		String species = match_sp.group(0);
	    		String map_id = match_id.group(0);
	    	    getSpeConf(species, map_id);
	    	    getMapConf(map_id);
    		} else {
    			System.err.println("Invalid pathway name: " + element);
    		}
    	}
    }
    
    /**
     * Recupere la liste des reactions dont le gene et l'espece passes en 
     * parametres sont impliques.
     * @param species Identifiant de l'espece.
     * @param gene_id Identifiant du gene.
     * @return Retourne DefaultListModel<String> contenant la liste des 
     * reactions dont le gene est implique.
     */
    public static DefaultListModel<String> involvedInReactionsList(String species, String gene_id) {
    	DefaultListModel<String> iirl = new DefaultListModel<String>();
    	List<String> pathway_list = getPathwayList(species, gene_id);
    	getPathwayListConf(pathway_list);
    	
    	for (String element : pathway_list) {
    		Pattern map = Pattern.compile("[a-z]{3,4}([0-9]{5})");
    		Matcher match = map.matcher(element);
    		if (match.find()) {
    			String map_id = match.group(1);
	    		// liste des reactions de la voie metabolique generale
	    		Hashtable<String, String> rl = getMapReactionList(map_id);
	    		// liste des reactions pour lesquelles le gene_id intervient
	    		Hashtable<String, String> sgl = getSpecificGeneList(species, map_id, gene_id);
	    		
	    		for(Map.Entry<String, String> m : sgl.entrySet()) {
//	    			System.out.println(m.getKey()+ " : " + m.getValue());
	    			if (rl.containsKey(m.getKey())) { // si rl contient la meme cle que sgl
	    				iirl.addElement(rl.get(m.getKey()) + " @ " + element);
	    			}
	    		}
    		} else {
    			System.err.println("involvedInReactionsList error : invalid pathway");
    		}
    	}
    	return iirl ;
    }
    
    /**
     * Recupere la liste des des genes impliques dans la reaction dont 
     * l'identifiant de l'espece et de la voie metabolique sont passes en 
     * parametres.
     * @param species Identifiant de l'espece.
     * @param map_id Identifiant de la voie metabolique.
     * @return Retourne DefaultListModel<String> contenant la liste des genes 
     * impliques dans la reaction.
     */
    public static DefaultListModel<String> involvesGenesList(String species, String map_id) {
    	DefaultListModel<String> igl = new DefaultListModel<String>();
    	Hashtable<String, List<String>> gl = getGeneList(species, map_id);
    	   	
    	System.out.println("selectedRectangleString:"+selectedRectangleString); // TEST
    	List<String> gene_list =  gl.get(selectedRectangleString);				
    	
		if (!gene_list.isEmpty()) { // si la liste n'est pas vide : 
			System.out.println(selectedReaction + " : " + gene_list);
			for (String gene : gene_list) {
				System.out.println("Gene : " + gene);							// TEST
		    	igl.addElement(gene);
		    }
		}
    	return igl;
    }
    
    /**
     * Renvoie la liste des reactions de la voie metabolique generale passee en 
     * argument.
     * @param map_id
     * @return Hashtable<String, String>: key = coordonnees, 
     * 									  value = id de reaction
     */
	public static Hashtable<String, String> getMapReactionList(String map_id) {
//		List<String> map_reaction_list = new ArrayList<String>();
		Hashtable<String, String> map_reaction_hash = new Hashtable<String, String>();
		File map_conf = getMapConf(map_id);
//		String r = "^rect.+((R\\d{5}).+){1,}";
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(map_conf));
			String line;
			while ((line = b.readLine()) != null) {
				if (line.startsWith("rect")) {
					Pattern reaction = Pattern.compile("\\s\\bR\\d{5}\\b"); // " R00000"
//					Pattern coordTopLeft = Pattern.compile("\\s\\(\\d+,\\d+\\)\\s"); // " (000,000)"
					Pattern coordTopLeft = Pattern.compile("\\s\\((\\d+,\\d+)\\)\\s"); // " 000,000"
					Matcher match_reaction = reaction.matcher(line);
					Matcher match_coord = coordTopLeft.matcher(line);
					if (match_coord.find() && match_reaction.find()) {
						map_reaction_hash.put(match_coord.group(1).trim(), match_reaction.group().trim());
//						map_reaction_list.add(match_reaction.group().trim());
						
//						System.out.println(match_coord.group().trim() +" : " + match_reaction.group().trim()); 						// TEST
						/* Probleme lorsqu'il y a plus d'une reaction par ligne:
						 * seule la premiere est prise en compte.
						 */
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println("SaveFile IOException: " + ioe.getMessage());
		}
		printHash(map_reaction_hash);
		return map_reaction_hash;
	}
	
	/**
     * Renvoie la liste des reactions de la voie metabolique generale passee en 
     * argument.
     * @param map_id
     * @param reaction
     * @return Hashtable<String, String>: key = coordonnees, 
     * 									  value = id de reaction
     */
	public static String getReactionCoord(String map_id, String reaction) {
		String reactionCoord = "";
		File map_conf = getMapConf(map_id);
		
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(map_conf));
			String line;
			while ((line = b.readLine()) != null) {
				if (line.startsWith("rect")) {
					Pattern react = Pattern.compile("\\s" + reaction); // " R00000"
					Pattern coordTopLeft = Pattern.compile("\\s\\((\\d+,\\d+)\\)\\s"); // " 000,000"
					Matcher match_reaction = react.matcher(line);
					Matcher match_coord = coordTopLeft.matcher(line);
					if (match_coord.find() && match_reaction.find()) {
						reactionCoord = match_coord.group(1).trim();
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println("SaveFile IOException: " + ioe.getMessage());
		}
		System.out.println("getReactionCoord : " + reactionCoord); 				// TEST
		return reactionCoord;
	}
	
	/**
     * Renvoie un hash contenant la liste des genes et les coordonnees du 
     * rectangle correspondant a partir du fichier .conf de l'espece.
	 * @param species Identifiant de l'espece.
	 * @param map_id Identifiant de la voie metabolique.
	 * @return Retourne la liste des coordonnees et leur gene associe sous 
	 * forme de table de hash.
	 */
	public static Hashtable<String, List<String>> getGeneList(String species, String map_id) {
//		List<String> geneList = new ArrayList<String>();
//		String g = "^rect.+\\b[a-z]\\d{4}\\b";
		Hashtable<String, List<String>> gene_hash = new Hashtable<String, List<String>>();
		File org_conf = getSpeConf(species, map_id);
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(org_conf));
			String line;
			while ((line = b.readLine()) != null) {
				if (line.startsWith("rect")) {
					Pattern gene = Pattern.compile("(\\s+\\b[a-z]\\d{4}\\b\\s+.+,?){1,}"); // " x0000 (xxxX), ..."
					Pattern coordTopLeft = Pattern.compile("\\s\\((\\d+,\\d+)\\)\\s"); // " 000,000"
					
					Matcher match_gene = gene.matcher(line);
					Matcher match_coord = coordTopLeft.matcher(line);
					
					if (match_coord.find() && match_gene.find()) {
						//stocke les coordonnees et la liste des genes impliques
						Pattern split = Pattern.compile("\\s\\(\\w{3,4}\\),?\\s?");
						/* operation obscure qui permet de recuperer la liste 
						 * des genes : */
						List<String> genes = new ArrayList<String>(Arrays.asList(split.split(match_gene.group().trim())));
							gene_hash.put(match_coord.group(1).trim(), genes);
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println("getGeneList IOException: " + ioe.getMessage());
		}
		printHashList(gene_hash); // TEST
		return gene_hash;
	}
	
	/**
     * Renvoie un hash contenant la liste des coordonnees du rectangle 
     * correspondant au gene passe en argument a partir du fichier .conf de 
     * la voie metabolique de l'espece.
	 * @param species Identifiant de l'espece.
	 * @param map_id Identifiant de la voie metabolique.
	 * @param gene_id Identifiant du gene recherche.
	 * @return
	 */
	public static Hashtable<String, String> getSpecificGeneList(String species, String map_id, String gene_id) {
		Hashtable<String, String> gene_hash = new Hashtable<String, String>();
		File org_conf = getSpeConf(species, map_id);
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(org_conf));
			String line;
			while ((line = b.readLine()) != null) {
				if (line.startsWith("rect")) {
					Pattern gene = Pattern.compile(gene_id); // "x0000"
					Pattern coordTopLeft = Pattern.compile("\\s\\((\\d+,\\d+)\\)\\s"); // " 000,000"
					
					Matcher match_gene = gene.matcher(line);
					Matcher match_coord = coordTopLeft.matcher(line);
					
					if (match_coord.find() && match_gene.find()) {
						gene_hash.put(match_coord.group(1).trim(), match_gene.group().trim());
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println("getSpecificGeneList IOException: " + ioe.getMessage());
		}
		printHash(gene_hash); // TEST
		return gene_hash;
	}
    
	/**
	 * Affiche les elements d'une table de hash dans la console.
	 * @param hash La table de hash a afficher.
	 */
	public static void printHash(Hashtable<String, String> hash) {
		System.out.println("Hash :");
		for(Map.Entry<String, String> m : hash.entrySet()) {
			System.out.println(m.getKey()+ " : " + m.getValue());
		}
	}
	
	/**
	 * Affiche les elements d'une table de hash dans la console.
	 * @param hash La table de hash a afficher.
	 */
	public static void printHashList(Hashtable<String, List<String>> hash) {
		System.out.println("Hash :");
		for(Map.Entry<String, List<String>> m : hash.entrySet()) {
			System.out.println(m.getKey()+ " : " + m.getValue());
		}
	}
	
	
	
	/*=========================== RECTANGLES =================================*/
	/**
	 * 
	 * @param mouse_x Position x de la souris.
	 * @param mouse_y Position y de la souris.
	 * @param map_id Identifiant de la voie metabolique.
	 * @return Retourne l'identifiant de la reaction.
	 */
	public static String getRectangle(int mouse_x, int mouse_y, String map_id) {
		Hashtable<String, String> reaction_list = getMapReactionList(map_id);
		
		for(Map.Entry<String, String> m : reaction_list.entrySet()) {
//			System.out.println(m.getKey()+ " : " + m.getValue());
			String[] split = coma.split(m.getKey());
			// Coordonnees du coin superieur gauche du rectangle
			int rect_x = Integer.parseInt(split[0]);
			int rect_y = Integer.parseInt(split[1]);
			
			if (mouse_x >= rect_x && mouse_x <= rect_x + RECT_LENGTH) {
				if (mouse_y >= rect_y && mouse_y <= rect_y + RECT_WIDTH) {
					selectedRectangleString = m.getKey();
					selectedRectangle.setLocation(rect_x, rect_y);
					selectedReaction = m.getValue();
				}
			}
		}
	    return selectedReaction;		
	}
	
	/**
	 * Recupere les coordonnees du coin superieur gauche du rectangle de la 
	 * reaction selectionnee dans la JList involved_reaction_list 
	 * @param map_id Identifiant de la voie metabolique.
	 * @param reaction Identifiant de la reaction
	 * @return Retourne les coordonnees du coin superieur gauche du rectangle
	 * sous forme de String. Ex : "250,126"
	 */
	public static String setRectangle(String map_id, String reaction) {
		String reactCoord =  getReactionCoord(map_id, reaction);
		String[] split = coma.split(reactCoord);
		// Coordonnees du coin superieur gauche du rectangle
		int rect_x = Integer.parseInt(split[0]);
		int rect_y = Integer.parseInt(split[1]);
	
		selectedRectangleString = reactCoord;
		selectedRectangle.setLocation(rect_x, rect_y);
		selectedReaction = reaction;
		
	    return selectedRectangleString;		
	}
	
	/**
	 * @return Retourne les coordonnees du coin superieur gauche du rectangle 
	 * selectionne.
	 */
	public static Point getRectangleCoord() {
		return selectedRectangle;
	}
	
	
}


