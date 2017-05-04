


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public final class Client {
    /**
     * Number 3 constant.
     */
    public static final int THREE = 3;
    /**
     * Constant for 2000ms.
     */
    public static final int TWO_THOUSAND_MS = 2000;
    /**
     * Constant for 4000ms.
     */
    public static final int THREE_THOUSAND_MS = 3000;
    /**
     * Constant for 7000ms.
     */
    public static final int FOUR_THOUSAND_MS = 4000;
    /**
     * Constant for 7000ms.
     */
    public static final int SEVEN_THOUSAND_MS = 7000;
    /**
     * Constant for 10000ms.
     */
    public static final int TEN_THOUSAND_MS = 10000;
    /**
     * Constant for 4.
     */
    public static final int FOUR = 4;
    /**
     * Logger of the Client class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    /**
     * @uml.property  name="activity"
     * @uml.associationEnd  multiplicity="(1 -1)" ordering="true"
     * aggregation="composite" inverse="client:Activity"
     * @uml.association  name="activities"
     */
    private static  List<Activity> activity = new ArrayList<Activity>();
    /**
     * Empty constructor of utility class.
     */
    private Client() {
    }
    /**
     * Main method.
     * @param args
     */
    @SuppressWarnings("unused")
    public static void main(final String[] args) throws Exception {
        TimeLapse tl = new TimeLapse();
        tl.setStartDate(new Date());
        Task t3 = null;
        Task t2 = null;
        Task t1 = null;
        Project p1 = null;
        Project p2 = null;
        Project p3 = null;
        Project p4 = null;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int option = 0;
        while (option != 1 && option != 2 && option != THREE) {
            System.out.println("Enter the desired option: ");
            System.out.println("1-Load from existing timeTracker");
            System.out.println("2-Create new timeTracker");
            System.out.println("3-Exit");
            option = reader.nextInt();
        }

        /**
         * Load root project from file and keep running
         */
        if (option == 1) {
            Deserializer sol = new Deserializer();
            p1 = (Project) sol.loadProject();
            List<Activity> childs = new ArrayList<Activity>();
            childs = p1.getChildActivities();
            t3 = (Task) childs.get(0);
            p2 = (Project) childs.get(1);
            childs = p2.getChildActivities();
            t1 = (Task) childs.get(0);
            t2 = (Task) childs.get(1);
            Clock clock = Clock.getInstance();
            clock.setDelay(TWO_THOUSAND_MS);
            Printer printer = new Printer(p1);
            printer.visitProject(p1);

            try {
                t3.start();
                Thread.sleep(THREE_THOUSAND_MS);
                t3.stop();
                Thread.sleep(SEVEN_THOUSAND_MS);
                t2.start();
                Thread.sleep(TEN_THOUSAND_MS);
                t2.stop();
                t3.start();
                Thread.sleep(TWO_THOUSAND_MS);
                t3.stop();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        /**
         * Create a new timeTracker and start running
         */
        } else if (option == 2) {
            p1 = new Project("P1", "desc", null);
            t3 = new Task("T3", "desc1", p1);
            p2 = new Project("P2", "desc", p1);
            p3 = new Project("P3" , "desc", p1);
            t1 = new Task("T1", "desc1", p3);
            t2 = new Task("T2", "desc1x�", p2);
            p4 = new Project("P4" , "desc", null);
            option = 0;
            while (option != 1 && option != 2 && option != THREE) {
                System.out.println("Enter the desired test: ");
                System.out.println("1-Sequencial test");
                System.out.println("2-Parallel test");
                System.out.println("3-Exit");
                option = reader.nextInt();
            }
            Clock clock = Clock.getInstance();
            clock.setDelay(TWO_THOUSAND_MS);
            Printer printer = new Printer(p1);
            /**
             * Sequential test
             */
            if (option == 1) {
                try {
                    t3.start();
                    Thread.sleep(THREE_THOUSAND_MS);
                    t3.stop();
                    Thread.sleep(SEVEN_THOUSAND_MS);
                    t2.start();
                    Thread.sleep(TEN_THOUSAND_MS);
                    t2.stop();
                    t3.start();
                    Thread.sleep(TWO_THOUSAND_MS);
                    t3.stop();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            /**
             * Parallel test
             */
            } else if (option == 2) {
                try {
                    t3.start();
                    Thread.sleep(FOUR_THOUSAND_MS);
                    t2.start();
                    Thread.sleep(TWO_THOUSAND_MS);
                    t3.stop();
                    Thread.sleep(TWO_THOUSAND_MS);
                    t1.start();
                    Thread.sleep(FOUR_THOUSAND_MS);
                    t1.stop();
                    Thread.sleep(TWO_THOUSAND_MS);
                    t2.stop();
                    Thread.sleep(FOUR_THOUSAND_MS);
                    t3.start();
                    Thread.sleep(TWO_THOUSAND_MS);
                    t3.stop();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
        activity.add(p1);
        tl.setEndDate(new Date());
        Ascii a = new Ascii();
        Html h = new Html();
        DetailedReport dr = new DetailedReport(tl, a, activity);
        BriefReport r = new BriefReport(tl, a, activity);
        dr = new DetailedReport(tl, h, activity);
        h = new Html();
        r = new BriefReport(tl, h, activity);
        Serializer ds = new Serializer(p1); //save the root project
    }
}


