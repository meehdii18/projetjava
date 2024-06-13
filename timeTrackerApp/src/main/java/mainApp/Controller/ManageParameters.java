package mainApp.Controller;
import mainApp.Model.Parameters;

import java.io.*;

// TODO : A modifier / déplacer ou supprimer


public class ManageParameters{
    private static final String FILE_NAME = "parameters.ser";  // Nom du fichier où les paramètres sont sauvegardés
    private Parameters parameters;  // Instance des paramètres gérés par cette classe

    // Constructeur qui initialise les paramètres en les chargeant depuis le fichier
    public ManageParameters() {
        this.parameters = loadParameters();
    }

    // Renvoie l'objet Parameters actuel
    public Parameters getParameters() {
        return parameters;
    }

    // Mise à jour de l'objet Parameters et sauvegarde dans le fichier
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
        saveParameters();
    }

    // On sérialise les paramètres et les écrit dans le fichier
    private void saveParameters() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(parameters);  // Écrit l'objet Parameters dans le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Désérialisation des paramètres à partir du fichier
    private Parameters loadParameters() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Parameters) ois.readObject();  // Lit l'objet Parameters depuis le fichier
        } catch (FileNotFoundException exeption) {
            // Si le fichier n'existe pas, retourne des paramètres par défaut
            return new Parameters("127.0.0.1", 8080);
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }


    }
}