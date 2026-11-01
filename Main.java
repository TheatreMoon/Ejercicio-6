import java.util.Scanner;

public class Main{
    public static double numberValid(Scanner keyboard){
        double number = 0;
        boolean validInput = false;
        while(!validInput){
            if(!keyboard.hasNextLine()){
                return -1;
            }
            if(keyboard.hasNextDouble()){
                number = keyboard.nextDouble();
                if(keyboard.hasNextLine()){
                    keyboard.nextLine();
                }
                if(number > 0 && number <= Double.MAX_VALUE){
                    validInput = true;
                }
                else{
                    System.out.println("Number must be greater than zero and within range.");
                }
            }
            else{
                System.out.println("Invalid input. Please enter a number.");
                keyboard.nextLine();
            }
        }
        return number;
    }

    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        Catalog catalog = new Catalog();
        Controller controller = new Controller(catalog);
        API payment1 = new Payment("PAY001", "Card Gateway", true, 80, 0.03);
        API payment2 = new Payment("PAY002", "Bank Transfer", true, 65, 0.01);
        API payment3 = new Payment("PAY003", "Backup Payment", false, 120, 0.02);
        API logistics1 = new Logistics("LOG001", "City Delivery", true, 45, 3.50);
        API logistics2 = new Logistics("LOG002", "National Delivery", true, 80, 2.25);
        API logistics3 = new Logistics("LOG003", "Express Delivery", true, 30, 5);
        API invoice1 = new Invoice("INV001", "Invoice Provider A", true, 65, 0.12);
        API invoice2 = new Invoice("INV002", "Invoice Provider B", true, 90, 0.12);
        API wallet1 = new Wallet("WAL001", "Digital Wallet A", true, 25, 500);
        API wallet2 = new Wallet("WAL002", "Digital Wallet B", true, 45, 1000);

        catalog.addAPI(payment1);
        catalog.addAPI(payment2);
        catalog.addAPI(payment3);
        catalog.addAPI(logistics1);
        catalog.addAPI(logistics2);
        catalog.addAPI(logistics3);
        catalog.addAPI(invoice1);
        catalog.addAPI(invoice2);
        catalog.addAPI(wallet1);
        catalog.addAPI(wallet2);

