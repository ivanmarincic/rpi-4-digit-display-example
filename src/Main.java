import com.pi4j.io.gpio.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        final GpioController gpio = GpioFactory.getInstance();
        List<Pin> segmentsPins = new ArrayList(Arrays.asList(RaspiPin.GPIO_25, RaspiPin.GPIO_02, RaspiPin.GPIO_24, RaspiPin.GPIO_23, RaspiPin.GPIO_22, RaspiPin.GPIO_21, RaspiPin.GPIO_00, RaspiPin.GPIO_07));
        List<GpioPinDigitalOutput> segments = new ArrayList<>();
        for (int i =0; i<segmentsPins.size(); i++){
            segments.add(gpio.provisionDigitalOutputPin(segmentsPins.get(i), PinState.LOW));
        }
        List<Pin> digitsPin = new ArrayList(Arrays.asList(RaspiPin.GPIO_26, RaspiPin.GPIO_05, RaspiPin.GPIO_03, RaspiPin.GPIO_01));
        List<GpioPinDigitalOutput> digits = new ArrayList<>();
        for (int i =0; i<digitsPin.size(); i++){
            digits.add(gpio.provisionDigitalOutputPin(digitsPin.get(i), PinState.LOW));
        }
        Map<String, Integer[]> numbers =  new HashMap();
        numbers.put("0", new Integer[]{1,1,1,1,1,1,0});
        numbers.put("1", new Integer[]{0,1,1,0,0,0,0});
        numbers.put("2", new Integer[]{1,1,0,1,1,0,1});
        numbers.put("3", new Integer[]{1,1,1,1,0,0,1});
        numbers.put("4", new Integer[]{0,1,1,0,0,1,1});
        numbers.put("5", new Integer[]{1,0,1,1,0,1,1});
        numbers.put("6", new Integer[]{1,0,1,1,1,1,1});
        numbers.put("7", new Integer[]{1,1,1,0,0,0,0});
        numbers.put("8", new Integer[]{1,1,1,1,1,1,1});
        numbers.put("9", new Integer[]{1,1,1,1,0,1,1});

        try{
            Calendar calendar = Calendar.getInstance();
            String time;
            while (true){
                calendar = Calendar.getInstance();
                time = new SimpleDateFormat("mmss").format(calendar.getTime());
                for(int i=0; i<digits.size(); i++){
                    for(int j=0; j<segments.size()-1; j++){
                        segments.get(j).low();
                        if(numbers.get(String.valueOf(time.charAt(i)))[j] == 1){
                            segments.get(j).high();
                        }
                        if(i==1){
                            segments.get(7).high();
                        }else{
                            segments.get(7).low();
                        }
                    }
                    digits.get(i).high();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}