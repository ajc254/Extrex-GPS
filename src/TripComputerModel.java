package extrex;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * TripComputer Model.
 *
 * @author Louise Haggett 2018
 */
class TripComputerModel extends HomeModel {

    String[] tripComp;
    NumberFormat formatter = new DecimalFormat(Const.DECIMAL_FORMAT);
    private static int seconds = 0;
    private static int minutes = 0;
    private static int falseCount = 3;

    public TripComputerModel() {
        tripComp = new String[3];
        setChanged();
        notifyObservers();

    }

    /**
     * Method to start the TripComputer thread.
     */
    public void startTripComputer() {
        
        Thread t = new Thread() {
            public void run() {
                while (true) {

                    // Populate the array with the constantly updated values.
                    tripComp[0] = getOdem();
                    getTime();
                    tripComp[1] = Integer.toString(minutes) + " min " + Integer.toString(seconds) + " sec";
                    tripComp[2] = getSpeed();
                    setChanged();
                    notifyObservers(tripComp);

                    // Ensure we only update every second.
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        t.start();
    }

    /**
     * A method to get the distance of the journey.
     *
     * @return The distance travelled so far.
     */
    public String getOdem() {
        Double distanceTravelled = Satellite.distanceTravelled;
        return formatter.format(distanceTravelled) + "KM";
    }

    /**
     * A method to get the average speed of the user.
     *
     * @return The average speed of the user.
     */
    public String getSpeed() {

        double hours = minutes / 60.0 + seconds / 3600.0;
        
        //Ensure the speed isn't infinity
        if (hours > 0) {
            Double speed = Satellite.distanceTravelled / hours;
            if (!Double.toString(speed).equals("NaN")) {
                return formatter.format(speed) + "KM/H";
            }
        }
        return "0.0KM/H";
    }

    /**
     * A method to get the time of the journey. NOTE: The time will only be
     * increased if the user is moving and has signal
     *
     * @author Louise Haggett 2018
     * @author Jordan Tucker 2018
     */
    public void getTime() {
        
        boolean moving = Satellite.isMoving() && Satellite.hasSignal();
        
        // If the user is moving or allow FALSE_COUNT seconds to stop.
        if ((moving) || falseCount < Const.FALSE_COUNT) {
            if (!moving) {
                falseCount++;
            } else {
                falseCount = 0;
            }
            if (seconds == 59) {
                seconds = 0;
                minutes++;
            } else {
                seconds++;
            }
        }
    }

    /**
     * Method to reset the state of TripComp Mode. NOTE: This will only be
     * called if the power button is clicked
     */
    void resetState() {
        
        seconds = 0;
        minutes = 0;
        setChanged();
        notifyObservers(tripComp);
    }

    @Override
    public void powerButtonClicked() {
        resetState();
    }

    @Override
    public void menuButtonClicked() {
    }

}