        boolean running = true;
        while(running == true){
            System.out.println("\nAPI Integration System");
            System.out.println("- 1. View APIs");
            System.out.println("- 2. Search API");
            System.out.println("- 3. Sort by Latency");
            System.out.println("- 4. View Available Operations");
            System.out.println("- 5. Request Operation");
            System.out.println("- 6. Advance Shipping Status");
            System.out.println("- 7. Exit");
            System.out.print("- Choose an option: ");
            if(!keyboard.hasNextLine()){
                break;
            }
            String choice = keyboard.nextLine();

            switch(choice){
                case "1":
                    for(API api: controller.listAPI()){
                        System.out.println("\nID: " + api.getId());
                        System.out.println("Name: " + api.getName());
                        System.out.println("Connected: " + api.getConnected());
                        System.out.printf("Latency: %.2f ms%n", api.getLatency());
                    }
                    break;

                case "2":
                    System.out.print("- Enter API ID: ");
                    if(!keyboard.hasNextLine()){
                        running = false;
                        break;
                    }
                    String searchId = keyboard.nextLine();
                    API foundAPI = controller.findAPI(searchId);
                    if(foundAPI != null){
                        System.out.println("\nAPI Information");
                        System.out.println("ID: " + foundAPI.getId());
                        System.out.println("Name: " + foundAPI.getName());
                        System.out.println("Connected: " + foundAPI.getConnected());
                        System.out.printf("Latency: %.2f ms%n", foundAPI.getLatency());

                        if(foundAPI instanceof Payment){
                            Payment payment = (Payment) foundAPI;
                            System.out.printf("Fee: %.2f%%%n", payment.getFee() * 100);
                            System.out.printf("Amount available for refund: Q%.2f%n", payment.getCharged());
                        }
                        else if(foundAPI instanceof Logistics){
                            Logistics logistics = (Logistics) foundAPI;
                            System.out.printf("Cost per km: Q%.2f%n", logistics.getCostPerKm());
                        }
                        else if(foundAPI instanceof Invoice){
                            Invoice invoice = (Invoice) foundAPI;
                            System.out.printf("Tax rate: %.2f%%%n", invoice.getTaxRate() * 100);
                            System.out.println("Events: " + invoice.getAudit().size());
                        }
                        else if(foundAPI instanceof Wallet){
                            Wallet wallet = (Wallet) foundAPI;
                            System.out.printf("Balance: Q%.2f%n", wallet.getBalance());
                            System.out.println("Events: " + wallet.getAudit().size());
                        }
                    }
                    else{
                        System.out.println("- API not found.");
                    }
                    break;

                case "3":
                    controller.sort();
                    System.out.println("- Catalog sorted by latency and ID. Select View APIs to see it.");
                    break;

                case "4":
                    System.out.print("- Enter API ID: ");
                    if(!keyboard.hasNextLine()){
                        running = false;
                        break;
                    }
                    String operationsId = keyboard.nextLine();
                    API selectedAPI = controller.findAPI(operationsId);
                    if(selectedAPI != null){
                        System.out.println("Available operations:");
                        for(String operation: controller.getOperations(selectedAPI)){
                            System.out.println("- " + operation);
                        }
                    }
                    else{
                        System.out.println("- API not found.");
                    }
                    break;

                case "5":
                    System.out.print("- Enter API ID: ");
                    if(!keyboard.hasNextLine()){
                        running = false;
                        break;
                    }
                    String id = keyboard.nextLine();
                    API api = controller.findAPI(id);
                    if(api != null){
                        boolean operating = true;
                        while(operating == true){
                            System.out.println("\nOperations for " + api.getName());
                            System.out.println("Available: " + controller.getOperations(api));
                            System.out.println("- 1. Charge");
                            System.out.println("- 2. Refund");
                            System.out.println("- 3. Quote Delivery");
                            System.out.println("- 4. Check Status");
                            System.out.println("- 5. Generate Document");
                            System.out.println("- 6. Register Event");
                            System.out.println("- 7. View Events");
                            System.out.println("- 8. Back");
                            System.out.print("- Choose an option: ");
                            if(!keyboard.hasNextLine()){
                                operating = false;
                                running = false;
                                break;
                            }
                            String operationChoice = keyboard.nextLine();
                            switch(operationChoice){
                                case "1":
                                    if(controller.getOperations(api).contains("charge")){
                                        if(api.getConnected() == true){
                                            System.out.print("- Amount in Q: ");
                                            double chargeValue = numberValid(keyboard);
                                            if(chargeValue < 0){
                                                operating = false;
                                                running = false;
                                                break;
                                            }
                                            double chargeResult = controller.charge(api, chargeValue);
                                            if(chargeResult >= 0){
                                                System.out.printf("- Charged: Q%.2f%n", chargeResult);
                                            }
                                            else{
                                                System.out.println("- Operation rejected. Check the amount, balance or previous charges.");
                                            }
                                        }
                                        else{
                                            System.out.println("- API disconnected.");
                                        }
                                    }
                                    else{
                                        System.out.println(controller.checkOperation(api, "charge"));
                                    }
                                    break;

                                case "2":
                                    if(controller.getOperations(api).contains("refund")){
                                        if(api.getConnected() == true){
                                            System.out.print("- Amount in Q: ");
                                            double refundValue = numberValid(keyboard);
                                            if(refundValue < 0){
                                                operating = false;
                                                running = false;
                                                break;
                                            }
                                            double refundResult = controller.refund(api, refundValue);
                                            if(refundResult >= 0){
                                                System.out.printf("- Refunded: Q%.2f%n", refundResult);
                                            }
                                            else{
                                                System.out.println("- Operation rejected. Check the amount, balance or previous charges.");
                                            }
                                        }
                                        else{
                                            System.out.println("- API disconnected.");
                                        }
                                    }
                                    else{
                                        System.out.println(controller.checkOperation(api, "refund"));
                                    }
                                    break;

                                case "3":
                                    if(controller.getOperations(api).contains("calcCost")){
                                        if(api.getConnected() == true){
                                            System.out.print("- Distance in km: ");
                                            double calcCostValue = numberValid(keyboard);
                                            if(calcCostValue < 0){
                                                operating = false;
                                                running = false;
                                                break;
                                            }
                                            double calcCostResult = controller.calcCost(api, calcCostValue);
                                            if(calcCostResult >= 0){
                                                System.out.printf("- Quote: Q%.2f%n", calcCostResult);
                                            }
                                            else{
                                                System.out.println("- Quote rejected. Check the distance and cost per km.");
                                            }
                                        }
                                        else{
                                            System.out.println("- API disconnected.");
                                        }
                                    }
                                    else{
                                        System.out.println(controller.checkOperation(api, "calcCost"));
                                    }
                                    break;

                                case "4":
                                    System.out.println(controller.status(api));
                                    break;

                                case "5":
                                    System.out.println(controller.genDoc(api));
                                    break;

                                case "6":
                                    if(controller.getOperations(api).contains("registerEvent")){
                                        if(api.getConnected() == true){
                                            System.out.print("- Event: ");
                                            if(!keyboard.hasNextLine()){
                                                operating = false;
                                                running = false;
                                                break;
                                            }
                                            String event = keyboard.nextLine();
                                            if(controller.registerEvent(api, event)){
                                                System.out.println("- Event registered.");
                                            }
                                            else{
                                                System.out.println("- Event cannot be empty.");
                                            }
                                        }
                                        else{
                                            System.out.println("- API disconnected.");
                                        }
                                    }
                                    else{
                                        System.out.println(controller.checkOperation(api, "registerEvent"));
                                    }
                                    break;

                                case "7":
                                    if(controller.getOperations(api).contains("getEvent")){
                                        if(controller.getEvent(api).size() == 0){
                                            System.out.println("- No events registered.");
                                        }
                                        else{
                                            for(String event: controller.getEvent(api)){
                                                System.out.println("- " + event);
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println(controller.checkOperation(api, "getEvent"));
                                    }
                                    break;

                                case "8":
                                    operating = false;
                                    break;

                                default:
                                    System.out.println("- Invalid option.");
                                    break;
                            }
                        }
                    }
                    else{
                        System.out.println("- API not found.");
                    }
                    break;
                case "6":
                    System.out.print("- Enter API ID: ");
                    String shippingId = keyboard.nextLine();
                    API shippingAPI = controller.findAPI(shippingId);

                    if(shippingAPI != null){
                        if(controller.changeShippingStatus(shippingAPI)){
                            System.out.println("- Shipping status updated.");
                            System.out.println(controller.status(shippingAPI));
                        }
                        else{
                            System.out.println("- Status could not be advanced. The API may not be a logistics provider or the shipment is already delivered.");
                        }
                    }
                    else{
                        System.out.println("- API not found.");
                    }
                    break;
                case "7":
                    running = false;
                    System.out.println("- Program closed.");
                    break;

                default:
                    System.out.println("- Invalid option.");
                    break;
            }
        }
        keyboard.close();
    }
}
