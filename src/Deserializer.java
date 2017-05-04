import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deserializer is the class responsible of loading the root project
 * from the file 'file.ser'
 * if the client wants to recover the root project of the last time
 * he used the program.
 */


public class Deserializer {
    /**
     * Logger of the timer class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Deserializer.class);
    /**
     * Project to be deserialized.
     * @uml.property  name="p"
     */
    private Project p = null;
    /**
     * Empty constructor of a new SavedObjectLoader.
     *
     */
    public Deserializer() {
    }
    /**
     * Loads the last root project object from the file "file.ser"
     * and returns it.
     */
    public final Project loadProject() {
        try (FileInputStream fis = new FileInputStream("file.ser")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.p =  (Project) ois.readObject();
            logger.debug(this.p.getName() + " Recovered from file");
            ois.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.p;
    }
}
