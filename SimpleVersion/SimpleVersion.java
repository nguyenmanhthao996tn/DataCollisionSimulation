import java.util.Random;
import java.io.PrintWriter;
import java.lang.Exception;

class SimpleVersion {

    public static void main(String[] args) {
        try {
            long timeOfExamine = System.currentTimeMillis();
            PrintWriter writer = new PrintWriter(timeOfExamine + ".txt", "UTF-8");

            int numberOfSuccess = 0, numberOfFailure = 0;

            int numberOfPacket = Integer.parseInt(args[0]);
            int timeOnAir = Integer.parseInt(args[1]);
            int transmissionErrorTime = Integer.parseInt(args[2]);
            int maximumOfRandomDelayTime = Integer.parseInt(args[3]);
            int numberOfNode = Integer.parseInt(args[4]);
            int timeSlotLength = Integer.parseInt(args[5]);
            int timerError = Integer.parseInt(args[6]);
            int timerDiffence = Integer.parseInt(args[7]);
            int gatewayProcessingTime = Integer.parseInt(args[8]);

            // Process
            int packetExaminedCounter = 0;
            int packetIndex = 1;
            long Tstart_gateway_next = timeOfExamine;
            long Tstart_node_next = Tstart_gateway_next + timerDiffence;
            while (packetExaminedCounter < numberOfPacket) {
                long Tstart_node = Tstart_node_next;
                long Tstart_gateway = Tstart_gateway_next;
                long Tstop_node = Tstart_node + timeSlotLength;
                long Tstop_gateway = Tstart_gateway + timeSlotLength;
                long Tnode_send_packet = Tstart_node + (new Random()).nextInt(maximumOfRandomDelayTime);
                long Tgateway_listen = Tstart_gateway;
                long Tgateway_receive_packet = Tnode_send_packet + timeOnAir + transmissionErrorTime;
                long Tgateway_send_ack = Tgateway_receive_packet + gatewayProcessingTime;
                long Tnode_receive_ack = Tgateway_send_ack + timeOnAir + transmissionErrorTime;

                boolean result = (Tnode_receive_ack <= Tstop_node) && (Tgateway_listen <= Tnode_send_packet);
                int result1 = 0;

                if (result) {
                    numberOfSuccess++;
                } else {
                    numberOfFailure++;
                    if (Tnode_receive_ack > Tstop_node) {
                        result1 = 1;
                    } else {
                        result1 = 2;
                    }
                }

                String output = packetIndex + "|" + Tstart_node + "|" + Tstart_gateway + "|" + Tstop_node + "|"
                        + Tstop_gateway + "|" + Tnode_send_packet + "|" + Tgateway_listen + "|"
                        + Tgateway_receive_packet + "|" + Tgateway_send_ack + "|" + Tnode_receive_ack + "|"
                        + result1;

                System.out.println(output);
                writer.println(output);

                Tstart_node_next = Tstop_node + (new Random()).nextInt(timerError);
                Tstart_gateway_next = Tstop_gateway + (new Random()).nextInt(timerError);

                packetIndex++;
                if (packetIndex > numberOfNode) {
                    packetIndex = 1;

                    Tstart_gateway_next += timeSlotLength;
                    Tstart_node_next = Tstart_gateway_next + timerDiffence;
                }

                packetExaminedCounter++;
            }

            System.out.println("Examine Time: " + timeOfExamine);
            System.out.println("Number of packet to be examined: " + numberOfPacket);
            System.out.println("Packet time on air: " + timeOnAir);
            System.out.println("Transmission Error Time: " + transmissionErrorTime);
            System.out.println("Maximum of Random Delay Time: " + maximumOfRandomDelayTime);
            System.out.println("Number of Node: " + numberOfNode);
            System.out.println("Time slot length: " + timeSlotLength);
            System.out.println("Timer Error: " + timerError);
            System.out.println("Timer Diffence: " + timerDiffence);
            System.out.println("Gateway Processing Time: " + gatewayProcessingTime);

            System.out.println("Success: " + numberOfSuccess);
            System.out.println("Failure: " + numberOfFailure);

            writer.println("Examine Time: " + timeOfExamine);
            writer.println("Number of packet to be examined: " + numberOfPacket);
            writer.println("Packet time on air: " + timeOnAir);
            writer.println("Transmission Error Time: " + transmissionErrorTime);
            writer.println("Maximum of Random Delay Time: " + maximumOfRandomDelayTime);
            writer.println("Number of Node: " + numberOfNode);
            writer.println("Time slot length: " + timeSlotLength);
            writer.println("Timer Error: " + timerError);
            writer.println("Timer Diffence: " + timerDiffence);
            writer.println("Gateway Processing Time: " + gatewayProcessingTime);

            writer.println("Success: " + numberOfSuccess);
            writer.println("Failure: " + numberOfFailure);

            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
