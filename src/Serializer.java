import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Serializer is the class responsible of saving the root
 * project to the file 'file.ser' every time the client stops
 * running the program.
 */


public class Serializer {
    /**
     * Logger of the Serializer class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Serializer.class);
    /**
     * Constructor of a new Serializer.
     * Takes the root project and saves it to the file "file.ser".
     */
    public Serializer(final Project rootProject) {
        try (FileOutputStream fos = new FileOutputStream("file.ser")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(rootProject);
            logger.debug(rootProject.getName() + " saved to file");
            oos.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
